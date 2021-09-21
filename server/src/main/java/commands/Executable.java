package commands;

import data.User;

public interface Executable {
    boolean execute(String str, Object obj, User user);
}
