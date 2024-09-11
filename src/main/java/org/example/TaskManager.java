package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class TaskManager {
    private List<Task> tasks;
    private final String fileName;

    public TaskManager(String fileName) {
        tasks = new ArrayList<>();
        this.fileName = fileName;
    }

    public void addTask(Task task) throws IOException {
        tasks.add(task);
    }

    public void removeTask(Task task) throws IOException {
        tasks.remove(task);
    }

    public List<Task> getToDoTasks() {
        return tasks.stream().filter(t -> t.getStatus() == TaskStatus.TODO).toList();
    }

    public List<Task> getInProgressTasks() {
        return tasks.stream().filter(t -> t.getStatus() == TaskStatus.IN_PROGRESS).toList();
    }

    public List<Task> getDoneTasks() {
        return tasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).toList();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void loadTasks() throws IOException {
        this.tasks.clear();

        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;

        while ((line = reader.readLine()) != null) {
            if (line.equals("[") || line.equals("]")) {
                continue;
            }

            Task task = Task.jsonToTask(line);
            this.tasks.add(task);
        }

        reader.close();
    }

    public void saveTasks() throws IOException {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            json.append(task.toJson());

            if (i != tasks.size() - 1) {
                json.append(",");
            }

            json.append("\n");
        }
        json.append("]");

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(json.toString());
        writer.close();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Task findById(Integer id) {
        return tasks.stream().filter(t -> Objects.equals(t.getId(), id)).findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
