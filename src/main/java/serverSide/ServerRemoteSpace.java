package serverSide;

import org.jspace.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static util.Config.IP_ADDRESS;

public class ServerRemoteSpace {

    // List<Column> columns;
    static Column backlog = new Column("backlog");
    static Column doing = new Column("doing");
    static Column review = new Column("review");
    static Column done = new Column("done");



    public static void main(String[] args) throws InterruptedException {

        Object[] request;
        Object[] arguments;
        String columnName;
        String function;

        int port = 8080;
        String uri = "tcp://" + IP_ADDRESS + ":" + port + "/?keep";

        SpaceRepository repository = new SpaceRepository();
        repository.addGate(uri);
        Space requestSpace = new SequentialSpace();
        repository.add("requests", requestSpace);
        repository.add("backlog", backlog.getColumnSpace());
        repository.add("doing", doing.getColumnSpace());
        repository.add("review", review.getColumnSpace());
        repository.add("done", done.getColumnSpace());

        // Only for testing
//        while (true) {
//            Object[] t = backlog.getColumnSpace().get(new FormalField(String.class));
//            System.out.println("Backlog tuple: " + t[0]);
//            if (t != null) {
//                backlog.getColumnSpace().put(t[0]);
//            }
//
//            Object[] t1 = doing.getColumnSpace().get(new FormalField(String.class));
//            System.out.println("Doing tuple: " + t1[0]);
//            if (t1 != null) {
//                doing.getColumnSpace().put(t1[0]);
//            }
//
//            Object[] t2 = review.getColumnSpace().get(new FormalField(String.class));
//            System.out.println("Review tuple: " + t2[0]);
//            if (t2 != null) {
//                review.getColumnSpace().put(t2[0]);
//            }
//
//            Object[] t3 = done.getColumnSpace().get(new FormalField(String.class));
//            System.out.println("Done tuple: " + t3[0]);
//            if (t3 != null) {
//                done.getColumnSpace().put(t3[0]);
//            }
//        }

        // Keep serving requests to enter chatrooms
        while (true) {
            request = requestSpace.get(new FormalField(String.class), new ActualField(String.class), new FormalField(String.class));
            function = (String) request[0];
            columnName = (String) request[1];
            switch (function) {
                case "add":
                    arguments = requestSpace.get(new ActualField(String.class), new ActualField(String.class), new FormalField(String.class));
                    System.out.println("add");
                    if (Objects.equals(columnName, "backlog")) {
                        System.out.println("adding to " + arguments[2] + " backlog");
                        backlog.getColumnSpace().put((String) arguments[2]);
                    }
                    if (Objects.equals(columnName, "doing")) {
                        System.out.println("adding to " + arguments[2] + " doing");
                        doing.getColumnSpace().put((String) arguments[2]);
                    }
                    if (Objects.equals(columnName, "review")) {
                        System.out.println("adding to " + arguments[2] + " review");
                        review.getColumnSpace().put((String) arguments[2]);
                    }
                    if (Objects.equals(columnName, "done")) {
                        System.out.println("adding to " + arguments[2] + " done");
                        done.getColumnSpace().put((String) arguments[2]);
                    }
//                case "bar":
//                    arguments = rpc.get(new ActualField(callID), new ActualField("args"), new FormalField(String.class), new FormalField(String.class));
//                    rpc.put(callID, "result", bar((String) arguments[2], (String) arguments[3]));
                default:
                    // ignore RPC for unknown functions
                    continue;
            }
        }
    }

    public static void addTask() {
        Scanner input = new Scanner(System.in);

        System.out.println("Choose the column you want to add the task to:");
        System.out.println("1. Backlog");
        System.out.println("2. Doing");
        System.out.println("3. Review");
        System.out.println("4. Done");

        int columnChoice = input.nextInt(); // TODO: Fix InputMismatchException here
        input.nextLine(); // This is necessary to consume the newline after the integer input

        System.out.println("Please enter the name of the task:");
        String taskName = input.nextLine();

        try {
            switch (columnChoice) {
                case 1:
                    backlog.getColumnSpace().put(taskName);
                    System.out.println("Task added to Backlog");
                    break;
                case 2:
                    doing.getColumnSpace().put(taskName);
                    System.out.println("Task added to Doing");
                    break;
                case 3:
                    review.getColumnSpace().put(taskName);
                    System.out.println("Task added to Review");
                    break;
                case 4:
                    done.getColumnSpace().put(taskName);
                    System.out.println("Task added to Done");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
