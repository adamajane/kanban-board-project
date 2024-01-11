package serverSide;

import org.jspace.*;

import java.util.Arrays;

public class ServerRemoteSpace {
    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        String uri = "tcp://10.209.152.92:" + port + "/?keep";

        SpaceRepository repository = new SpaceRepository();
        repository.addGate(uri);
        Space backlog = new SequentialSpace();
//        Space doing = new SequentialSpace();
//        Space review = new SequentialSpace();
//        Space done = new SequentialSpace();
        repository.add("backlog", backlog);
//        repository.add("doing", doing);
//        repository.add("review", review);
//        repository.add("done", done);

        while (true) {
            Object[] t = backlog.query(new FormalField(String.class));
            System.out.println(t[0]);
        }
    }
}
