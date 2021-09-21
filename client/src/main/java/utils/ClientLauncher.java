package utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class ClientLauncher {

    public static final int PORT = 1499;
    public static final long TIMEOUT = 1000;

    public void launch(){
        BufferedInputStream bf = new BufferedInputStream(System.in);
        BufferedReader r = new BufferedReader(new InputStreamReader(bf, StandardCharsets.UTF_8));
        AskManager askManager = new AskManager(r);
        StringWorking stringWorking = new StringWorking(askManager);
        Authorization authorization = new Authorization(r);

        try {
            InetAddress ADDR = InetAddress.getByName("localhost");
            InetSocketAddress socketAddress = new InetSocketAddress(ADDR, PORT);
            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.connect(socketAddress);
            ClientSender sender = new ClientSender(socketAddress, datagramChannel);
            ClientReceiver receiver = new ClientReceiver(datagramChannel, TIMEOUT);
            ClientHelper client = new ClientHelper(receiver, sender, stringWorking, authorization);
            Console console = new Console(r, askManager, client);
            stringWorking.addConsole(console);

            client.login();
            console.interactiveMode();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

}
