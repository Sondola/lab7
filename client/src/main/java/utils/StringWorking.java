package utils;

import data.HumanBeing;
import data.HumanBeingRaw;
import data.User;
import exceptions.*;
import objects.Request;

import java.util.Stack;

/**
 * Class for refactoring the command line
 * @author Julia Polezhaeva
 * @version 1.1
 */

public class StringWorking {
    /**
     * Interaction with client
     */
    private AskManager askManager;
    /**
     * Name of scripts which are executing now
     */
    private Stack<String> scriptNames;

    private Console console;
    /**
     * Constructor for class
     * @param askManager {@link AskManager}
     */
    public StringWorking(AskManager askManager){
        this.askManager = askManager;
        scriptNames = new Stack<>();
    }

    public void addConsole(Console console) {
        this.console = console;
    }

    /**
     * Refactoring string before the execution
     * @param commandArr command line
     * @return refactored String
     * @throws IncorrectCommandException undefined command
     */
    private String refactorString(String[] commandArr) throws IncorrectCommandException {
        if(commandArr.length == 0) throw new IncorrectCommandException("Введена некорректная команда");
        String line;
        if(commandArr.length == 1){
            line = "";
        }
        else{
            line = commandArr[1];
        }
        return line;
    }

    public Request makeRequest(String command, User user) throws IncorrectCommandException {
        String[] commandArr = command.trim().split(" ", 2);
        String line = refactorString(commandArr);

        ArgumentState argumentState = analyzeCommand(commandArr[0], line);
        if (argumentState == ArgumentState.ERROR) return null;
        try{
            HumanBeingRaw human = null;
            switch(argumentState) {
                case ADD_OBJECT: {
                    human = addHuman();
                    return new Request(commandArr[0], line, human, user);
                }
                case UPDATE_OBJECT: {
                    human = updateHuman();
                    return new Request(commandArr[0], line, human, user);
                }
                case SCRIPT_MODE: {
                    for (String name : scriptNames) {
                        if (name.equals(line)) throw new RecursionScriptException("Ошибка! Рекурсивный вызов скрипта!");
                    }
                    scriptNames.push(line);
                    console.scriptMode(line);
                    scriptNames.pop();
                    break;
                }
                case EXIT: {console.setWork(false);}
            }
        } catch (RecursionScriptException | IncorrectScriptInputException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return new Request(commandArr[0], line, user);
    }

    /**
     * Connecting with {@link ArgumentState}
     * @param command command line
     * @param arg command arguments
     * @return command type
     * @throws IncorrectCommandException undefined command
     * @throws RecursionScriptException script recursion
     */
    public ArgumentState analyzeCommand(String command, String arg) {
        try {
            switch (command){
                case "help":
                case "info":
                case "show":
                case "clear":
                case "remove_greater":
                case "max_by_creation_date":
                    if (arg.length() != 0)
                        throw new WrongAmountOfElementsException("Неправильное количество аргументов!");
                    else return ArgumentState.OK;
                case "add":
                case "add_if_max":
                case "add_if_min":
                    if (arg.length() != 0)
                        throw new WrongAmountOfElementsException("Неправильное количество аргументов!");
                    else return ArgumentState.ADD_OBJECT;
                case "update":
                    if (arg.length() == 0) {
                        throw new WrongAmountOfElementsException("Неправильное количество аргументов!");
                    }
                    else return ArgumentState.UPDATE_OBJECT;
                case "execute_script":
                    if (arg.length() == 0)
                        throw new WrongAmountOfElementsException("Неправильное количество аргументов!");
                    else return ArgumentState.SCRIPT_MODE;
                case "exit":
                    if (arg.length() != 0)
                        throw new WrongAmountOfElementsException("Неправильное количество аргументов!");
                    else return ArgumentState.EXIT;
                case "remove_all_by_car":
                case "remove_by_id":
                case "filter_greater_than_soundtrack_name":
                    String [] commandArr = arg.trim().split(" ");
                    if (arg.length() == 0 || commandArr.length != 1) {
                        throw new WrongAmountOfElementsException("Неправильное количество аргументов!");
                    }
                    else return ArgumentState.OK;
                default:
                    throw new IncorrectCommandException("Введена некорректная команда! Введите help для просмотра списка команд");
            }
        } catch (WrongAmountOfElementsException | IncorrectCommandException e) {
            System.out.println(e.getMessage());
            return ArgumentState.ERROR;
        }
    }

    public HumanBeingRaw addHuman() throws IncorrectScriptInputException {
        if (!scriptNames.empty()) {
            askManager.setInteractiveMode(false);
        }
        HumanBeingRaw human = new HumanBeingRaw(askManager.askName(),
                askManager.askCoordinates(),
                askManager.askRealHero(),
                askManager.askHasToothpick(),
                askManager.askImpactSpeed(),
                askManager.askSoundtrackName(),
                askManager.askMinutesOfWaiting(),
                askManager.askMood(),
                askManager.askCar());
        askManager.setInteractiveMode(true);
        return human;
    }

    public HumanBeingRaw updateHuman() throws IncorrectScriptInputException {
        HumanBeing human = new HumanBeing();
        if(!scriptNames.empty()){
            askManager.setInteractiveMode(false);
        }
        if (askManager.questionCheck("имя")) human.setName(askManager.askName());
        else human.setName(null);
        if (askManager.questionCheck("координата Х")) human.setCoordinateX(askManager.askCoordinateX());
        else human.setCoordinateX(null);
        if (askManager.questionCheck("координата Y")) human.setCoordinateY(askManager.askCoordinateY());
        else human.setCoordinateY(0);
        if (askManager.questionCheck("реальный герой")) human.setRealHero(askManager.askRealHero());
        else human.setRealHero(false);
        if (askManager.questionCheck("зубочистка")) human.setHasToothpick(askManager.askHasToothpick());
        else human.setHasToothpick(null);
        if (askManager.questionCheck("скорость удара")) human.setImpactSpeed(askManager.askImpactSpeed());
        else human.setImpactSpeed(null);
        if (askManager.questionCheck("название песни")) human.setSoundtrackName(askManager.askSoundtrackName());
        else human.setSoundtrackName(null);
        if (askManager.questionCheck("время ожидания")) human.setMinutesOfWaiting(askManager.askMinutesOfWaiting());
        else human.setMinutesOfWaiting(0);
        if (askManager.questionCheck("настоение")) human.setMood(askManager.askMood());
        else human.setMood(null);
        if (askManager.questionCheck("название машины")) human.setCarName(askManager.askCarName());
        else human.setCarName(null);
        human.setCreationDate();
        askManager.setInteractiveMode(true);
        return new HumanBeingRaw(human.getName(),
                human.getCoordinates(),
                human.getRealHero(),
                human.getHasToothpick(),
                human.getImpactSpeed(),
                human.getSoundtrackName(),
                human.getImpactSpeed(),
                human.getMood(),
                human.getCar());
    }
}
