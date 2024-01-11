package clientSide;

import org.jspace.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClientRemoteSpace {

    static Space backlog;
    static RemoteSpace doing;
    static RemoteSpace review;
    static RemoteSpace done;

    public static void main(String[] args) throws IOException, InterruptedException {

        backlog = new RemoteSpace("tcp://127.0.0.1:8080/backlog?keep");
        doing = new RemoteSpace("tcp://127.0.0.1:8080/doing?keep");
        review = new RemoteSpace("tcp://127.0.0.1:8080/review?keep");
        done = new RemoteSpace("tcp://127.0.0.1:8080/done?keep");
        welcomeScreen();
        //backlog.put("Tuple 1");
        //doing.put("Tuple 2");
        //review.put("Tuple 3");
        //done.put("Tuple 4");
    }

    public static void welcomeScreen() throws InterruptedException {
        System.out.println("Welcome to KanPlan!");
        System.out.println(" ");
        while (true) {
            mainScreen();
        }
    }

    public static void mainScreen() throws InterruptedException {
        printSpace(backlog);

        //List<Object[]> doingList = doing.queryAll(new FormalField(String.class));
        //System.out.println("Tasks in Doing:");
        //for (Object[] obj : doingList) {
        //    System.out.println(Arrays.toString(obj));
        //}

//        List<Object[]> reviewList = review.queryAll(new FormalField(String.class));
//        System.out.println("Tasks in Review:");
//        for (Object[] obj : reviewList) {
//            System.out.println(Arrays.toString(obj));
//        }
//
//        List<Object[]> doneList = done.queryAll(new FormalField(String.class));
//        System.out.println("Tasks in Done:");
//        for (Object[] obj : doneList) {
//            System.out.println(Arrays.toString(obj));
//        }
        System.out.println(" ");
        System.out.println("Please select an option:");
        System.out.println("1. Add a task");
        System.out.println("2. Remove a task");
        System.out.println("3. Edit a task");
        System.out.println("4. Move a task");
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
                // removeTask();
                break;
            case 3:
                // editTask();
                break;
            case 4:
                // moveTask();
                break;
            case 5:
                mainScreen();
                break;
            case 6:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private static void printSpace(Space space) throws InterruptedException {
        List<Object[]> backlogList = space.queryAll(new FormalField(String.class));
        System.out.println("Tasks in Backlog:");
        for (Object[] obj : backlogList) {
            //if (obj.length == 0) {
            //    System.out.println("No tasks in Backlog");
            //} else {
            String data = (String) obj[0];
            System.out.println(data);
            //}
            //System.out.println(Arrays.toString(obj));
        }
    }

    public static void addTask() {
        System.out.println("Please enter the name of the task:");
        Scanner input = new Scanner(System.in);
        String taskName = input.nextLine();
        // System.out.println("Please enter the description of the task:");
        // String taskDescription = input.nextLine();
        Tuple task = new Tuple(taskName);
        //System.out.println("Task: " + task + " added to Backlog");
        try {
            backlog.put(taskName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
