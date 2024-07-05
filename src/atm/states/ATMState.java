package atm.states;

import atm.ATM;

public interface ATMState {
    void handle(ATM atm);
}
