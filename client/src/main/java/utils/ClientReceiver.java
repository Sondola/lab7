package utils;

import objects.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

public class ClientReceiver {
    private DatagramChannel datagramChannel;
    private ByteBuffer buffer = ByteBuffer.allocate(8192);
    private long timeout;

    public ClientReceiver(DatagramChannel datagramChannel, long timeout){
        this.datagramChannel = datagramChannel;
        this.timeout = timeout;
    }

    public Response getResponse() {
        try {
            Selector selector = Selector.open();
            datagramChannel.configureBlocking(false);

            datagramChannel.register(selector, SelectionKey.OP_READ);

            long startTime = System.currentTimeMillis();

            while(true) {
                /*if (System.currentTimeMillis() - startTime > timeout) {
                    selector.close();
                    System.out.println("Server is not available now");
                }*/

                if (selector.selectNow() > 0) {
                    Set<SelectionKey> selectionKeys = selector.keys();
                    for (SelectionKey selectionKey : selectionKeys) {
                        if (selectionKey.isReadable()) {
                            datagramChannel.receive(buffer);
                            buffer.flip();

                            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
                            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

                            Response response = (Response) objectInputStream.readObject();

                            objectInputStream.close();
                            byteArrayInputStream.close();
                            buffer.clear();
                            selector.close();
                            return response;
                        } else {
                            System.out.println("Unreadable");
                        }
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Answer isn't received");
            return null;
        }
    }
}
