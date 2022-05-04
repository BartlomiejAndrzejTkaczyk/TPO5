/**
 *
 *  @author Tkaczyk Bartłomiej S22517
 *
 */

package zad1;


import com.sun.source.util.TaskListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


public class ClientTask extends FutureTask<String>{
    Client client;
    List<String> reqs;
    boolean showSendRes;

    public ClientTask(Client c, List<String> reqs, boolean showSendRes) {
        super(()->task(c, reqs));
        this.client = c;
        this.reqs = reqs;
        this.showSendRes = showSendRes;
    }


    private static String task(Client client,List<String> reqs){
        client.connect();
        client.send("login " + client.getId());
        for(String req : reqs) {
            String res = client.send(req);
        }
        return client.send("bye and log transfer");
    }

    public static ClientTask create(Client c, List<String> reqs, boolean showSendRes){
        return new ClientTask(c, reqs, showSendRes);
    }


}
