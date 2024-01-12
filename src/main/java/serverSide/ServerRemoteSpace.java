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
        String clientName;

        int port = 8080;
        String uri = "tcp://" + IP_ADDRESS + ":" + port + "/?keep";

        SpaceRepository repository = new SpaceRepository();
        SequentialSpace requests = new SequentialSpace();
        SequentialSpace responses = new SequentialSpace();
        repository.add("requests", requests);
        repository.add("responses", responses);
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

        while (true) {
//            System.out.println("here1");
            request = requests.get(new FormalField(String.class), new FormalField(String.class), new FormalField(String.class), new FormalField(String.class));
            function = (String) request[0];
            columnName = (String) request[1];
            clientName = (String) request[3];
            requests.put(function, columnName, (String) request[2], clientName);

            switch (function) {
                case "add":
                    arguments = requests.get(new FormalField(String.class), new FormalField(String.class), new FormalField(String.class), new FormalField(String.class));
                    if (Objects.equals(columnName, "backlog")) {
                        System.out.println(arguments[3] + " added " + arguments[2] + " to backlog");
                        backlog.getColumnSpace().put((String) arguments[2]);
                        responses.put((String) clientName,"ok");
                    }
                    else if (Objects.equals(columnName, "doing")) {
                        System.out.println(arguments[3] + " added " + arguments[2] + " to doing");
                        doing.getColumnSpace().put((String) arguments[2]);
                        responses.put((String) clientName,"ok");
                    }
                    else if (Objects.equals(columnName, "review")) {
                        System.out.println(arguments[3] + " added " + arguments[2] + " to review");
                        review.getColumnSpace().put((String) arguments[2]);
                        responses.put((String) clientName,"ok");
                    }
                    else if (Objects.equals(columnName, "done")) {
                        System.out.println(arguments[3] + " added " + arguments[2] + " to done");
                        done.getColumnSpace().put((String) arguments[2]);
                        responses.put((String) clientName,"ok");
                    } else {
                        responses.put((String) clientName,"ko");
                    }
                    break;
                case "remove":
                    arguments = requests.get(new FormalField(String.class), new FormalField(String.class), new FormalField(String.class), new FormalField(String.class));
                    String removeArgument = (String) arguments[2];
                    if (Objects.equals(columnName, "backlog")) {
                        System.out.println(arguments[3] + " removed " + arguments[2] + " from backlog");
                        backlog.getColumnSpace().get(new ActualField(removeArgument));
                        responses.put((String) clientName,"ok");
                    }
                    else if (Objects.equals(columnName, "doing")) {
                        System.out.println(arguments[3] + " removed " + arguments[2] + " from doing");
                        doing.getColumnSpace().get(new ActualField(removeArgument));
                        responses.put((String) clientName,"ok");
                    }
                    else if (Objects.equals(columnName, "review")) {
                        System.out.println(arguments[3] + " removed " + arguments[2] + " from review");
                        review.getColumnSpace().get(new ActualField(removeArgument));
                        responses.put((String) clientName,"ok");
                    }
                    else if (Objects.equals(columnName, "done")) {
                        System.out.println(arguments[3] + " removed " + arguments[2] + " from done");
                        done.getColumnSpace().get(new ActualField(removeArgument));
                        responses.put((String) clientName,"ok");
                    }
                    else {
                        responses.put((String) clientName, "ko");
                    }
                    break;
                default:
                    // ignore RPC for unknown functions
                    continue;
            }
        }
    }
}
