package data;

import java.util.Optional;

public class ATMData {

    private int balance;

    private ATMData(int balance) {
        this.balance = balance;
    }

    public ATMData() {
        this(50000);
    }

    @Override
    public String toString() {
        return "" + balance;
    }

    public static Optional<ATMData> fromString(String ATMStr) {
        try {
            int balance = Integer.parseInt(ATMStr);
            return Optional.of(new ATMData(balance));
        } catch (Exception ex) {
            return Optional.empty();
        }

    }
}
