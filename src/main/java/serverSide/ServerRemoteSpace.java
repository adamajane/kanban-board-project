package serverSide;

import org.jspace.*;

import java.util.Arrays;

public class ServerRemoteSpace {
    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        String uri = "tcp://10.209.152.92:" + port + "/?keep";

        SpaceRepository repository = new SpaceRepository();
        repository.addGate(uri);
        Space space = new SequentialSpace();
        repository.add("space", space);

        while (true) {
            Object[] t = space.query(new FormalField(String.class), new FormalField(String.class));
            System.out.println(t[0]);
        }
    }
}
