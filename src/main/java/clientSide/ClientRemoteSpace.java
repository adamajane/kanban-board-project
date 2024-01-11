package clientSide;

import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ClientRemoteSpace {
    public static void main(String[] args) throws IOException, InterruptedException {

        RemoteSpace backlog = new RemoteSpace("tcp://10.209.152.92:8080/backlog?keep");
        RemoteSpace doing = new RemoteSpace("tcp://10.209.152.92:8080/doing?keep");
        RemoteSpace review = new RemoteSpace("tcp://10.209.152.92:8080/review?keep");
        RemoteSpace done = new RemoteSpace("tcp://10.209.152.92:8080/done?keep");

        backlog.put("Tuple 1");
        doing.put("Tuple 2");
        review.put("Tuple 3");
        done.put("Tuple 4");

        // while (true) {
        //     String message = scan.nextLine();
        //     backlog.put(message);
        //     String msg = Arrays.toString(backlog.query(new FormalField(String.class)));
        //     System.out.println(msg);
        // }
    }

}
