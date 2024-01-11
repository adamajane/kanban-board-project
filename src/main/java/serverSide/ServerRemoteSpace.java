package serverSide;

import org.jspace.*;

import java.util.Arrays;
import java.util.List;

public class ServerRemoteSpace {

    // List<Column> columns;
    static Column backlog = new Column("backlog");

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        String uri = "tcp://localhost:" + port + "/?keep";

        SpaceRepository repository = new SpaceRepository();
        repository.addGate(uri);
        repository.add("backlog", backlog.getColumnSpace());
        // Space backlog = new SequentialSpace();
        // Space doing = new SequentialSpace();
        // Space review = new SequentialSpace();
        // Space done = new SequentialSpace();
        // repository.add("backlog", backlog);
        // repository.add("doing", doing);
        // repository.add("review", review);
        // repository.add("done", done);

        while (true) {
            Object[] t = backlog.getColumnSpace().get(new FormalField(String.class));
            System.out.println("backlog tuple:" + t[0]);

            // Object[] t1 = doing.get(new FormalField(String.class));
            // System.out.println("doing tuple:" + t1[0]);

            // Object[] t2 = review.get(new FormalField(String.class));
            // System.out.println("review tuple:" + t2[0]);

            // Object[] t3 = done.get(new FormalField(String.class));
            // System.out.println("done tuple:" + t3[0]);
        }
    }
}
