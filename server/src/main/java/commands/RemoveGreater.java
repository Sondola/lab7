package commands;

import data.HumanBeing;
import data.User;
import exceptions.*;
import utils.CollectionManager;
import utils.ResponseBuilder;

public class RemoveGreater implements Executable{
    private CollectionManager collectionManager;

    public RemoveGreater(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public boolean execute(String str, Object obj, User user) {
        try {
            if (str.length() != 0) throw new WrongAmountOfElementsException("Неправильное количество аргументов для команды");
            if (collectionManager.collectionSize() != 0) {
                HumanBeing human = (HumanBeing) obj;
                collectionManager.removeGreater(human);
            }
            else throw new NoElementsInCollectionException("Коллекция пуста!");
            return true;
        } catch (WrongAmountOfElementsException | NoElementsInCollectionException e) {
            ResponseBuilder.appendError(e.getMessage());
            return false;
        }
    }
}
