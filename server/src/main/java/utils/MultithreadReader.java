package utils;


import objects.Request;

import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadReader implements Runnable{

    private DatagramSocket socket;
    private ServerReceiver receiver;
    private Request request;
    private CommandManager commandManager;
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public MultithreadReader(Request request, DatagramSocket socket, CommandManager commandManager){
        //this.receiver = receiver;
        this.commandManager = commandManager;
        this.socket = socket;
        this.request = request;
    }

    @Override
    public void run() {
        //Request request = receiver.getRequest();
        ServerSender sender = new ServerSender(socket);
        cachedThreadPool.submit(new Analyzer(request, commandManager, sender));
        //Thread handleThr = new Thread(new Analyzer(request, commandManager, sender));
        //handleThr.start();
    }
}
