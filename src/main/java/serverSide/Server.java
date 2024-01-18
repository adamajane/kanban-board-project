package serverSide;

import org.jspace.*;

import java.util.Objects;

import static util.Config.IP_ADDRESS;

public class Server {

    static Column backlog = new Column("backlog");
    static Column doing = new Column("doing");
    static Column review = new Column("review");
    static Column done = new Column("done");


    public void run() {

        Object[] request;
        Object[] arguments;
        String columnName;
        String function;
        String clientName;
        String taskName;
        String toColumn;

        try {

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


            while (true) {
                request = requests.get(new FormalField(String.class), new FormalField(String.class), new FormalField(String.class), new FormalField(String.class), new FormalField(String.class));
                function = (String) request[0];
                columnName = (String) request[1];
                taskName = (String) request[2];
                clientName = (String) request[3];
                toColumn = (String) request[4];
                requests.put(function, columnName, taskName, clientName, toColumn);

                switch (function) {
                    case "add":
                        arguments = requests.get(new FormalField(String.class), new FormalField(String.class), new FormalField(String.class), new FormalField(String.class), new FormalField(String.class));
                        if (Objects.equals(columnName, "backlog")) {
                            System.out.println(arguments[3] + " added " + arguments[2] + " to Backlog");
                            backlog.getColumnSpace().put((String) arguments[2]);
                            responses.put((String) clientName, "ok");
                        } else if (Objects.equals(columnName, "doing")) {
                            System.out.println(arguments[3] + " added " + arguments[2] + " to Doing");
                            doing.getColumnSpace().put((String) arguments[2]);
                            responses.put((String) clientName, "ok");
                        } else if (Objects.equals(columnName, "review")) {
                            System.out.println(arguments[3] + " added " + arguments[2] + " to Review");
                            review.getColumnSpace().put((String) arguments[2]);
                            responses.put((String) clientName, "ok");
                        } else if (Objects.equals(columnName, "done")) {
                            System.out.println(arguments[3] + " added " + arguments[2] + " to Done");
                            done.getColumnSpace().put((String) arguments[2]);
                            responses.put((String) clientName, "ok");
                        } else {
                            responses.put((String) clientName, "ko");
                        }
                        break;
                    case "remove":
                        arguments = requests.get(new FormalField(String.class), new FormalField(String.class), new FormalField(String.class), new FormalField(String.class), new FormalField(String.class));
                        String removeArgument = (String) arguments[2];
                        if (Objects.equals(columnName, "backlog")) {
                            System.out.println(arguments[3] + " removed " + arguments[2] + " from Backlog");
                            backlog.getColumnSpace().get(new ActualField(removeArgument));
                            responses.put((String) clientName, "ok");
                        } else if (Objects.equals(columnName, "doing")) {
                            System.out.println(arguments[3] + " removed " + arguments[2] + " from Doing");
                            doing.getColumnSpace().get(new ActualField(removeArgument));
                            responses.put((String) clientName, "ok");
                        } else if (Objects.equals(columnName, "review")) {
                            System.out.println(arguments[3] + " removed " + arguments[2] + " from Review");
                            review.getColumnSpace().get(new ActualField(removeArgument));
                            responses.put((String) clientName, "ok");
                        } else if (Objects.equals(columnName, "done")) {
                            System.out.println(arguments[3] + " removed " + arguments[2] + " from Done");
                            done.getColumnSpace().get(new ActualField(removeArgument));
                            responses.put((String) clientName, "ok");
                        } else {
                            responses.put((String) clientName, "ko");
                        }
                        break;
                    case "move":
                        arguments = requests.get(new FormalField(String.class), new FormalField(String.class), new FormalField(String.class), new FormalField(String.class), new FormalField(String.class));
                        String fromColumn = (String) arguments[1];
                        toColumn = (String) arguments[4];
                        Space fromSpace = getSpaceFromChoice(fromColumn);
                        Space toSpace = getSpaceFromChoice(toColumn);
                        String moveArgument = (String) arguments[2];
                        if (fromSpace != null && toSpace != null) {
                            fromSpace.get(new ActualField(moveArgument));
                            toSpace.put(moveArgument);
                            responses.put((String) clientName, "ok");
                            System.out.println(arguments[3] + " moved " + moveArgument + " from column number " + fromColumn + " to column number " + toColumn);
                        } else {
                            responses.put((String) clientName, "ko");
                        }
                        break;
                    case "edit":
                        arguments = requests.get(new FormalField(String.class), new FormalField(String.class), new FormalField(String.class), new FormalField(String.class), new FormalField(String.class));
                        columnName = (String) arguments[1];
                        String oldTaskName = (String) arguments[2];
                        String newTaskName = (String) arguments[4];
                        Space editSpace = getSpaceFromChoice(columnName);

                        if (editSpace != null) {
                            editSpace.get(new ActualField(oldTaskName));
                            editSpace.put(newTaskName);
                            responses.put((String) clientName, "ok");
                            System.out.println("Edited task " + oldTaskName + " to " + newTaskName + " in column " + columnName);
                        } else {
                            responses.put((String) clientName, "ko");
                        }
                        break;
                    default:
                        System.out.println("Here in default");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Space getSpaceFromChoice(String choice) {
        switch (choice) {
            case "1":
                return backlog.getColumnSpace();
            case "2":
                return doing.getColumnSpace();
            case "3":
                return review.getColumnSpace();
            case "4":
                return done.getColumnSpace();
            default:
                return null;
        }
    }
}
