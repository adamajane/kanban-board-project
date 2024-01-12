package clientSide;

import org.jspace.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static util.Config.IP_ADDRESS;

public class ClientRemoteSpace {

    static RemoteSpace backlog;
    static RemoteSpace doing;
    static RemoteSpace review;
    static RemoteSpace done;

    public static void main(String[] args) throws IOException, InterruptedException {

        backlog = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/backlog?keep");
        doing = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/doing?keep");
        review = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/review?keep");
        done = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/done?keep");
        RemoteSpace requests = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/requests?keep");

        // for testing rpc
//        requests.put("add", "backlog", "taskname");
//        List<Object[]> taskList = requests.queryAll(new FormalField(String.class), new FormalField(String.class), new FormalField(String.class));
//        for (Object[] obj : taskList) {
//            String data = (String) obj[0] + " " + (String) obj[1] + " " + (String) obj[2];
//            System.out.println(data);
//        }

        welcomeScreen(requests);
        //backlog.put("Tuple 1");
        //doing.put("Tuple 2");
        //review.put("Tuple 3");
        //done.put("Tuple 4");
    }

    public static void welcomeScreen(Space requests) throws InterruptedException {
        System.out.println("Welcome to KanPlan!");
        System.out.println(" ");
        while (true) {
            mainScreen(requests);
        }
    }

    public static void mainScreen(Space requests) throws InterruptedException {
        printSpaceTasks(backlog, "Backlog");
        System.out.println(" ");
        printSpaceTasks(doing, "Doing");
        System.out.println(" ");
        printSpaceTasks(review, "Review");
        System.out.println(" ");
        printSpaceTasks(done, "Done");
        System.out.println(" ");


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
                addTask(requests);
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
                mainScreen(requests);
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


    public static void addTask(Space requests) throws InterruptedException {
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
                    requests.put("add", "backlog", taskName);
                    System.out.println("Task added to Backlog");
                    break;
                case 2:
                    requests.put("add", "doing", taskName);
                    System.out.println("Task added to Doing");
                    break;
                case 3:
                    requests.put("add", "review", taskName);
                    System.out.println("Task added to Review");
                    break;
                case 4:
                    requests.put("add", "done", taskName);
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

    public static void removeTask() {
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
                    backlog.getp(new ActualField(taskName));
                    System.out.println("Task removed from Backlog");
                    break;
                case 2:
                    doing.get(new ActualField(taskName));
                    System.out.println("Task removed from Doing");
                    break;
                case 3:
                    review.get(new ActualField(taskName));
                    System.out.println("Task removed from Review");
                    break;
                case 4:
                    done.get(new ActualField(taskName));
                    System.out.println("Task removed from Done");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void moveTask() {
        Scanner input = new Scanner(System.in);

        System.out.println("Choose the column you want to move the task from:");
        System.out.println("1. Backlog");
        System.out.println("2. Doing");
        System.out.println("3. Review");
        System.out.println("4. Done");

        int fromColumnChoice = input.nextInt();
        input.nextLine();

        System.out.println("Choose the column you want to move the task to:");
        System.out.println("1. Backlog");
        System.out.println("2. Doing");
        System.out.println("3. Review");
        System.out.println("4. Done");

        int toColumnChoice = input.nextInt();
        input.nextLine();

        System.out.println("Please enter the name of the task:");
        String taskName = input.nextLine();

        try {
            RemoteSpace fromSpace = getSpaceFromChoice(fromColumnChoice);
            RemoteSpace toSpace = getSpaceFromChoice(toColumnChoice);

            if (fromSpace != null && toSpace != null) {
                fromSpace.getp(new ActualField(taskName));
                toSpace.put(taskName);
                System.out.println("Task moved successfully");
            } else {
                System.out.println("Invalid column choice");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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


}
