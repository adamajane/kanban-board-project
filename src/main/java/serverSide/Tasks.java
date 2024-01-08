package serverSide;

public class Tasks {

    private int taskID;
    private String name;
    private String description;
    private boolean token = true;

    public Tasks(int taskID, String name, String description, boolean token) {
        this.taskID = taskID;
        this.name = name;
        this.description = description;
        this.token = token;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isToken() {
        return token;
    }

    public void setToken(boolean token) {
        this.token = token;
    }

// TODO: Return which tuple space it's contained in?
}
