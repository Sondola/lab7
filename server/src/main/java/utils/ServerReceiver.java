package utils;

import objects.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerReceiver {
    private DatagramSocket serverSocket;
    private DatagramPacket packet;
    private byte bytes[] = new byte[8192];

    public ServerReceiver(DatagramSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Request getRequest() {
        packet = new DatagramPacket(bytes, bytes.length);
        try {
            serverSocket.receive(packet);

            ServerSender.setClientAddr(packet.getAddress());
            ServerSender.setClientPort(packet.getPort());

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Request request = (Request) objectInputStream.readObject();

            byteArrayInputStream.close();
            objectInputStream.close();

            ServerHelper.logger.info("Package received" + request.toString());
            bytes = new byte[8192];
            return request;
        } catch (IOException | ClassNotFoundException e) {
            ServerHelper.logger.info("In/out error");
            bytes = new byte[8192];
            return null;
        }
    }
}
