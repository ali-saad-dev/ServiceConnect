package nl.novi.serviceconnect.core.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String message) {
        super("Record not found " + message);
    }
}
