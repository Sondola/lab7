package utils;

import commands.*;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import exceptions.*;
import utils.database.DatabaseCollectionManager;
import utils.database.DatabaseManager;
import utils.database.DatabaseUserManager;

public class ServerLauncher {

    private static final String databaseLogin = "postgres";
    private static String databaseHost;
    private static String databasePassword;
    private static String databaseAddress;
    public static final int PORT = 1499;
    public static final Logger logger = Logger.getLogger("ServerLogger");

    public void launch(String [] args){
        if(!uploadDatabaseVariables(args)) return;
        DatabaseManager databaseManager = new DatabaseManager(databaseAddress, databaseLogin, databasePassword);
        DatabaseUserManager databaseUserManager = new DatabaseUserManager(databaseManager);
        DatabaseCollectionManager databaseCollectionManager = new DatabaseCollectionManager(databaseManager, databaseUserManager);

        CollectionManager collectionManager = new CollectionManager(databaseCollectionManager);
        CommandManager commandManager = new CommandManager(
                new Info(collectionManager),
                new Help(),
                new Show(collectionManager),
                new Add(collectionManager, databaseCollectionManager),
                new Update(collectionManager),
                new RemoveById(collectionManager),
                new Clear(collectionManager, databaseCollectionManager),
                new ExecuteScript(),
                new Exit(collectionManager),
                new AddIfMax(collectionManager, databaseCollectionManager),
                new AddIfMin(collectionManager, databaseCollectionManager),
                new RemoveGreater(collectionManager),
                new RemoveAllByCar(collectionManager),
                new MaxByCreationDate(collectionManager),
                new FilterGreaterThanSoundtrackName(collectionManager),
                new Login(databaseUserManager),
                new Register(databaseUserManager));

        //Analyzer analyzer = new Analyzer(commandManager);

        try {
            InetSocketAddress socketAddress = new InetSocketAddress(PORT);
            DatagramSocket datagramSocket = new DatagramSocket(socketAddress);

            ServerReceiver receiver = new ServerReceiver(datagramSocket);
            //ServerSender sender = new ServerSender(datagramSocket);

            ServerHelper server = new ServerHelper(receiver, datagramSocket, commandManager);
            server.run();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private boolean uploadDatabaseVariables(String [] args){
        try{
            if(args.length != 2) throw new WrongAmountOfElementsException("Неверное количество аргументов");
            databaseHost = args[0];
            databasePassword = args[1];
            databaseAddress = "jdbc:postgresql://" + databaseHost + ":5432/studs";
            //System.out.println("Хост: " + args[0] + ", пароль: " + args[1]);
            return true;
        }
        catch (WrongAmountOfElementsException e){
            logger.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

}
