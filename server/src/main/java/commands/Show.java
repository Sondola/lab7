package commands;

import data.User;
import exceptions.WrongAmountOfElementsException;
import utils.CollectionManager;
import utils.ResponseBuilder;

public class Show implements Executable{
    private String string = "";
    private CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public boolean execute(String str, Object obj, User user) {
        try {
            if (str.length() != 0) {
                throw new WrongAmountOfElementsException("Неправильное количество аргументов для команды");
            }
            string = collectionManager.showCollection();
            ResponseBuilder.append(string);
            return true;
        } catch (WrongAmountOfElementsException e) {
            ResponseBuilder.appendError(e.getMessage());
            return false;
        }
    }
}
