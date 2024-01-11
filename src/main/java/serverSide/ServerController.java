package serverSide;

import com.google.gson.Gson;
import org.jspace.Tuple;
import util.ReadJSONFromFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ServerController {

    private List<Integer> ClientList;
    private List<Task> TaskList;

    public List<Integer> getClientList() {
        return ClientList;
    }

    public void setClientList(List<Integer> clientList) {
        ClientList = clientList;
    }

    public List<Task> getTaskList() {
        return TaskList;
    }

    public void setTaskList(List<Task> taskList) {
        TaskList = taskList;
    }

    // TODO: flipToken(ID)
    public void handleRequest() {
        ServerSocket serverSocket = new ServerSocket();
        Thread serverThread = new Thread(serverSocket);
        serverThread.start();


        String filePath = "src/main/resources/server_data.json";
        if (Files.isReadable(Paths.get(filePath))) {
            ReadJSONFromFile readJSONFromFile = new ReadJSONFromFile();
            String json = readJSONFromFile.read(filePath);
            // Deserialize JSON to Java object
            if (json != null && !json.isEmpty()) {
                Gson gson = new Gson();
                Tuple task = gson.fromJson(json, Tuple.class);

                // print task getters for testing
                System.out.println("TaskID: " + task.getElementAt(0));
                System.out.println("Name: " + task.getElementAt(1));
                System.out.println("Description: " + task.getElementAt(2));
            }
        }
    }

}
