import commands.*;
import utils.*;

import java.net.*;
import java.util.logging.Logger;

public class ServerMain {

    public static void main(String[] args) {
        ServerLauncher serverLauncher = new ServerLauncher();
        serverLauncher.launch(args);
    }
}
