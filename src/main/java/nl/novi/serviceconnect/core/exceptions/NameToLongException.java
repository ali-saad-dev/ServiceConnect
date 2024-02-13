package nl.novi.serviceconnect.core.exceptions;


public class NameToLongException extends RuntimeException {
    public NameToLongException(String message) {
        super("Record to long " + message);
    }
}