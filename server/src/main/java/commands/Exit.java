package commands;

import data.User;
import exceptions.WrongAmountOfElementsException;
import utils.CollectionManager;
import utils.ResponseBuilder;

import javax.xml.stream.XMLStreamException;

public class Exit implements Executable{
    private CollectionManager collectionManager;

    public Exit(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public boolean execute(String str, Object obj, User user) {
        try {
            if (str.length() != 0) throw new WrongAmountOfElementsException("Неправильное количество аргументов к команде!");
            //collectionManager.saveCollection();
            return true;
        //} catch (XMLStreamException e) {
          //  return false;
        } catch (WrongAmountOfElementsException e) {
            ResponseBuilder.appendError(e.getMessage());
            return false;
        }
    }
}
