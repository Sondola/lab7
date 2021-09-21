package utils;

import objects.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ClientSender {
    private InetSocketAddress socketAdd;
    private ByteBuffer buffer = ByteBuffer.allocate(8192);
    private DatagramChannel datagramChannel;
    //private Selector selector;

    public ClientSender(InetSocketAddress socketAddress, DatagramChannel datagramChannel) {
        this.socketAdd = socketAddress;
        this.datagramChannel =datagramChannel;
        //this.selector = selector;
    }

    public boolean send(Request request) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream= new ObjectOutputStream(byteArrayOutputStream)){

            objectOutputStream.writeObject(request);

            buffer.put(byteArrayOutputStream.toByteArray());
            buffer.flip();

            datagramChannel.send(buffer, socketAdd);

            buffer.clear();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Package isn't sent");
            return false;
        }
    }
}
