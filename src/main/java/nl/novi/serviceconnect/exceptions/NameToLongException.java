package nl.novi.serviceconnect.exceptions;


public class NameToLongException extends RuntimeException {
    public NameToLongException(String message) {
        super("Record to long " + message);
    }
}