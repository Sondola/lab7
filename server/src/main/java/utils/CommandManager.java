package utils;

import commands.Executable;
import data.User;

public class CommandManager {
    private Executable info;
    private Executable help;
    private Executable show;
    private Executable add;
    private Executable update;
    private Executable remove_by_id;
    private Executable clear;
    private Executable execute_script;
    private Executable exit;
    private Executable add_if_max;
    private Executable add_if_min;
    private Executable remove_greater;
    private Executable remove_all_by_car;
    private Executable max_by_creation_date;
    private Executable filter_greater_than_soundtrack_name;
    private Executable login;
    private Executable register;

    public CommandManager(Executable info,
                          Executable help,
                          Executable show,
                          Executable add,
                          Executable update,
                          Executable remove_by_id,
                          Executable clear,
                          Executable execute_script,
                          Executable exit,
                          Executable add_if_max,
                          Executable add_if_min,
                          Executable remove_greater,
                          Executable remove_all_by_car,
                          Executable max_by_creation_date,
                          Executable filter_greater_than_soundtrack_name,
                          Executable login,
                          Executable register) {
        this.info = info;
        this.help = help;
        this.show = show;
        this.add = add;
        this.update = update;
        this.remove_by_id = remove_by_id;
        this.clear = clear;
        this.execute_script = execute_script;
        this.exit = exit;
        this.add_if_max = add_if_max;
        this.add_if_min = add_if_min;
        this.remove_greater = remove_greater;
        this.remove_all_by_car = remove_all_by_car;
        this.max_by_creation_date = max_by_creation_date;
        this.filter_greater_than_soundtrack_name = filter_greater_than_soundtrack_name;
        this.login = login;
        this.register = register;
    }

    public boolean info(String str, Object obj, User user) {
        return info.execute(str, obj, user);
    }

    public boolean help(String str, Object obj, User user) {
        return help.execute(str, obj, user);
    }

    public boolean show(String str, Object obj, User user) {
        return show.execute(str, obj, user);
    }

    public boolean add(String str, Object obj, User user) {
        return add.execute(str, obj, user);
    }

    public boolean update(String str, Object obj, User user) {
        return update.execute(str, obj, user);
    }

    public boolean remove_by_id(String str, Object obj, User user) {
        return remove_by_id.execute(str, obj, user);
    }

    public boolean clear(String str, Object obj, User user) {
        return clear.execute(str, obj, user);
    }

    public boolean execute_script(String str, Object obj, User user) {
        return execute_script.execute(str, obj, user);
    }
    public boolean exit(String str, Object obj, User user) {
        return exit.execute(str, obj, user);
    }
    public boolean add_if_max(String str, Object obj, User user) {
        return add_if_max.execute(str, obj, user);
    }
    public boolean add_if_min(String str, Object obj, User user) {
        return add_if_min.execute(str, obj, user);
    }
    public boolean remove_greater(String str, Object obj, User user) {
        return remove_greater.execute(str, obj, user);
    }
    public boolean remove_all_by_car(String str, Object obj, User user) {
        return remove_all_by_car.execute(str, obj, user);
    }
    public boolean max_by_creation_date(String str, Object obj, User user) {
        return max_by_creation_date.execute(str, obj, user);
    }
    public boolean filter_greater_than_soundtrack_name(String str, Object obj,User user) {
        return filter_greater_than_soundtrack_name.execute(str, obj, user);
    }

    public boolean login(String str, Object obj, User user) {
        return login.execute(str, obj, user);
    }

    public boolean register(String str, Object obj, User user) {
        return register.execute(str, obj, user);
    }
}
