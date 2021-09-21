package utils;

import objects.Response;
import utils.ServerHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerSender {
    private DatagramSocket serverSocket;
    private byte bytes[] = new byte[8192];
    private static InetAddress clientAddr;
    private static int clientPort;

    public ServerSender(DatagramSocket ds) {
        serverSocket = ds;
    }

    public static void setClientAddr(InetAddress newClientAddr) {
        clientAddr = newClientAddr;
    }

    public static void setClientPort(int port){
        clientPort = port;
    }

    public boolean send(Response serverResponse) {
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(serverResponse);
            objectOutputStream.flush();

            bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();

            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, clientAddr, clientPort);
            serverSocket.send(packet);
            ServerHelper.logger.info("Package sent: " + clientAddr + " " + clientPort);
            bytes = new byte[8192];
            return true;
        } catch (IOException e) {
            ResponseBuilder.appendError("In/out error");
            return false;
        }
    }
}
