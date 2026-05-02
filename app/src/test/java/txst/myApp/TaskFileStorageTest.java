package txst.myApp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskFileStorageTest {
    @TempDir
    Path tempDir;

    @Test
    void loadTasksReturnsEmptyListWhenFileDoesNotExist() throws IOException {
        TaskFileStorage storage = new TaskFileStorage(tempDir.resolve("missing-tasks.txt"));

        List<Task> tasks = storage.loadTasks();

        assertTrue(tasks.isEmpty());
    }

    @Test
    void saveTasksWritesTasksToFile() throws IOException {
        Path filePath = tempDir.resolve("tasks.txt");
        TaskFileStorage storage = new TaskFileStorage(filePath);
        List<Task> tasks = List.of(
                new Task(1, "Write storage", "Save tasks to a text file", false),
                new Task(2, "Test storage", "Load tasks from a text file", true)
        );

        storage.saveTasks(tasks);

        assertTrue(Files.exists(filePath));
        assertEquals(2, Files.readAllLines(filePath).size());
    }

    @Test
    void loadTasksReadsSavedTasks() throws IOException {
        Path filePath = tempDir.resolve("tasks.txt");
        TaskFileStorage storage = new TaskFileStorage(filePath);
        List<Task> originalTasks = List.of(
                new Task(1, "Write storage", "Save tasks to a text file", false),
                new Task(2, "Test storage", "Load tasks from a text file", true)
        );

        storage.saveTasks(originalTasks);
        List<Task> loadedTasks = storage.loadTasks();

        assertEquals(2, loadedTasks.size());
        assertEquals(1, loadedTasks.get(0).getId());
        assertEquals("Write storage", loadedTasks.get(0).getTitle());
        assertEquals("Save tasks to a text file", loadedTasks.get(0).getDescription());
        assertEquals(false, loadedTasks.get(0).isCompleted());
        assertEquals(2, loadedTasks.get(1).getId());
        assertEquals("Test storage", loadedTasks.get(1).getTitle());
        assertEquals("Load tasks from a text file", loadedTasks.get(1).getDescription());
        assertEquals(true, loadedTasks.get(1).isCompleted());
    }

    @Test
    void storagePreservesSeparatorsAndBackslashesInTextFields() throws IOException {
        Path filePath = tempDir.resolve("tasks.txt");
        TaskFileStorage storage = new TaskFileStorage(filePath);
        Task task = new Task(1, "Read A|B", "Open C:\\notes\\todo.txt", false);

        storage.saveTasks(List.of(task));
        List<Task> loadedTasks = storage.loadTasks();

        assertEquals("Read A|B", loadedTasks.get(0).getTitle());
        assertEquals("Open C:\\notes\\todo.txt", loadedTasks.get(0).getDescription());
    }

    @Test
    void taskManagerContinuesIdsAfterLoadedTasks() {
        List<Task> loadedTasks = List.of(
                new Task(3, "Loaded task", "Already in file", false),
                new Task(7, "Another loaded task", "Highest ID", true)
        );
        TaskManager manager = new TaskManager(loadedTasks);

        Task newTask = manager.addTask("New task", "Created after loading");

        assertEquals(8, newTask.getId());
    }
}
