package nl.novi.serviceconnect.exceptions;


public class NameToLongException extends RuntimeException {
    public NameToLongException() {
        super("Record is to long");
    }
    public NameToLongException(String message) {super(message);}
}