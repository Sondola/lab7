package commands;

import data.User;
import exceptions.*;
import utils.CollectionManager;
import utils.ResponseBuilder;

import java.time.format.DateTimeFormatter;

public class Info implements Executable{
    private String string = "";
    private CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public boolean execute(String str, Object humanObj, User user) {
        try{
            if(str.length() != 0){
                throw new WrongAmountOfElementsException("Неправильное количество аргументов для команды");
            }
            string = "Тип коллекции: " + collectionManager.collectionType() + "\n";
            string += "Размер коллекции: " + collectionManager.collectionSize() + "\n";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            try{
                if (collectionManager.getLastSave() == null){
                    throw new NullLastSaveException("Данная коллекция еще не сохранялась");
                }
                string += "Дата последнего сохранения: " + collectionManager.getLastSave().format(formatter) + "\n";
            }
            catch (NullLastSaveException e){
                System.out.println(e.getMessage());
            }
            string += "Дата инициализации: " + collectionManager.getLastInit().format(formatter);
            ResponseBuilder.append(string);
            return true;
        }
        catch (WrongAmountOfElementsException e){
            ResponseBuilder.appendError(e.getMessage());
            return false;
        }
    }

    public String getString() {
        return string;
    }
}
