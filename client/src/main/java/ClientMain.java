import utils.*;
import utils.Console;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class ClientMain {

    public static void main(String[] args) {
        ClientLauncher clientLauncher = new ClientLauncher();
        clientLauncher.launch();
    }
}

