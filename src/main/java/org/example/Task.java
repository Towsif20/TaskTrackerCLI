package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {

    private static Integer counter = 0;

    private Integer id;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Task(String description) {
        this.id = ++counter;
        this.description = description;
        this.setStatus(TaskStatus.TODO);
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Task(Integer id, String description, TaskStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(description, task.description) &&
               status == task.status && Objects.equals(createdAt, task.createdAt) &&
               Objects.equals(updatedAt, task.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(status);
        result = 31 * result + Objects.hashCode(createdAt);
        result = 31 * result + Objects.hashCode(updatedAt);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
               "id=" + id +
               ", description='" + description + '\'' +
               ", status=" + status +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               '}';
    }

    public void updateTask(String description) {
        this.setDescription(description);
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void markToDo() {
        this.setStatus(TaskStatus.TODO);
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void markInProgress() {
        this.setStatus(TaskStatus.IN_PROGRESS);
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void markDone() {
        this.setStatus(TaskStatus.DONE);
        this.setUpdatedAt(LocalDateTime.now());
    }

    public static Task jsonToTask(String json) {
        String taskString = json.replace("{", "")
                                .replace("}", "")
                                .replace("\"", "");

        String[] values = taskString.split(",");

        for (int i = 0, valuesLength = values.length; i < valuesLength; i++) {
            int index = values[i].indexOf(":");
            values[i] = values[i].substring(index+1).trim();
        }

        Integer id = Integer.parseInt(values[0]);
        String description = values[1];
        TaskStatus status = TaskStatus.valueOf(values[2]);
        LocalDateTime createdAt = LocalDateTime.parse(values[3], formatter);
        LocalDateTime updatedAt = LocalDateTime.parse(values[4], formatter);

        return new Task(id, description, status, createdAt, updatedAt);
    }

    public String toJson() {
        return "{\"id\":\"" + id + "\", \"description\":\"" + description.strip() + "\", \"status\":\"" + status +
               "\", \"createdAt\":\"" + createdAt.format(formatter) + "\", \"updatedAt\":\"" + updatedAt.format(formatter) + "\"}";
    }

    public static Integer getCounter() {
        return counter;
    }

    public static void setCounter(Integer counter) {
        Task.counter = counter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
