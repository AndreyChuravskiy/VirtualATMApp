package exceptions;

public class ATMDataException extends Exception{
    public ATMDataException(String message) {
        super(message);
    }

    public ATMDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
