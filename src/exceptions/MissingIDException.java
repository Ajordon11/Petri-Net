package exceptions;

public class MissingIDException extends Exception {
    public MissingIDException() {
    }

    public MissingIDException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Cannot find ID.";
    }
}
