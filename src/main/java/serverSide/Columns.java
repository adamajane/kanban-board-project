package serverSide;

import org.jspace.RandomSpace;
import org.jspace.Space;

public class Columns {

    private String name;
    private Space tasks = new RandomSpace(); // TODO: May change from a random space

    public Columns(String name, Space tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Space getTasks() {
        return tasks;
    }

    public void setTasks(Space tasks) {
        this.tasks = tasks;
    }

    // TODO: add(task)
    // TODO: remove(task)
    // TODO: edit(task)

    // TODO:
}
