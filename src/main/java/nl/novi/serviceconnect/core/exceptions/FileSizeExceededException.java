package nl.novi.serviceconnect.core.exceptions;

public class FileSizeExceededException extends RuntimeException {

    public FileSizeExceededException(String fileName, long fileSize, long maxSize) {
        super("File size of '" + fileName + "' exceeds the maximum allowed size. Current size: " + fileSize + " bytes. Maximum size allowed: " + maxSize + " bytes.");
    }
}
