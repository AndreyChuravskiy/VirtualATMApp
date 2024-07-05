package data;

import exceptions.ClientDataParseException;

import java.time.LocalDateTime;

public class Client {

    private String cardNumber;

    private String pinCode;

    private int balance;

    private int failedAttempts;

    private boolean isBlocked;

    private LocalDateTime blockTime;

    private Client(String cardNumber, String pinCode, int balance, int failedAttempts, boolean isBlocked, LocalDateTime blockTime) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.balance = balance;
        this.failedAttempts = failedAttempts;
        this.isBlocked = isBlocked;
        this.blockTime = blockTime;
    }

    public Client(String cardNumber, String pinCode, int balance) {
        this(cardNumber, pinCode, balance, 0, false, null);
    }

    public Client(String cardNumber, String pinCode) {
        this(cardNumber, pinCode, 0);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getBalance() {
        return balance;
    }

    public boolean checkBlock() {
        if (isBlocked && LocalDateTime.now().isBefore(blockTime.plusDays(1))) {
            return true;
        } else if (isBlocked && LocalDateTime.now().isAfter(blockTime.plusDays(1))) {
            isBlocked = false;
            failedAttempts = 0;
        }
        return false;
    }

    public boolean checkPinCode(String inputPin) {
        if (pinCode.equals(inputPin)) {
            failedAttempts = 0;
            return true;
        } else {
            failedAttempts++;
            if (failedAttempts >= 3) {
                isBlocked = true;
                blockTime = LocalDateTime.now();
            } else {
            }
            return false;
        }
    }

    @Override
    public String toString() {
        return cardNumber + "\\" + pinCode + "\\" + balance + "\\" + failedAttempts + "\\" +
                isBlocked + "\\" + blockTime;
    }

    public static Client fromString(String clientStr) throws ClientDataParseException {
        try {
            String[] parts = clientStr.split("\\\\");
            if (parts.length < 6) {
                throw new ClientDataParseException("Ошибка парсинга данных клиента: недостаточно данных для парсинга");
            }
            String cardNumber = parts[0];
            String pinCode = parts[1];
            int balance = Integer.parseInt(parts[2]);
            int failedAttempts = Integer.parseInt(parts[3]);
            boolean isBlocked = Boolean.parseBoolean(parts[4]);
            LocalDateTime blockTime = LocalDateTime.parse(parts[5]);
            return new Client(cardNumber, pinCode, balance, failedAttempts, isBlocked, blockTime);
        } catch (Exception ex) {
            throw new ClientDataParseException("Ошибка парсинга данных клиента", ex);
        }

    }
}
