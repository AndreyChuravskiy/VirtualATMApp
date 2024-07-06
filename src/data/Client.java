package data;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

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

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public LocalDateTime getBlockTime() {
        return blockTime;
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
            }
            return false;
        }
    }

    @Override
    public String toString() {
        String stringBlockTime;
        if (blockTime == null) {
            stringBlockTime = "null";
        } else {
            stringBlockTime = blockTime.toString();
        }
        return cardNumber + "\\" + pinCode + "\\" + balance + "\\" + failedAttempts + "\\" +
                isBlocked + "\\" + stringBlockTime;
    }

    public static Optional<Client> fromString(String clientStr) {
        try {
            String[] parts = clientStr.split("\\\\");

            if (parts.length < 6) {
                return Optional.empty();
            }

            String cardNumber = parts[0];
            if (!isCardNumberValidate(cardNumber)) {
                return Optional.empty();
            }

            String pinCode = parts[1];
            if (!isPinValidate(pinCode)) {
                return Optional.empty();
            }

            int balance = Integer.parseInt(parts[2]);
            int failedAttempts = Integer.parseInt(parts[3]);
            boolean isBlocked = Boolean.parseBoolean(parts[4]);
            LocalDateTime blockTime;
            if (parts[5].equals("null")) {
                blockTime = null;
            } else {
                blockTime = LocalDateTime.parse(parts[5]);
            }

            return Optional.of(new Client(cardNumber, pinCode, balance, failedAttempts, isBlocked, blockTime));
        } catch (Exception ex) {
            return Optional.empty();
        }

    }

    public static boolean isCardNumberValidate(String cardNumber) {
        String regex = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(cardNumber).matches();
    }

    public static boolean isPinValidate(String pin) {
        String regex = "\\d{4}";
        return Pattern.matches(regex, pin);
    }
}
