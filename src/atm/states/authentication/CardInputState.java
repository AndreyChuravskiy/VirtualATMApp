package atm.states.authentication;

import atm.ATM;
import atm.states.ATMState;
import atm.states.RootState;
import data.Client;

import java.util.Map;

public class CardInputState implements ATMState {
    @Override
    public void handle(ATM atm) {
        atm.display("Введите номер карты по шаблону: XXXX-XXXX-XXXX-XXXX");
        atm.display("Для возврата в предыдущее меню введите 'back'");
        atm.display("->");

        String cardNumber = atm.getInput();

        if (cardNumber.equals("back")) {
            atm.setCurrentState(new RootState());
            return;
        }

        if (!isCardNumberValidate(cardNumber)) {
            atm.display("Номер карты не соответствует шаблону (XXXX-XXXX-XXXX-XXXX)");
            return;
        }

        if (!isCardPresent(cardNumber, atm.getClients())) {
            atm.display("Данной карты не существует");
            return;
        }

        Client client = atm.getClients().get(cardNumber);
        if (client.checkBlock()) {
            atm.display("Карта заблокирована");
            return;
        }

        atm.setCurrentState(new PinInputState(cardNumber));
    }

    private boolean isCardNumberValidate(String cardNumber) {
        return Client.isCardNumberValidate(cardNumber);
    }

    private boolean isCardPresent(String cardNumber, Map<String, Client> clients) {
        return clients.containsKey(cardNumber);
    }
}
