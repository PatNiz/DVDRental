package exception;

public class DVDAlreadyExistsException extends RuntimeException {
    public DVDAlreadyExistsException(String message) {
        super(message);
    }
}
