package exceptions;

public class ClientDataException extends Exception {

    public ClientDataException(String message) {
        super(message);
    }

    public ClientDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
