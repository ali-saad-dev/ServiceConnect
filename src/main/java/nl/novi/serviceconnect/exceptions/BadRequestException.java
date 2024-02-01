package nl.novi.serviceconnect.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super("Bad request" + message);
    }
}
