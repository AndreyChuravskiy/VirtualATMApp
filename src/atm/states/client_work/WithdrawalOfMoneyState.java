package atm.states.client_work;

import atm.ATM;
import atm.states.ATMState;
import atm.states.RootState;
import data.Client;

public class WithdrawalOfMoneyState implements ATMState {

    private Client client;

    public WithdrawalOfMoneyState(Client client) {
        this.client = client;
    }

    @Override
    public void handle(ATM atm) {
        atm.display("Введите сумму для снятия средств");
        atm.display("Для возврата в предыдущее меню введите 'back'");
        atm.display("->");

        String input = atm.getInput();
        if (input.equals("back")) {
            atm.setCurrentState(new ClientMenuState(client));
            return;
        }

        int count;
        try {
            count = Integer.parseInt(input);
        } catch(Exception e) {
            atm.display("Некорректный ввод, повторите попытку");
            return;
        }

        if (count <= 0) {
            atm.display("Некорректный ввод, повторите попытку");
            return;
        }

        client.withdrawMoney(count, atm);
        atm.setCurrentState(new ClientMenuState(client));
    }
}
