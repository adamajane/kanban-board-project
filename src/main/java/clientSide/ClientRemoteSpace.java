package clientSide;

import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ClientRemoteSpace {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        RemoteSpace chat = new RemoteSpace("tcp://10.209.152.92:8080/backlog?keep");

        while (true) {
            String message = scan.nextLine();
            chat.put(message);
            String msg = Arrays.toString(chat.query(new FormalField(String.class)));
            System.out.println(msg);
        }
    }
}
