package exceptions;

public class EmptyPasswordException extends Exception{
    public EmptyPasswordException(String msg) {
        super(msg);
    }
}
