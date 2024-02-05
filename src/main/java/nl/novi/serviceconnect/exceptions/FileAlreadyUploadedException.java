package nl.novi.serviceconnect.exceptions;

public class FileAlreadyUploadedException extends RuntimeException {

    public FileAlreadyUploadedException(String message) {
        super("File has already been uploaded. Please choose a different file " + message);
    }
}
