package commands;

import data.HumanBeing;
import data.User;
import exceptions.*;
import utils.CollectionManager;
import utils.ResponseBuilder;
import utils.database.DatabaseCollectionManager;

import java.sql.SQLException;

public class Clear implements Executable{
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public Clear(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    public boolean execute(String str, Object humanObj, User user) {
        try {
            if(str.length() != 0){
                throw new WrongAmountOfElementsException("Неправильное количество аргументов для команды");
            }
            for (HumanBeing humanBeing : collectionManager.getCollection()) {
                if (!humanBeing.getCreator().equals(user)) throw new PermissionDeniedException("Недостаточно прав для удаления объектов");
            }
            databaseCollectionManager.clearCollection();
            collectionManager.clearCollection();
            ResponseBuilder.append("Коллекция очищена");
            return true;
        } catch (WrongAmountOfElementsException | PermissionDeniedException e) {
            ResponseBuilder.appendError(e.getMessage());
            return false;
        } catch (SQLException throwables) {
            ResponseBuilder.appendError("Ошибка при работе с БД");
            return false;
        }
    }
}
