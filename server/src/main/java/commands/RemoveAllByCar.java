package commands;

import data.*;
import exceptions.*;
import utils.CollectionManager;
import utils.ResponseBuilder;

import java.time.LocalDateTime;

public class RemoveAllByCar implements Executable{
    private CollectionManager collectionManager;

    public RemoveAllByCar(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public boolean execute(String str, Object humanObj, User user) {
        try {
            if (str.length() != 0) throw new WrongAmountOfElementsException("Неправильное количество аргументов для команды");
            Car car = (Car) humanObj;
            if (collectionManager.collectionSize() != 0) {
                collectionManager.removeByCar(car, user);
            } else throw new NoElementsInCollectionException("Коллекция пуста!");
            return true;
        } catch (WrongAmountOfElementsException | NoElementsInCollectionException | PermissionDeniedException e) {
            ResponseBuilder.appendError(e.getMessage());
            return false;
        }
    }
}
