package commands;

import data.*;
import exceptions.WrongAmountOfElementsException;
import utils.*;
import utils.database.DatabaseCollectionManager;

import java.sql.SQLException;

public class AddIfMax implements Executable{
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public AddIfMax(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    public boolean execute(String str, Object humanObj, User user) {
        try {
            if(str.length() != 0){
                throw new WrongAmountOfElementsException("Неправильное количество аргументов для команды");
            }
            HumanBeingRaw human = (HumanBeingRaw) humanObj;
            if (Float.compare(human.getImpactSpeed(), collectionManager.getMaxImpactSpeed()) == 1) {
                collectionManager.add(databaseCollectionManager.insertHuman(human, user));
                ResponseBuilder.append("Элемент успешно добавлен в коллекцию!");
            }
            else ResponseBuilder.append("Элемент не был добавлен в коллекцию, так как его значение поля ImpactSpeed меньше максимального");
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
