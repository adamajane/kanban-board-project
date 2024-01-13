package clientSide;

import org.jspace.*;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static util.Config.IP_ADDRESS;

public class ClientRemoteSpace {

    static RemoteSpace backlog;
    static RemoteSpace doing;
    static RemoteSpace review;
    static RemoteSpace done;
    static RemoteSpace requests;
    static RemoteSpace responses;
    static RemoteSpace clients;
    static String clientName;


    public static void main(String[] args) throws IOException, InterruptedException {

        backlog = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/backlog?keep");
        doing = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/doing?keep");
        review = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/review?keep");
        done = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/done?keep");
        requests = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/requests?keep");
        responses = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/responses?keep");

        // for testing rpc
//        requests.put("add", "backlog", "taskname");
//        List<Object[]> taskList = requests.queryAll(new FormalField(String.class), new FormalField(String.class), new FormalField(String.class));
//        for (Object[] obj : taskList) {
//            String data = (String) obj[0] + " " + (String) obj[1] + " " + (String) obj[2];
//            System.out.println(data);
//        }

        welcomeScreen();
    }

    public static void welcomeScreen() throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to KanPlan!");
        System.out.println(" ");
        System.out.println("Insert your name here:");
        clientName = scan.nextLine();
        System.out.println(" ");
        System.out.println("Connected as: " + clientName);
        System.out.println(" ");

        printSpaceTasks(backlog, "Backlog");
        System.out.println(" ");
        printSpaceTasks(doing, "Doing");
        System.out.println(" ");
        printSpaceTasks(review, "Review");
        System.out.println(" ");
        printSpaceTasks(done, "Done");
        System.out.println(" ");

        while (true) {
            mainScreen();
        }
    }

    public static void mainScreen() throws InterruptedException {

        System.out.println(" ");
        System.out.println("Please select an option:");
        System.out.println("1. Add a task");
        System.out.println("2. Remove a task");
        System.out.println("3. Move a task");
        System.out.println("4. Edit a task");
        System.out.println("5. Update");
        System.out.println("6. Exit");
        System.out.println(" ");
        Scanner scan = new Scanner(System.in);
        int userOption = scan.nextInt();

        switch (userOption) {
            case 1:
                addTask();
                break;
            case 2:
                removeTask(); // TODO: Can remove tasks. Has to throw exception if no task of that name exists
                break;
            case 3:
                moveTask();
                break;
            case 4:
                // editTask();
                break;
            case 5:
                update();
                break;
            case 6:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option");
                break;
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


    public static void addTask() throws InterruptedException {
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
                    requests.put("add", "backlog", taskName, clientName, "");
                    System.out.println("Task added to Backlog");
                    break;
                case 2:
                    requests.put("add", "doing", taskName, clientName, "");
                    System.out.println("Task added to Doing");
                    break;
                case 3:
                    requests.put("add", "review", taskName, clientName, "");
                    System.out.println("Task added to Review");
                    break;
                case 4:
                    requests.put("add", "done", taskName, clientName, "");
                    System.out.println("Task added to Done");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        refreshTaskLists();

    }

    public static void removeTask() throws InterruptedException {
        Scanner input = new Scanner(System.in);

        System.out.println("Choose the column you want to remove the task from:");
        System.out.println("1. Backlog");
        System.out.println("2. Doing");
        System.out.println("3. Review");
        System.out.println("4. Done");

        int columnChoice = input.nextInt();
        input.nextLine();

        System.out.println("Please enter the name of the task:");
        String taskName = input.nextLine();

        try {
            switch (columnChoice) {
                case 1:
                    requests.put("remove", "backlog", taskName, clientName, "");
//                    backlog.getp(new ActualField(taskName));
                    System.out.println("Task removed from Backlog");
                    break;
                case 2:
                    requests.put("remove", "doing", taskName, clientName, "");
//                    doing.get(new ActualField(taskName));
                    System.out.println("Task removed from Doing");
                    break;
                case 3:
                    requests.put("remove", "review", taskName, clientName, "");
//                    review.get(new ActualField(taskName));
                    System.out.println("Task removed from Review");
                    break;
                case 4:
                    requests.put("remove", "done", taskName, clientName, "");
//                    done.get(new ActualField(taskName));
                    System.out.println("Task removed from Done");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        refreshTaskLists();
    }

    public static void moveTask() throws InterruptedException {
        Scanner input = new Scanner(System.in);

        System.out.println("Choose the column you want to move the task from:");
        System.out.println("1. Backlog");
        System.out.println("2. Doing");
        System.out.println("3. Review");
        System.out.println("4. Done");

        int fromColumnChoice = input.nextInt();
        input.nextLine(); // Consume the newline

        System.out.println("Choose the column you want to move the task to:");
        System.out.println("1. Backlog");
        System.out.println("2. Doing");
        System.out.println("3. Review");
        System.out.println("4. Done");

        int toColumnChoice = input.nextInt();
        input.nextLine(); // Consume the newline

        System.out.println("Please enter the name of the task:");
        String taskName = input.nextLine();
        String fromColumnString = Integer.toString(fromColumnChoice);
        String toColumnString = Integer.toString(toColumnChoice);

        requests.put("move", fromColumnString, taskName, clientName, toColumnString);

        System.out.println("Task moved from " + fromColumnChoice + " to " + toColumnChoice);
        refreshTaskLists();
    }


    private static RemoteSpace getSpaceFromChoice(int choice) {
        switch (choice) {
            case 1:
                return backlog;
            case 2:
                return doing;
            case 3:
                return review;
            case 4:
                return done;
            default:
                return null;
        }
    }

    public static void refreshTaskLists() throws InterruptedException {
        Object[] response = responses.get(new ActualField(clientName), new FormalField(String.class));
        String responseString = (String) response[1];
        if (Objects.equals(responseString, "ok")) {
            System.out.println("\n\n\n\n");
            printSpaceTasks(backlog, "Backlog");
            System.out.println(" ");
            printSpaceTasks(doing, "Doing");
            System.out.println(" ");
            printSpaceTasks(review, "Review");
            System.out.println(" ");
            printSpaceTasks(done, "Done");
            System.out.println(" ");
        } else {
            System.out.println("Server: bad response");
        }
    }

    public static void update() throws InterruptedException {
        System.out.println("\n\n\n\n");
        printSpaceTasks(backlog, "Backlog");
        System.out.println(" ");
        printSpaceTasks(doing, "Doing");
        System.out.println(" ");
        printSpaceTasks(review, "Review");
        System.out.println(" ");
        printSpaceTasks(done, "Done");
        System.out.println(" ");
    }


}
