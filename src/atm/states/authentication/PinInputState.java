package atm.states.authentication;

import atm.ATM;
import atm.states.ATMState;
import atm.states.RootState;
import atm.states.client_work.ClientMenuState;
import data.Client;

import java.util.regex.Pattern;

public class PinInputState implements ATMState {

    private String cardNumber;

    public PinInputState(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void handle(ATM atm) {
        atm.display("Введите PIN-код (4-значный циферный код)");
        atm.display("Для возврата в предыдущее меню введите 'back'");
        atm.display("->");

        String pin = atm.getInput();

        if (pin.equals("back")) {
            atm.setCurrentState(new RootState());
            return;
        }

        if (!isPinValidate(pin)) {
            atm.display("Некоректный ввод");
            return;
        }

        Client client = atm.getClients().get(cardNumber);
        if (!isPinCorrect(pin, client)) {
            if (!client.checkBlock()) {
                atm.display("Введен неправильный PIN-код, до блокировки " + (3 - client.getFailedAttempts()) + " попытки");
                return;
            } else {
                atm.display("Введен неправльный PIN-код, аккаунт заблокирован");
                atm.setCurrentState(new RootState());
                return;
            }
        }

        atm.display("Добро пожаловать");
        atm.setCurrentState(new ClientMenuState(client));
    }

    private boolean isPinValidate(String pin) {
        return Client.isPinValidate(pin);
    }

    private boolean isPinCorrect(String pin, Client client) {
        return client.checkPinCode(pin);
    }
}
