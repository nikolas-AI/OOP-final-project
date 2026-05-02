package txst.myApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskManager {
    private final List<Task> tasks;
    private int nextTaskId;

    public TaskManager() {
        tasks = new ArrayList<>();
        nextTaskId = 1;
    }

    public Task addTask(String title, String description) {
        validateTitle(title);

        Task task = new Task(nextTaskId, title.trim(), cleanDescription(description), false);
        tasks.add(task);
        nextTaskId++;
        return task;
    }

    public boolean updateTask(int id, String title, String description) {
        validateTitle(title);

        Optional<Task> existingTask = findTaskById(id);
        if (existingTask.isEmpty()) {
            return false;
        }

        Task task = existingTask.get();
        task.setTitle(title.trim());
        task.setDescription(cleanDescription(description));
        return true;
    }

    public boolean markTaskCompleted(int id) {
        Optional<Task> existingTask = findTaskById(id);
        if (existingTask.isEmpty()) {
            return false;
        }

        existingTask.get().setCompleted(true);
        return true;
    }

    public boolean deleteTask(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public List<Task> getCompletedTasks() {
        List<Task> completedTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isCompleted()) {
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }

    public Optional<Task> findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return Optional.of(task);
            }
        }
        return Optional.empty();
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be blank.");
        }
    }

    private String cleanDescription(String description) {
        if (description == null) {
            return "";
        }
        return description.trim();
    }
}
