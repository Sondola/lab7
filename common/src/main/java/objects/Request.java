package objects;

import data.User;

import java.io.Serializable;

public class Request implements Serializable {
    private String command;
    private String commandArg;
    private Object obj;
    private User user;

    public Request(String command, String commandArg, Object obj, User user) {
        this.command = command;
        this.commandArg = commandArg;
        this.obj = obj;
        this.user = user;
    }

    public Request(String command, String commandArr, User user) {
        this(command, commandArr, null, user);
    }

    public Request(String command, User user) {
        this(command, "", null, user);
    }

    public String getCommandString() {
        return command;
    }

    public String getCommandArg() {return commandArg;}

    public Object getObject() {return obj;}

    public User getUser() {
        return user;
    }

    public boolean isEmpty() {
        return command.isEmpty();
    }

    @Override
    public String toString() {
        return "Request[" + command + "]\n" + "Argument: " + commandArg + "\nObject: " + obj;
    }
}
