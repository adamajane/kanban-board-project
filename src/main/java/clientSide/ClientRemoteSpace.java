package clientSide;

import org.jspace.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClientRemoteSpace {

    static RemoteSpace backlog;
    static RemoteSpace doing;
    static RemoteSpace review;
    static RemoteSpace done;

    public static void main(String[] args) throws IOException, InterruptedException {

        backlog = new RemoteSpace("tcp://localhost:8080/backlog?keep");
        // doing = new RemoteSpace("tcp://10.209.152.92:8080/doing?keep");
        // review = new RemoteSpace("tcp://10.209.152.92:8080/review?keep");
        // done = new RemoteSpace("tcp://10.209.152.92:8080/done?keep");

        //backlog.put("Tuple 1");
        //add
        //
        // backlog.put("Tuple 9");
        //doing.put("Tuple 2");
        //review.put("Tuple 3");
        //done.put("Tuple 4");
        add(backlog, new Tuple("Tuple 5"));
        // welcomeScreen();
        // while (true) {
        //     String message = scan.nextLine();
        //     backlog.put(message);
        //     String msg = Arrays.toString(backlog.query(new FormalField(String.class)));
        //     System.out.println(msg);
        // }
    }

    public static void add(Space space, Tuple task) throws InterruptedException {
        space.put(task.getTuple());
        //System.out.println("Added: " + task + " to Backlog");
    }

    public static void welcomeScreen() throws InterruptedException {
        System.out.println("Welcome to the KanPlan!");
        System.out.println(" ");
        //System.out.println("Backlog");
        List<Object[]> taskList = backlog.queryAll(new FormalField(String.class));
        System.out.println("Tasks in Backlog:");
        for (Object[] obj : taskList) {
            //if (obj.length == 0) {
            //    System.out.println("No tasks in Backlog");
            //} else {
            System.out.println(Arrays.toString(obj));
            //}
            //System.out.println(Arrays.toString(obj));
        }


        System.out.println("Please select an option:");
        System.out.println("1. Add a task");
        System.out.println("2. Move a task");
        System.out.println("3. Exit");
    }

}
