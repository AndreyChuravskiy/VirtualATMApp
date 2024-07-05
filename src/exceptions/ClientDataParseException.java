package exceptions;

public class ClientDataParseException extends Exception {

    public ClientDataParseException(String message) {
        super(message);
    }

    public ClientDataParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
