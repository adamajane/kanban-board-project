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
        RandomSpace requests = new RandomSpace();
        repository.add("requests", requests);
        repository.add("backlog", backlog.getColumnSpace());
        repository.add("doing", doing.getColumnSpace());
        repository.add("review", review.getColumnSpace());
        repository.add("done", done.getColumnSpace());
        repository.addGate(uri);

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
//            System.out.println("here1");
            request = requests.get(new FormalField(String.class), new FormalField(String.class), new FormalField(String.class));
//            System.out.println(request[0] + " " + request[1] + " " + request[2]);
//            List<Object[]> taskList = requests.getAll(new FormalField(String.class), new FormalField(String.class), new FormalField(String.class));
//            for (Object[] obj : taskList) {
//                String data = (String) obj[0];
//                System.out.println(data);
//            }

            function = (String) request[0];
            columnName = (String) request[1];
            requests.put(function, columnName, (String) request[2]);

            switch (function) {
                case "add":
                    System.out.println("in add");
                    arguments = requests.get(new FormalField(String.class), new FormalField(String.class), new FormalField(String.class));
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

    private static void printSpaceTasks(Space space, String spaceName) throws InterruptedException {
        List<Object[]> taskList = space.queryAll(new FormalField(String.class));
        System.out.println("Tasks in " + spaceName + ":");
        if (taskList.isEmpty()) {
            System.out.println("No tasks in " + spaceName);
        } else {
            for (Object[] obj : taskList) {
                String data = (String) obj[0];
                System.out.println(data);
            }
        }
    }

    private static void printSpaceTasks2(Space space, String spaceName) throws InterruptedException {
        List<Object[]> taskList = space.queryAll(new FormalField(String.class), new FormalField(String.class), new FormalField(String.class));
        System.out.println("Tasks in " + spaceName + ":");
        if (taskList.isEmpty()) {
            System.out.println("No tasks in " + spaceName);
        } else {
            for (Object[] obj : taskList) {
                String data = (String) obj[0];
                System.out.println(data);
            }
        }
    }

}
