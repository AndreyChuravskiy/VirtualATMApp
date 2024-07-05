package exceptions;

public class ATMDataParseException extends Exception{
    public ATMDataParseException(String message) {
        super(message);
    }

    public ATMDataParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
