package commands;

import data.User;
import exceptions.*;
import utils.ResponseBuilder;
import utils.database.DatabaseUserManager;

import java.sql.SQLException;

public class Login implements  Executable{
    private DatabaseUserManager databaseUserManager;

    public Login(DatabaseUserManager databaseUserManager){
        this.databaseUserManager = databaseUserManager;
    }

    public boolean execute(String str, Object arg, User user) {
        try {
            if (str.length() != 0 || arg != null)
                throw new WrongAmountOfElementsException("Неправильное количество аргументов для операции");
            if(databaseUserManager.checkUserByUsernameAndPassword(user)){
                ResponseBuilder.append("Пользователь " + user.getUsername() + " авторизован");
            }
            else throw new UserNotFoundException("Пользователя с таким логином и паролем нет в базе");
            return true;
        }
        catch (WrongAmountOfElementsException | UserNotFoundException e){
            ResponseBuilder.appendError(e.getMessage());
            return false;
        }
        catch (SQLException e){
            ResponseBuilder.appendError("Ошибка при работе с БД");
            return false;
        }
    }

}

