/**
 *
 *  @author Tkaczyk Bartłomiej S22517
 *
 */

package zad1;


import standardString.Commend;
import standardString.LogFormat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    String host;
    int port;
    ExecutorService exeServer = Executors.newFixedThreadPool(1);
    ExecutorService exeClientHandel = Executors.newCachedThreadPool();
    ServerSocketChannel serverChannel;
    Charset defCharset = Charset.forName("cp1250");
    List<String> serverLogs = Collections.synchronizedList(new ArrayList<String>());
    public Server(String host, int port){
        this.host = host;
        this.port = port;
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(host, port));
            serverChannel.configureBlocking(true);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void startServer() {
         exeServer.execute(this);
    }

    public void stopServer(){
        try {
            serverChannel.close();
        } catch (IOException ignored){

        }
        exeServer.shutdownNow();
    }

    @Override
    public void run() {
        try {
            while (!exeServer.isShutdown()) {
                SocketChannel client = serverChannel.accept();
                exeClientHandel.execute(new ClientHandel(client, defCharset, serverLogs));
            }
        } catch (IOException ignored){

        } finally {
            stopServer();
        }
    }

    public String getServerLog() {
        StringBuilder res = new StringBuilder();
        serverLogs.forEach(res::append);
        return res.toString();
    }
}

class ClientHandel implements Runnable{
    List<String> logs = new ArrayList<>();
    List<String> serverLogs;
    SocketChannel client;
    Commend cmd = new Commend();
    LogFormat lf = new LogFormat();
    String id;
    Charset defCharset;
    public ClientHandel(SocketChannel client, Charset defCharset, List<String> serverLogs) {
        this.client = client;
        this.defCharset = defCharset;
        this.serverLogs = serverLogs;
    }

    @Override
    public void run() {
        boolean running = true;
        try {
            //
            while (running) {
                String reply = "";
                String req = input();
                if (cmd.isLoginReq(req)){
                  reply = cmd.loginReply();
                  id = req.split(" ")[1];
                } else if (cmd.isDate(req)) {
                    reply = cmd.dateReply(req);
                } else if (cmd.isByeAndLog(req)) {
                    StringBuilder temp = new StringBuilder();
                    logs.forEach(temp::append);
                    reply = temp.toString();
                    running = false;
                } else if (cmd.isBye(req)) {
                    reply = cmd.byeReply();
                    running = false;
                }
                addLogServer(req);
                output(reply);
            }
        } catch (IOException e){
            logOut(e.toString());
        }
    }

    private void output(String txt) throws IOException {
        SocketChannel channel = this.client;
        channel.write(ByteBuffer.wrap(txt.getBytes(StandardCharsets.UTF_8)));
        addLog(txt);
    }
    private String input() throws IOException {
        SocketChannel in = this.client;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        CharBuffer charBuffer = null;
        Charset charset = Charset.forName("UTF-8");
        in.read(byteBuffer);
        StringBuilder res = new StringBuilder();
        byteBuffer.flip();
        charBuffer = charset.decode(byteBuffer);
        while (charBuffer.hasRemaining()){
            res.append(charBuffer.get());
        }
        addLog(res.toString());

        return res.toString();
    }

    public void addLog(String lien){
        if (cmd.isLoginReq(lien)){
            logs.add(lf.startLog(lien.split(" ")[1]));
        } else if(cmd.isLoginReplay(lien)){
            logs.add(lf.loginLog());
        } else if (cmd.isDate(lien)){
            logs.add(lf.request(lien));
        } else if (cmd.isByeAndLog(lien)){
            logs.add(lf.logoutLog());
            logs.add(lf.endLog(id));
        } else {
            logs.add(lf.reply(lien));
        }

    }
    public void addLogServer(String lien){
        StringBuilder newLog = new StringBuilder();
        newLog.append(id).append(" ");
        if (cmd.isLoginReq(lien)){
            newLog.append(cmd.loginReply());
        } else if (cmd.isDate(lien)){
            newLog.append(cmd.request);
        } else if (cmd.isByeAndLog(lien)){
            newLog.append(cmd.byeReply());
        } else {
            newLog.append(cmd.byeReply());
        }
        newLog.append(" at ").append("10:27:40.896736500"); //todo
        if (cmd.isDate(lien)) {
            newLog.append(": ").append('"').append(lien).append('"');
        }
        newLog.append(System.lineSeparator());
        serverLogs.add(newLog.toString());
    }
    public static void logOut(String ... arr){
        for (String ele :arr){
            System.out.print("S: "+ele);
        }
        System.out.println();
    }
}