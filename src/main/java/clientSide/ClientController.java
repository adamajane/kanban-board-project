package clientSide;

import com.google.gson.Gson;
import serverSide.Task;

import javafx.event.*;

public class ClientController {

    private int clientID;
    private boolean token = false;

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
        System.out.println("Task created");
    }

    // TODO: addRequest()

    // TODO: removeRequest()

    // TODO: editRequest()

    // TODO: collectToken(ID)

    // TODO: cancelToken()

    public void testRequest() {
        ClientSocket clientSocket = new ClientSocket();

        Task obj = new Task(2, "Testing", "This is just a test", false);
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        clientSocket.request(json);
        System.out.println(json);
    }
}
