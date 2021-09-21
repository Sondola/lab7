package exceptions;

public class EmptyLoginException extends Exception {
    public EmptyLoginException(String msg) {
        super(msg);
    }
}
