package nl.novi.serviceconnect.core.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super("Bad request " + message);
    }
}
