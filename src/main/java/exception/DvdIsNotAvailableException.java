package exception;

public class DvdIsNotAvailableException extends RuntimeException{
    public DvdIsNotAvailableException(String message) {
        super(message);
    }

}
