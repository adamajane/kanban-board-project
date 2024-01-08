package serverSide;

import java.util.List;

public class ServerController {

    private List<Integer> ClientList;
    private List<Tasks> TaskList;

    public List<Integer> getClientList() {
        return ClientList;
    }

    public void setClientList(List<Integer> clientList) {
        ClientList = clientList;
    }

    public List<Tasks> getTaskList() {
        return TaskList;
    }

    public void setTaskList(List<Tasks> taskList) {
        TaskList = taskList;
    }

    // TODO: flipToken(ID)
    // TODO: handleRequest()
}
