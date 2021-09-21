package exceptions;

public class UserIsAlreadyRegisteredException extends Exception{
    public UserIsAlreadyRegisteredException(String msg) {
        super(msg);
    }
}
