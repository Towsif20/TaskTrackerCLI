package org.example;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Main {
    private final static String filePath = "data.json";

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager(filePath);

        try {
            taskManager.loadTasks();
        }
        catch (IOException e) {
            System.out.println("Cannot load tasks file");
            return;
        }

        int maxId = taskManager.getTasks().stream().map(Task::getId).max(Integer::compareTo).orElse(0);

        Task.setCounter(maxId);

        if (args.length == 0) {
            System.out.println("Please enter a valid command");
        }

        if (args.length == 1) {
            if (Objects.equals(args[0], "list")) {
                taskManager.getTasks().forEach(System.out::println);
            }

            else {
                System.out.println("Please enter a valid command");
            }
        }

        if (args.length == 2) {
            if (Objects.equals(args[0], "add")) {
                try {
                    taskManager.addTask(new Task(args[1]));
                    taskManager.saveTasks();
                }
                catch (IOException e) {
                    System.out.println("Cannot add task");
                }
            }

            else if (Objects.equals(args[0], "list")) {
                if (Objects.equals(args[1], "done")) {
                    taskManager.getDoneTasks().forEach(System.out::println);
                }
                else if (Objects.equals(args[1], "todo")) {
                    taskManager.getToDoTasks().forEach(System.out::println);
                }
                else if (Objects.equals(args[1], "in-progress")) {
                    taskManager.getInProgressTasks().forEach(System.out::println);
                }
                else {
                    System.out.println("Please enter a valid task type to filter");
                }
            }

            else if (Objects.equals(args[0], "delete")) {
                try {
                    Integer id = Integer.parseInt(args[1]);
                    Task task = taskManager.findById(id);
                    taskManager.removeTask(task);

                    taskManager.saveTasks();
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid task id");
                }
                catch (NoSuchElementException e) {
                    System.out.println("Cannot find task");
                }
                catch (IOException e) {
                    System.out.println("Cannot delete task");
                }
            }

            else if (Objects.equals(args[0], "mark-done")) {
                try {
                    Integer id = Integer.parseInt(args[1]);
                    Task task = taskManager.findById(id);
                    task.markDone();

                    taskManager.saveTasks();
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid task id");
                }
                catch (NoSuchElementException e) {
                    System.out.println("Cannot find task");
                }
                catch (IOException e) {
                    System.out.println("Cannot update task status");
                }
            }

            else if (Objects.equals(args[0], "mark-in-progress")) {
                try {
                    Integer id = Integer.parseInt(args[1]);
                    Task task = taskManager.findById(id);
                    task.markInProgress();

                    taskManager.saveTasks();
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid task id");
                }
                catch (NoSuchElementException e) {
                    System.out.println("Cannot find task");
                }
                catch (IOException e) {
                    System.out.println("Cannot update task status");
                }
            }

            else {
                System.out.println("Please enter a valid command");
            }
        }

        if (args.length == 3) {
            if (Objects.equals(args[0], "update")) {
                try {
                    Integer id = Integer.parseInt(args[1]);
                    Task task = taskManager.findById(id);
                    task.updateTask(args[2]);

                    taskManager.saveTasks();
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid task id");
                }
                catch (NoSuchElementException e) {
                    System.out.println("Cannot find task");
                }
                catch (IOException e) {
                    System.out.println("Cannot update task description");
                }
            }
        }
    }
}