package commands;

import data.*;
import exceptions.WrongAmountOfElementsException;
import utils.CollectionManager;
import utils.ResponseBuilder;
import utils.database.DatabaseCollectionManager;

import java.sql.SQLException;

public class Add implements Executable{
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public Add(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    public boolean execute(String str, Object human, User user) {
        try {
            if(str.length() != 0){
                throw new WrongAmountOfElementsException("Неправильное количество аргументов для команды");
            }
            collectionManager.add(databaseCollectionManager.insertHuman((HumanBeingRaw) human, user));
            ResponseBuilder.append("Элемент успешно добавлен в коллекцию!");
            return true;
        } catch (WrongAmountOfElementsException e) {
            ResponseBuilder.appendError(e.getMessage());
            return false;
        } catch (SQLException e) {
            ResponseBuilder.appendError("Ошибка при работе с БД");
            return false;
        } catch (ClassCastException e) {
            ResponseBuilder.appendError("Переданный клиентом объект некорректен");
            return false;
        }
    }
}
