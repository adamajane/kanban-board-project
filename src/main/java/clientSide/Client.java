package clientSide;

import org.jspace.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static util.Config.IP_ADDRESS;

public class Client {

    static RemoteSpace backlog;
    static RemoteSpace doing;
    static RemoteSpace review;
    static RemoteSpace done;
    static RemoteSpace requests;
    static RemoteSpace responses;
    static String clientName;


    public void run() throws InterruptedException, IOException {

        backlog = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/backlog?keep");
        doing = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/doing?keep");
        review = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/review?keep");
        done = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/done?keep");
        requests = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/requests?keep");
        responses = new RemoteSpace("tcp://" + IP_ADDRESS + ":8080/responses?keep");

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

        update();

        while (true) {
            mainScreen();
        }
    }

    public static void mainScreen() throws InterruptedException {
        Scanner input = new Scanner(System.in);
        int userOption;

        while (true) {
            System.out.println(" ");
            System.out.println("Please select an option:");
            System.out.println("1. Add a task");
            System.out.println("2. Remove a task");
            System.out.println("3. Move a task");
            System.out.println("4. Edit a task");
            System.out.println("5. Update");
            System.out.println("6. Exit");
            System.out.println(" ");
            try {
                userOption = input.nextInt();
                input.nextLine();
                if (userOption < 1 || userOption > 6) {
                    System.out.println("Invalid option. Please enter a number between 1 and 6.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter an integer number.");
                input.nextLine();
            }
        }

        switch (userOption) {
            case 1:
                addTask();
                break;
            case 2:
                removeTask();
                break;
            case 3:
                moveTask();
                break;
            case 4:
                editTask();
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
        int columnChoice;
        while (true) {
            System.out.println("Choose the column you want to add the task to:");
            uiColumnsChoice();
            try {
                columnChoice = input.nextInt();
                input.nextLine();
                if (columnChoice < 1 || columnChoice > 4) {
                    System.out.println("Invalid option. Please enter a number between 1 and 4.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid option. Please enter a number between 1 and 4.");
                input.nextLine();
            }
        }

        String taskName;
        while (true) {
            System.out.println("Please enter the name of the task:");
            taskName = input.nextLine().trim();
            if (taskName.isEmpty()) {
                System.out.println("Invalid option. Task name can't be empty or only spaces.");
            } else {
                break;
            }
        }

        try {
            switch (columnChoice) {
                case 1:
                    requests.put("add", "backlog", taskName, clientName, "");
                    System.out.println("\n");
                    System.out.println("Added the task " + "\"" + taskName + "\"" + " to Backlog");
                    break;
                case 2:
                    requests.put("add", "doing", taskName, clientName, "");
                    System.out.println("\n");
                    System.out.println("Added the task " + "\"" + taskName + "\"" + " to Doing");
                    break;
                case 3:
                    requests.put("add", "review", taskName, clientName, "");
                    System.out.println("\n");
                    System.out.println("Added the task " + "\"" + taskName + "\"" + " to Review");
                    break;
                case 4:
                    requests.put("add", "done", taskName, clientName, "");
                    System.out.println("\n");
                    System.out.println("Added the task " + "\"" + taskName + "\"" + " to Done");
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
        int columnChoice;
        while (true) {
            System.out.println("Choose the column you want to remove the task to:");
            uiColumnsChoice();
            try {
                columnChoice = input.nextInt();
                input.nextLine();
                if (columnChoice < 1 || columnChoice > 4) {
                    System.out.println("Invalid option. Please enter a number between 1 and 4.");
                    continue;
                }

                switch (columnChoice) {
                    case 1:
                        printSpaceTasks(backlog, "Backlog");
                        System.out.println(" ");
                        break;
                    case 2:
                        printSpaceTasks(doing, "Doing");
                        System.out.println(" ");
                        break;
                    case 3:
                        printSpaceTasks(review, "Review");
                        System.out.println(" ");
                        break;
                    case 4:
                        printSpaceTasks(done, "Done");
                        System.out.println(" ");
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }

                break;
            } catch (Exception e) {
                System.out.println("Invalid option. Please enter a number between 1 and 4.");
                input.nextLine();
            }
        }

        String taskName;
        while (true) {
            System.out.println("Please enter the name of the task:");
            taskName = input.nextLine().trim();
            if (taskName.isEmpty()) {
                System.out.println("Invalid option. Task name can't be empty or only spaces.");
            } else {
                break;
            }
        }

        try {
            switch (columnChoice) {
                case 1:
                    if (backlog.queryp(new ActualField(taskName)) == null) {
                        System.out.println("No task with the name " + "\"" + taskName + "\"" + " in Backlog");
                        update();
                        return;
                    }
                    requests.put("remove", "backlog", taskName, clientName, "");
                    System.out.println("Removed the task " + "\"" + taskName + "\"" + " from Backlog");
                    break;
                case 2:
                    if (doing.queryp(new ActualField(taskName)) == null) {
                        System.out.println("No task with the name " + "\"" + taskName + "\"" + " in Doing");
                        update();
                        return;
                    }
                    requests.put("remove", "doing", taskName, clientName, "");
                    System.out.println("Removed the task " + "\"" + taskName + "\"" + " from Doing");
                    break;
                case 3:
                    if (review.queryp(new ActualField(taskName)) == null) {
                        System.out.println("No task with the name " + "\"" + taskName + "\"" + " in Review");
                        update();
                        return;
                    }
                    requests.put("remove", "review", taskName, clientName, "");
                    System.out.println("Removed the task " + "\"" + taskName + "\"" + " from Review");
                    break;
                case 4:
                    if (done.queryp(new ActualField(taskName)) == null) {
                        System.out.println("No task with the name " + "\"" + taskName + "\"" + " in Done");
                        update();
                        return;
                    }
                    requests.put("remove", "done", taskName, clientName, "");
                    System.out.println("Removed the task " + "\"" + taskName + "\"" + " from Done");
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
        int fromColumnChoice;
        while (true) {
            System.out.println("Choose the column you want to move the task from:");
            uiColumnsChoice();
            try {
                fromColumnChoice = input.nextInt();
                input.nextLine();
                if (fromColumnChoice < 1 || fromColumnChoice > 4) {
                    System.out.println("Invalid option. Please enter a number between 1 and 4.");
                    continue;
                }

                switch (fromColumnChoice) {
                    case 1:
                        printSpaceTasks(backlog, "Backlog");
                        System.out.println(" ");
                        break;
                    case 2:
                        printSpaceTasks(doing, "Doing");
                        System.out.println(" ");
                        break;
                    case 3:
                        printSpaceTasks(review, "Review");
                        System.out.println(" ");
                        break;
                    case 4:
                        printSpaceTasks(done, "Done");
                        System.out.println(" ");
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }

                break;
            } catch (Exception e) {
                System.out.println("Invalid option. Please enter a number between 1 and 4.");
                input.nextLine();
            }
        }

        int toColumnChoice;
        while (true) {
            System.out.println("Choose the column you want to move the task to:");
            uiColumnsChoice();
            try {
                toColumnChoice = input.nextInt();
                input.nextLine();
                if (toColumnChoice < 1 || toColumnChoice > 4) {
                    System.out.println("Invalid option. Please enter a number between 1 and 4.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid option. Please enter a number between 1 and 4.");
                input.nextLine();
            }
        }


        String taskName;
        while (true) {
            System.out.println("Please enter the name of the task:");
            taskName = input.nextLine().trim();
            if (taskName.isEmpty()) {
                System.out.println("Invalid option. Task name can't be empty or only spaces.");
            } else {
                break;
            }
        }

        switch (fromColumnChoice) {
            case 1:
                if (backlog.queryp(new ActualField(taskName)) == null) {
                    System.out.println("No task with the name " + "\"" + taskName + "\"" + " in Backlog");
                    update();
                    return;
                }
                break;
            case 2:
                if (doing.queryp(new ActualField(taskName)) == null) {
                    System.out.println("No task with the name " + "\"" + taskName + "\"" + " in Doing");
                    update();
                    return;
                }
                break;
            case 3:
                if (review.queryp(new ActualField(taskName)) == null) {
                    System.out.println("No task with the name " + "\"" + taskName + "\"" + " in Review");
                    update();
                    return;
                }
                break;
            case 4:
                if (done.queryp(new ActualField(taskName)) == null) {
                    System.out.println("No task with the name " + "\"" + taskName + "\"" + " in Done");
                    update();
                    return;
                }
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }

        String fromColumnString = Integer.toString(fromColumnChoice);
        String toColumnString = Integer.toString(toColumnChoice);

        RemoteSpace fromSpace = getSpaceFromChoice(fromColumnChoice);
        if (fromSpace.queryp(new ActualField(taskName)) == null) {
            System.out.println("The task '" + taskName + "' is no longer in the selected column.");
            return;
        }

        requests.put("move", fromColumnString, taskName, clientName, toColumnString);

        System.out.println("Moved task " + "\"" + taskName + "\"" + " from " + getColumnFromChoice(fromColumnChoice) + " to " + getColumnFromChoice(toColumnChoice));
        refreshTaskLists();
    }

    public static void editTask() throws InterruptedException {
        Scanner input = new Scanner(System.in);
        int columnChoice;
        while (true) {
            System.out.println("Choose the column of the task you want to edit:");
            uiColumnsChoice();
            try {
                columnChoice = input.nextInt();
                input.nextLine();
                if (columnChoice < 1 || columnChoice > 4) {
                    System.out.println("Invalid option. Please enter a number between 1 and 4.");
                    continue;
                }

                switch (columnChoice) {
                    case 1:
                        printSpaceTasks(backlog, "Backlog");
                        System.out.println(" ");
                        break;
                    case 2:
                        printSpaceTasks(doing, "Doing");
                        System.out.println(" ");
                        break;
                    case 3:
                        printSpaceTasks(review, "Review");
                        System.out.println(" ");
                        break;
                    case 4:
                        printSpaceTasks(done, "Done");
                        System.out.println(" ");
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }

                break;
            } catch (Exception e) {
                System.out.println("Invalid option. Please enter a number between 1 and 4.");
                input.nextLine();
            }
        }

        System.out.println("Please enter the name of the task to edit:");
        String taskName = input.nextLine().trim();

        switch (columnChoice) {
            case 1:
                if (backlog.queryp(new ActualField(taskName)) == null) {
                    System.out.println("No task with the name " + "\"" + taskName + "\"" + " in Backlog");
                    update();
                    return;
                }
                break;
            case 2:
                if (doing.queryp(new ActualField(taskName)) == null) {
                    System.out.println("No task with the name " + "\"" + taskName + "\"" + " in Doing");
                    update();
                    return;
                }
                break;
            case 3:
                if (review.queryp(new ActualField(taskName)) == null) {
                    System.out.println("No task with the name " + "\"" + taskName + "\"" + " in Review");
                    update();
                    return;
                }
                break;
            case 4:
                if (done.queryp(new ActualField(taskName)) == null) {
                    System.out.println("No task with the name " + "\"" + taskName + "\"" + " in Done");
                    update();
                    return;
                }
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }

        String newTaskName;
        while (true) {
            System.out.println("Please enter the name of the task:");
            newTaskName = input.nextLine().trim();
            if (newTaskName.isEmpty()) {
                System.out.println("Invalid option. Task name can't be empty or only spaces.");
            } else {
                break;
            }
        }

        RemoteSpace selectedColumnSpace = getSpaceFromChoice(columnChoice);
        if (selectedColumnSpace.queryp(new ActualField(taskName)) == null) {
            System.out.println("No task with the name \"" + taskName + "\" found in the selected column.");
            return;
        }

        String columnString = Integer.toString(columnChoice);
        requests.put("edit", columnString, taskName, clientName, newTaskName);

        System.out.println("Edited task " + "\"" + taskName + "\"" + " to " + "\"" + newTaskName + "\"" + " in " + getColumnFromChoice(columnChoice));

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

    public static String getColumnFromChoice(int choice) {
        switch (choice) {
            case 1:
                return "Backlog";
            case 2:
                return "Doing";
            case 3:
                return "Review";
            case 4:
                return "Done";
            default:
                return "Invalid option";
        }
    }

    public static void refreshTaskLists() throws InterruptedException {
        Object[] response = responses.get(new ActualField(clientName), new FormalField(String.class));
        String responseString = (String) response[1];
        if (Objects.equals(responseString, "ok")) {
            update();
        } else {
            System.out.println("Server: bad response");
        }
    }

    public static void update() throws InterruptedException {
        System.out.println("\n");
        printSpaceTasks(backlog, "Backlog");
        System.out.println(" ");
        printSpaceTasks(doing, "Doing");
        System.out.println(" ");
        printSpaceTasks(review, "Review");
        System.out.println(" ");
        printSpaceTasks(done, "Done");
        System.out.println(" ");
    }

    public static void uiColumnsChoice() {
        System.out.println("1. Backlog");
        System.out.println("2. Doing");
        System.out.println("3. Review");
        System.out.println("4. Done");
    }
}
