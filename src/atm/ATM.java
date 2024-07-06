package atm;

import atm.states.ATMState;
import atm.states.RootState;
import data.ATMData;
import data.Client;
import exceptions.ATMDataException;
import exceptions.ClientDataException;
import io.IOService;
import utils.ATMUtils;
import utils.ClientUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ATM {

    private final int MAX_DEPOSIT = 1000000;

    private boolean isRunning;

    private ATMState currentState;

    private Map<String, Client> clients;

    private ATMData atmData;

    private IOService ioService;

    public ATM(IOService ioService) {
        this.isRunning = true;
        this.ioService = ioService;
        this.clients = getClientsFromFile();
        this.currentState = new RootState();
        this.atmData = getAtmDataFromFile();
    }

    public ATM() {
        this(new IOService());
    }


    private void saveATMDataToFile() {
        try {
            ATMUtils.saveATMDataToFile(atmData);
        } catch (Exception e) {
            display(e.getMessage());
        }
    }

    private void saveClientsToFile() {
        try {
            ClientUtils.saveClientsToFile(clients.values().toArray(new Client[0]));
        } catch(Exception e) {
            display(e.getMessage());
        }
    }

    private Map<String, Client> getClientsFromFile() {
        try {
            Client[] clients = ClientUtils.loadClientsFromFile().orElseThrow(() ->
                    new ClientDataException("Ошибка получения списка клиентов"));
            Map<String, Client> clientMap = Arrays.stream(clients)
                    .collect(Collectors.toMap(Client::getCardNumber, client -> client));
            return clientMap;
        } catch (ClientDataException e) {
            display(e.getMessage());
            shutdown();
            return null;
        }
    }

    private ATMData getAtmDataFromFile() {
        try {
            return ATMUtils.loadATMDataFromFile().orElseThrow(() ->
                    new ATMDataException("Ошибка получения баланса банкомата"));
        } catch (ATMDataException e) {
            display(e.getMessage());
            shutdown();
            return null;
        }
    }

    public void setCurrentState(ATMState currentState) {
        this.currentState = currentState;
    }

    public void addMoney(int amount) {
        atmData.setBalance(atmData.getBalance() + amount);
    }

    public void removeMoney(int amount) {
        atmData.setBalance(atmData.getBalance() - amount);
    }

    public boolean canDeposit(int amount) {
        return amount <= MAX_DEPOSIT;
    }

    public boolean hasEnoughMoney(int amount) {
        return atmData.getBalance() >= amount;
    }

    public void save() {
        saveATMDataToFile();
        saveClientsToFile();
        display("Все данные сохранены");
    }

    public Map<String, Client> getClients() {
        return clients;
    }

    public void display(String message) {
        ioService.showMessage(message);
    }

    public String getInput() {
        return ioService.getInput();
    }

    public void shutdown() {
        display("Банкомат выключен");
        isRunning = false;
    }
    public void run() {
        while (isRunning) {
            currentState.handle(this);
        }
    }
}
