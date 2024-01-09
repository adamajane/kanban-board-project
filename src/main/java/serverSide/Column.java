package serverSide;

import org.jspace.*;

import java.util.Arrays;
import java.util.List;

public class Column {

    private String name;
    private Space tasks; // TODO: May change from a random space

    public Column(String name) {
        this.name = name;
        this.tasks = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(Tuple task) throws InterruptedException {
        if (tasks == null) {
            tasks = new RandomSpace();
            tasks.put(task.getTuple());
        } else {
            tasks.put(task.getTuple());
        }
        System.out.println("Added: " + task + " to " + name);
    }

    public void remove(Tuple task, String taskName) throws InterruptedException {
        if (tasks == null) {
            System.out.println("No tasks in " + name);
        } else if (task.getTuple()[1] == taskName) {
            // tasks.getp(new ActualField(task.getTuple()[1]));
            tasks.getp(new FormalField(Integer.class), new ActualField(taskName), new FormalField(String.class), new FormalField(Boolean.class));
            System.out.println("Removed: " + task + " from " + name);
        } else {
            System.out.println("No task with name: " + taskName + " in " + name);
        }
    }

    public void getTasks() throws InterruptedException {
        List<Object[]> taskList = tasks.queryAll(new FormalField(Integer.class), new FormalField(String.class), new FormalField(String.class), new FormalField(Boolean.class));
        System.out.println("Tasks in " + name + ":");
        for (Object[] obj : taskList) {
            System.out.println(Arrays.toString(obj));
        }
    }

    // TODO: edit(task)

}
