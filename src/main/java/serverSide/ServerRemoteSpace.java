package serverSide;

import org.jspace.*;

import java.util.Arrays;
import java.util.List;

public class ServerRemoteSpace {

    // List<Column> columns;
    static Column backlog = new Column("backlog");
    static Column doing = new Column("doing");
    static Column review = new Column("review");
    static Column done = new Column("done");

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        String uri = "tcp://127.0.0.1:" + port + "/?keep";

        SpaceRepository repository = new SpaceRepository();
        repository.addGate(uri);
        repository.add("backlog", backlog.getColumnSpace());
        repository.add("doing", doing.getColumnSpace());
        repository.add("review", review.getColumnSpace());
        repository.add("done", done.getColumnSpace());

        while (true) {
            Object[] t = backlog.getColumnSpace().get(new FormalField(String.class));
            System.out.println("backlog tuple:" + t[0]);
            if (t != null) {
                backlog.getColumnSpace().put(t[0]);
            }

            Object[] t1 = doing.getColumnSpace().get(new FormalField(String.class));
            System.out.println("doing tuple:" + t1[0]);

            Object[] t2 = review.getColumnSpace().get(new FormalField(String.class));
            System.out.println("review tuple:" + t2[0]);

            Object[] t3 = done.getColumnSpace().get(new FormalField(String.class));
            System.out.println("done tuple:" + t3[0]);
        }
    }
}
