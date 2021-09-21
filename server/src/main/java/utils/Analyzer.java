package utils;

import data.User;
import objects.Request;
import objects.Response;
import objects.ResponseAnswer;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Analyzer implements Runnable{
    private Request request;
    private CommandManager commandManager;
    private ServerSender sender;
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public Analyzer(Request request, CommandManager commandManager, ServerSender sender) {
        this.request = request;
        this.commandManager = commandManager;
        this.sender = sender;
    }

    public void run() {
        User user = new User(request.getUser().getUsername(), PasswordHasher.hashPassword(request.getUser().getPassword()));
        ResponseAnswer responseAnswer = analyze(request.getCommandString(), request.getCommandArg(), request.getObject(), user);
        Response response = new Response(responseAnswer, ResponseBuilder.getBuilder());
        ResponseBuilder.delete();
        cachedThreadPool.submit( () -> sender.send(response));
    }

    public synchronized ResponseAnswer analyze(String commandName, String commandArg, Object commandObject, User user) {
        switch(commandName.toUpperCase(Locale.ROOT)) {
            case "HELP":
                if(!commandManager.help(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "INFO":
                if(!commandManager.info(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "SHOW":
                if(!commandManager.show(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "ADD":
                if(!commandManager.add(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "ADD_IF_MAX":
                if(!commandManager.add_if_max(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "ADD_IF_MIN":
                if(!commandManager.add_if_min(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "UPDATE":
                if(!commandManager.update(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "REMOVE_BY_ID":
                if(!commandManager.remove_by_id(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "REMOVE_ALL_BY_CAR":
                if(!commandManager.remove_all_by_car(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "CLEAR":
                if(!commandManager.clear(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "EXECUTE_SCRIPT":
                if(!commandManager.execute_script(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "EXIT":
                if(!commandManager.exit(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "MAX_BY_CREATION_DATE":
                if (!commandManager.max_by_creation_date(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "REMOVE_GREATER":
                if(!commandManager.remove_greater(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "FILTER_GREATER_THAN_SOUNDTRACK_NAME":
                if(!commandManager.filter_greater_than_soundtrack_name(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "LOGIN":
                if(!commandManager.login(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            case "REGISTER":
                if(!commandManager.register(commandArg, commandObject, user)) return ResponseAnswer.ERROR;
                else return ResponseAnswer.OK;
            default:
                ResponseBuilder.appendError("Command " + commandName + " is not available");
                return ResponseAnswer.ERROR;
        }
    }
}
