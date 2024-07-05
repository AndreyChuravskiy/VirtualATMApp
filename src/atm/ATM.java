package atm;

import atm.states.ATMState;
import atm.states.AuthenticationState;
import data.Client;
import io.IOService;

import java.util.Map;

public class ATM {
    private boolean isRunning;
    private ATMState currentState;
    private Map<String, Client> clients;
    private IOService ioService;

    public ATM(IOService ioService) {
        this.ioService = ioService;
        this.clients = getClientsFromServer();
        this.currentState = new AuthenticationState();
    }

    public ATM() {
        this(new IOService());
    }

    private Map<String, Client> getClientsFromServer() {
        return null;
    }

    public void shutdown() {
        this.isRunning = false;
    }
    public void run() {
        isRunning = true;
        while (isRunning) {
            currentState.handle(this);
        }
    }
}
