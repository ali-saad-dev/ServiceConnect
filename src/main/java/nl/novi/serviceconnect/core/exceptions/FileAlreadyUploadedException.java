package nl.novi.serviceconnect.core.exceptions;

public class FileAlreadyUploadedException extends RuntimeException {

    public FileAlreadyUploadedException(String message) {
        super("File has already been uploaded. Please choose a different file " + message);
    }
}
