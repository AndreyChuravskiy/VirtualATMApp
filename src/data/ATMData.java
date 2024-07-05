package data;

import exceptions.ATMDataParseException;
import exceptions.ClientDataParseException;

import java.time.LocalDateTime;

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

    public static ATMData fromString(String ATMStr) throws ATMDataParseException {
        try {
            int balance = Integer.parseInt(ATMStr);
            return new ATMData(balance);
        } catch (Exception ex) {
            throw new ATMDataParseException("Ошибка парсинга данных банкомата", ex);
        }

    }
}
