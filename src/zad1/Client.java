/**
 *
 *  @author Tkaczyk Bartłomiej S22517
 *
 */

package zad1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.List;


public class Client {
    private final String host;
    private final int port;
    private final String id;
    Charset defCharset = Charset.forName("cp1250");
    SocketChannel channel;

    public String getId() {
        return id;
    }

    public Client(String host, int port, String id) {
        this.host = host;
        this.port = port;
        this.id = id;
    }

    public void connect(){
        try {
            this.channel = SocketChannel.open(new InetSocketAddress(host, port));
            this.channel.configureBlocking(true);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String send(String req){
        try {
            output(channel, req);
            return input(channel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }


    private void output(SocketChannel channel, String txt) throws IOException {
        channel.write(ByteBuffer.wrap(txt.getBytes(defCharset)));
    }

    private String input(SocketChannel in) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        CharBuffer charBuffer = null;
        in.read(byteBuffer);
        StringBuilder res = new StringBuilder();
        byteBuffer.flip();
        charBuffer = defCharset.decode(byteBuffer);
        while (charBuffer.hasRemaining()){
            res.append(charBuffer.get());
        }
        return res.toString();
    }

}
