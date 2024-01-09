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

    // public void remove(Tuple task) throws InterruptedException {
    //     if (tasks == null) {
    //         System.out.println("No tasks in " + name);
    //     } else {
    //         tasks.getp(task.getTuple());
    //         System.out.println("Removed: " + task + " from " + name);
    //     }
    // }

    public void getTasks() throws InterruptedException {
        List<Object[]> taskList = tasks.queryAll(new FormalField(Integer.class), new FormalField(String.class), new FormalField(String.class), new FormalField(Boolean.class));
        for (Object[] obj : taskList) {
            System.out.println("Tasks in " + name + ":");
            System.out.println(Arrays.toString(obj));
        }
    }

    // TODO: remove(task)


    // TODO: edit(task)

}
