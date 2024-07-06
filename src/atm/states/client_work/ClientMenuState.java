package atm.states.client_work;

import atm.ATM;
import atm.states.ATMState;
import atm.states.RootState;
import data.Client;

public class ClientMenuState implements ATMState {

    private Client client;

    public ClientMenuState(Client client) {
        this.client = client;
    }

    @Override
    public void handle(ATM atm) {
        atm.display("Выберите дальнейшее действие вводом номера пункта:");

        atm.display("1-Проверить баланс счета");
        atm.display("2-Снять средства со счета");
        atm.display("3-Пополнить счет");
        atm.display("4-Выйти из аккаунта");
        atm.display("5-Выйти из системы");

        int item;
        try {
            item = Integer.parseInt(atm.getInput());
        } catch(Exception e) {
            atm.display("Некоректный ввод");
            return;
        }

        switch (item) {
            case 1:
            {
                atm.display("Баланс: " + client.getBalance());
                atm.display("Для продолжения нажмите Enter");
                atm.getInput();
                break;
            }
            case 2:
            {

                break;
            }
            case 3:
            {

                break;
            }
            case 4:
            {
                atm.setCurrentState(new RootState());
                break;
            }
            case 5:
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
