package clientSide;

import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ClientRemoteSpace {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Welcome! Choose your board");
        Scanner input = new Scanner(System.in);
        String name = input.nextLine();

        RemoteSpace chat = new RemoteSpace("tcp://10.209.152.92:8080/" + name + "?keep");

        System.out.println("You're in " + chat.toString());
        while (true) {
            String message = input.nextLine();
            chat.put(message);
            String msg = Arrays.toString(chat.query(new FormalField(String.class)));
            System.out.println(msg);
        }
    }
}
