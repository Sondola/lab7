package commands;

import data.*;
import exceptions.*;
import utils.CollectionManager;
import utils.ResponseBuilder;
import utils.database.DatabaseCollectionManager;

import java.sql.SQLException;

public class AddIfMin implements Executable{
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public AddIfMin(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    public boolean execute(String str, Object humanObj, User user) {
        try {
            if(str.length() != 0){
                throw new WrongAmountOfElementsException("Неправильное количество аргументов для команды");
            }
            HumanBeingRaw human = (HumanBeingRaw) humanObj;
            if (collectionManager.collectionSize() != 0) {
                if (human.getImpactSpeed() < collectionManager.getMinImpactSpeed()) {
                    collectionManager.add(databaseCollectionManager.insertHuman(human, user));
                    ResponseBuilder.append("Элемент успешно добавлен в коллекцию!");
                } else
                    ResponseBuilder.append("Элемент не был добавлен в коллекцию, так как его значение поля ImpactSpeed больше минимального");
            } else throw new NoElementsInCollectionException("В коллекции нет элементов для сравнения!");
            return true;
        } catch (WrongAmountOfElementsException e) {
            ResponseBuilder.appendError(e.getMessage());
            return false;
        } catch (NoElementsInCollectionException e) {
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
