package utils;

import objects.*;

import java.net.DatagramSocket;
import java.util.logging.Logger;


public class ServerHelper {
    //private Analyzer analyzer;
    private ServerReceiver serverReceiver;
    //private ServerSender serverSender;
    private DatagramSocket socket;
    private CommandManager commandManager;
    public static Logger logger = Logger.getLogger("ServerLogger");

    public ServerHelper(ServerReceiver serverReceiver, DatagramSocket socket, CommandManager commandManager) {
        //this.analyzer = analyzer;
        this.serverReceiver = serverReceiver;
        //this.serverSender = serverSender;
        this.socket = socket;
        this.commandManager = commandManager;
    }

    public void run(){
        while(true) {
            //Request request = serverReceiver.getRequest();
            //Response response = analyzer.run();
            //if (!serverSender.send(response)) {
              //  ServerHelper.logger.info("Error while sending answer");
            //}
            Request request = serverReceiver.getRequest();
            if (!request.isEmpty()) {
                Thread readThread = new Thread(new MultithreadReader(request, socket, commandManager));
                readThread.start();
            }
        }
    }
}
