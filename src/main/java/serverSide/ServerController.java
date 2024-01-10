package serverSide;

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
    // TODO: handleRequest()
}
