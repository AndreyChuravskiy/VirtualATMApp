package atm.states;

import atm.ATM;
import atm.states.authentication.CardInputState;

public class RootState implements ATMState{
    @Override
    public void handle(ATM atm) {
        atm.display("Выберите дальнейшее действие вводом пункта меню:");

        atm.display("1-Работа с банковской картой");
        atm.display("2-Выход");
        atm.display("->");

        int item;
        try {
            item = Integer.parseInt(atm.getInput());
        } catch(Exception e) {
            atm.display("Некорректный ввод, повторите попытку");
            return;
        }

        switch (item) {
            case 1:
            {
                atm.setCurrentState(new CardInputState());
                break;
            }
            case 2:
            {
                atm.save();
                atm.shutdown();
                break;
            }
            default:
            {
                atm.display("Некорректный ввод, повторите попытку");
                break;
            }
        }
    }
}
