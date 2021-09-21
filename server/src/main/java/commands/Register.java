package commands;

import data.User;
import exceptions.*;
import utils.ResponseBuilder;
import utils.database.DatabaseUserManager;

import java.sql.SQLException;

public class Register implements Executable{
    private DatabaseUserManager databaseUserManager;

    public Register(DatabaseUserManager databaseUserManager){
        this.databaseUserManager = databaseUserManager;
    }

    public boolean execute(String str, Object arg, User user) {
        try {
            if(str.length() != 0 || arg != null) throw new WrongAmountOfElementsException("Неправильное количество аргументов для команды");
            if(databaseUserManager.insertUserToDatabase(user)) ResponseBuilder.append("Пользователь " + user.getUsername() + " зарегистрирован");
            else throw new UserIsAlreadyRegisteredException("Пользователь с таким именем уже зарегистрирован");
            return true;
        }
        catch (WrongAmountOfElementsException | UserIsAlreadyRegisteredException e){
            ResponseBuilder.appendError(e.getMessage());
            return false;
        }
        catch (SQLException e){
            ResponseBuilder.appendError("Ошибка при работе с БД");
            return false;
        }
    }
}
