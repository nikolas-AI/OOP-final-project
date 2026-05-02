package txst.myApp;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TaskService {
    private final TaskFileStorage storage;
    private final TaskManager taskManager;

    public TaskService(TaskFileStorage storage) throws IOException {
        this.storage = storage;
        taskManager = new TaskManager(storage.loadTasks());
    }

    public Task addTask(String title, String description) throws IOException {
        Task task = taskManager.addTask(title, description);
        saveTasks();
        return task;
    }

    public boolean updateTask(int id, String title, String description) throws IOException {
        boolean updated = taskManager.updateTask(id, title, description);
        if (updated) {
            saveTasks();
        }
        return updated;
    }

    public boolean markTaskCompleted(int id) throws IOException {
        boolean completed = taskManager.markTaskCompleted(id);
        if (completed) {
            saveTasks();
        }
        return completed;
    }

    public boolean deleteTask(int id) throws IOException {
        boolean deleted = taskManager.deleteTask(id);
        if (deleted) {
            saveTasks();
        }
        return deleted;
    }

    public List<Task> getAllTasks() {
        return taskManager.getAllTasks();
    }

    public List<Task> getCompletedTasks() {
        return taskManager.getCompletedTasks();
    }

    public Optional<Task> findTaskById(int id) {
        return taskManager.findTaskById(id);
    }

    public void saveTasks() throws IOException {
        storage.saveTasks(taskManager.getAllTasks());
    }
}
