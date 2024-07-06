package data;

import exceptions.ATMDataException;

import java.util.Optional;

public class ATMData {

    private int balance;

    private ATMData(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int amount) {
        balance = amount;
    }

    @Override
    public String toString() {
        return "" + balance;
    }

    public static Optional<ATMData> fromString(String ATMStr) {
        try {
            int balance = Integer.parseInt(ATMStr);
            if (balance < 0) return Optional.empty();
            return Optional.of(new ATMData(balance));
        } catch (Exception ex) {
            return Optional.empty();
        }

    }
}
