package txst.myApp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskServiceTest {
    @TempDir
    Path tempDir;

    @Test
    void serviceLoadsTasksFromStorageWhenCreated() throws IOException {
        Path filePath = tempDir.resolve("tasks.txt");
        TaskFileStorage storage = new TaskFileStorage(filePath);
        storage.saveTasks(List.of(new Task(4, "Loaded task", "Read from file", true)));

        TaskService service = new TaskService(storage);

        assertEquals(1, service.getAllTasks().size());
        assertEquals("Loaded task", service.getAllTasks().get(0).getTitle());
        assertTrue(service.getAllTasks().get(0).isCompleted());
    }

    @Test
    void addTaskAutomaticallySavesTasks() throws IOException {
        Path filePath = tempDir.resolve("tasks.txt");
        TaskFileStorage storage = new TaskFileStorage(filePath);
        TaskService service = new TaskService(storage);

        service.addTask("Save automatically", "Add should persist");
        List<Task> loadedTasks = storage.loadTasks();

        assertEquals(1, loadedTasks.size());
        assertEquals("Save automatically", loadedTasks.get(0).getTitle());
    }

    @Test
    void updateTaskAutomaticallySavesChanges() throws IOException {
        Path filePath = tempDir.resolve("tasks.txt");
        TaskFileStorage storage = new TaskFileStorage(filePath);
        TaskService service = new TaskService(storage);
        Task task = service.addTask("Old title", "Old description");

        boolean updated = service.updateTask(task.getId(), "New title", "New description");
        List<Task> loadedTasks = storage.loadTasks();

        assertTrue(updated);
        assertEquals("New title", loadedTasks.get(0).getTitle());
        assertEquals("New description", loadedTasks.get(0).getDescription());
    }

    @Test
    void markTaskCompletedAutomaticallySavesChanges() throws IOException {
        Path filePath = tempDir.resolve("tasks.txt");
        TaskFileStorage storage = new TaskFileStorage(filePath);
        TaskService service = new TaskService(storage);
        Task task = service.addTask("Complete me", "Mark as done");

        boolean completed = service.markTaskCompleted(task.getId());
        List<Task> loadedTasks = storage.loadTasks();

        assertTrue(completed);
        assertTrue(loadedTasks.get(0).isCompleted());
    }

    @Test
    void deleteTaskAutomaticallySavesChanges() throws IOException {
        Path filePath = tempDir.resolve("tasks.txt");
        TaskFileStorage storage = new TaskFileStorage(filePath);
        TaskService service = new TaskService(storage);
        Task task = service.addTask("Delete me", "Remove from storage");

        boolean deleted = service.deleteTask(task.getId());
        List<Task> loadedTasks = storage.loadTasks();

        assertTrue(deleted);
        assertTrue(loadedTasks.isEmpty());
    }

    @Test
    void missingTaskChangesDoNotSaveNewData() throws IOException {
        Path filePath = tempDir.resolve("tasks.txt");
        TaskFileStorage storage = new TaskFileStorage(filePath);
        TaskService service = new TaskService(storage);

        boolean updated = service.updateTask(99, "Missing", "No save needed");

        assertFalse(updated);
        assertTrue(storage.loadTasks().isEmpty());
    }
}
