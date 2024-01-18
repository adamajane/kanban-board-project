package serverSide;

import org.jspace.*;

import java.util.Arrays;
import java.util.List;

public class Column {

    private String name;
    private Space columnSpace;

    public Column(String name) {
        this.name = name;
        this.columnSpace = new SequentialSpace();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(Tuple task) throws InterruptedException {
        if (columnSpace == null) {
            columnSpace = new SequentialSpace();
            columnSpace.put(task.getTuple());
        } else {
            columnSpace.put(task.getTuple());
        }
        System.out.println("Added: " + task + " to " + name);
    }

    public void remove(Tuple task, String taskName) throws InterruptedException {
        if (columnSpace == null) {
            System.out.println("No tasks in " + name);
        } else if (task.getTuple()[1] == taskName) {
            // tasks.getp(new ActualField(task.getTuple()[1]));
            columnSpace.getp(new FormalField(Integer.class), new ActualField(taskName), new FormalField(String.class), new FormalField(Boolean.class));
            System.out.println("Removed: " + task + " from " + name);
        } else {
            System.out.println("No task with name: " + taskName + " in " + name);
        }
    }

    public void getTasks() throws InterruptedException {
        List<Object[]> taskList = columnSpace.queryAll(new FormalField(Integer.class), new FormalField(String.class), new FormalField(String.class), new FormalField(Boolean.class));
        System.out.println("Tasks in " + name + ":");
        for (Object[] obj : taskList) {
            System.out.println(Arrays.toString(obj));
        }
    }

    public Space getColumnSpace() {
        return columnSpace;
    }


}
