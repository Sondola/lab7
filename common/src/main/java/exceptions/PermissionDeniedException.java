package exceptions;

public class PermissionDeniedException extends Exception{
    public PermissionDeniedException(String msg) {
        super(msg);
    }
}
