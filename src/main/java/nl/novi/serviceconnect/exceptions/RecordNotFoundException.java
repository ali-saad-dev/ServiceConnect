package nl.novi.serviceconnect.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String message) {
        super("Record not found" + message);
    }
}
