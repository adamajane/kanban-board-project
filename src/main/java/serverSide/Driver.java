package serverSide;

import org.jspace.Tuple;

import java.util.Scanner;

public class Driver {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Create a column");
        Scanner input = new Scanner(System.in);
        String name = input.nextLine();
        Column column = new Column(name);
        System.out.println("Column created: " + column.getName());

        Tuple task1 = new Tuple(123, "Task 1", "Description 1", true);
        Tuple task2 = new Tuple(456, "Task 2", "Description 2", true);
        column.add(task1);
        column.add(task2);


        column.getTasks();

        System.out.println("Removing task 2");
        column.remove(task2, "Task 3");


        column.getTasks();
    }

}
