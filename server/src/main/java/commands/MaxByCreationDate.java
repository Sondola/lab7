package commands;

import data.User;
import exceptions.NoElementsInCollectionException;
import exceptions.WrongAmountOfElementsException;
import utils.CollectionManager;
import utils.ResponseBuilder;

public class MaxByCreationDate implements Executable{
    private CollectionManager collectionManager;

    public MaxByCreationDate(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public boolean execute(String str, Object arg, User user) {
        try {
            if (str.length() != 0) throw new WrongAmountOfElementsException("Неправильное количество аргументов для команды");
            if (collectionManager.collectionSize() != 0)
                System.out.println(collectionManager.maxByCreationDate().toString());
            else throw new NoElementsInCollectionException("Коллекция пуста!");
            return true;
        } catch (WrongAmountOfElementsException | NoElementsInCollectionException e) {
            ResponseBuilder.appendError(e.getMessage());
            return false;
        }
    }
}
