package commands;

import data.User;
import exceptions.*;
import utils.CollectionManager;
import utils.ResponseBuilder;

public class FilterGreaterThanSoundtrackName implements Executable{
    private CollectionManager collectionManager;

    public FilterGreaterThanSoundtrackName(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public boolean execute(String str, Object humanObj, User user) {
        try {
            if(str.length() == 0){
                throw new WrongAmountOfElementsException("Неправильное количество аргументов для команды");
            }
            String soundtrackName = humanObj.toString();
            if (collectionManager.collectionSize() != 0)
                collectionManager.showCollection(collectionManager.filterGreaterThanSoundtrackName(soundtrackName));
            else throw new NoElementsInCollectionException("Коллекция пуста!");
            return true;
        } catch (WrongAmountOfElementsException | NoElementsInCollectionException e) {
            ResponseBuilder.appendError(e.getMessage());
            return false;
        } /*catch (IncorrectScriptInputException e) {
            System.out.println("Не удалось выполнить скрипт! Введены некорректные данные!");
            return false;
        }*/
    }
}
