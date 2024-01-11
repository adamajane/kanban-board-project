package clientSide;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jspace.FormalField;
import org.jspace.Tuple;
import serverSide.Column; // TODO: Used for testing
import serverSide.Task;

import javafx.event.*;

import java.util.Optional;
import java.util.Random;

public class ClientController {

    private int clientID;
    private boolean token = false;

    @FXML
    private ListView<TitledPane> backlogColumn;
    @FXML
    private Button addBacklog;
    private Column backlog; // TODO: Used for testing

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public boolean isToken() {
        return token;
    }

    public void setToken(boolean token) {
        this.token = token;
    }

    public void addTask(ActionEvent e) {
        System.out.println("Task created");
    }

    public void addTaskBacklog(ActionEvent e) {
        // Dialog for task name
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Add Task");
        nameDialog.setHeaderText("Add Task to Backlog");
        nameDialog.setContentText("Task Name:");

        Optional<String> resultName = nameDialog.showAndWait();
        if (resultName.isPresent()) {
            String taskName = resultName.get();

            // Dialog for task description
            TextInputDialog descriptionDialog = new TextInputDialog();
            descriptionDialog.setTitle("Task Description");
            descriptionDialog.setHeaderText("Enter Task Description for " + taskName);
            descriptionDialog.setContentText("Description:");

            Optional<String> resultDescription = descriptionDialog.showAndWait();
            if (resultDescription.isPresent()) {
                String taskDescription = resultDescription.get();

                // Create the UI component for the task
                TitledPane taskPane = new TitledPane(taskName, new Label(taskDescription));

                // Generate a random ID for the task
                int taskID = generateUniqueTaskId();

                // Create the Tuple for the task
                Tuple taskTuple = new Tuple(taskID, taskName, taskDescription, true);

                // Add the UI component to the ListView
                backlogColumn.getItems().add(taskPane);

                // Add the Tuple to the Column's tuple space
                try {
                    if (backlog == null) {
                        backlog = new Column("Backlog");
                    }
                    backlog.add(taskTuple);
                    backlog.getTasks(); // For testing, to print out all tasks in the column
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    // Handle the exception, perhaps show an error message to the user
                }

                System.out.println("Task created: " + taskName);
            }
        }
    }

    // First version of removeTask

    public void removeTaskBacklog(ActionEvent e) {
        // Get the selected task from the ListView
        TitledPane selectedTaskPane = backlogColumn.getSelectionModel().getSelectedItem();

        if (selectedTaskPane != null) {
            // Remove the selected task from the ListView
            backlogColumn.getItems().remove(selectedTaskPane);

            // Remove the corresponding Tuple from the Column's tuple space
            // This assumes you have a way to get the task ID or name to create an equivalent Tuple for removal
            String taskName = selectedTaskPane.getText(); // Let's assume task names are unique for this example
            Tuple taskTupleToRemove = new Tuple(new FormalField(Integer.class), taskName, new FormalField(String.class), new FormalField(Boolean.class));

            try {
                if (backlog != null) {
                    backlog.remove(taskTupleToRemove, taskName);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                // Handle the exception, perhaps show an error message to the user
            }

            System.out.println("Task removed: " + taskName);
        } else {
            // No task was selected, handle this case appropriately
            System.out.println("No task selected for removal.");
        }
    }


    private int generateUniqueTaskId() {
        Random random = new Random();
        int x = random.nextInt(900) + 100;
        return x;
    }


    // TODO: addRequest()

    // TODO: removeRequest()

    // TODO: editRequest()

    // TODO: collectToken(ID)

    // TODO: cancelToken()

    public void testRequest() {
        ClientSocket clientSocket = new ClientSocket();

        Task obj = new Task(1, "Testing", "This is just a test", false);
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        clientSocket.request(json);
        System.out.println(json);
    }
}
