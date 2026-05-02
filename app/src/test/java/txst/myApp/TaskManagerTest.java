package txst.myApp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskManagerTest {
    @Test
    void addTaskCreatesTaskWithNextIdAndPendingStatus() {
        TaskManager manager = new TaskManager();

        Task firstTask = manager.addTask("Write code", "Create the manager class");
        Task secondTask = manager.addTask("Run tests", "Verify behavior");

        assertEquals(1, firstTask.getId());
        assertEquals(2, secondTask.getId());
        assertEquals("Write code", firstTask.getTitle());
        assertFalse(firstTask.isCompleted());
        assertEquals(2, manager.getAllTasks().size());
    }

    @Test
    void addTaskRejectsBlankTitle() {
        TaskManager manager = new TaskManager();

        assertThrows(IllegalArgumentException.class, () -> manager.addTask("   ", "No title"));
    }

    @Test
    void updateTaskChangesTitleAndDescription() {
        TaskManager manager = new TaskManager();
        Task task = manager.addTask("Old title", "Old description");

        boolean updated = manager.updateTask(task.getId(), "New title", "New description");

        assertTrue(updated);
        assertEquals("New title", task.getTitle());
        assertEquals("New description", task.getDescription());
    }

    @Test
    void updateTaskReturnsFalseWhenTaskDoesNotExist() {
        TaskManager manager = new TaskManager();

        boolean updated = manager.updateTask(99, "Missing task", "Nothing to update");

        assertFalse(updated);
    }

    @Test
    void markTaskCompletedUpdatesCompletionStatus() {
        TaskManager manager = new TaskManager();
        Task task = manager.addTask("Submit project", "Upload final zip");

        boolean completed = manager.markTaskCompleted(task.getId());

        assertTrue(completed);
        assertTrue(task.isCompleted());
    }

    @Test
    void getCompletedTasksOnlyReturnsCompletedTasks() {
        TaskManager manager = new TaskManager();
        Task completedTask = manager.addTask("Done", "Already complete");
        manager.addTask("Pending", "Still in progress");

        manager.markTaskCompleted(completedTask.getId());
        List<Task> completedTasks = manager.getCompletedTasks();

        assertEquals(1, completedTasks.size());
        assertEquals(completedTask.getId(), completedTasks.get(0).getId());
    }

    @Test
    void deleteTaskRemovesTask() {
        TaskManager manager = new TaskManager();
        Task task = manager.addTask("Remove me", "This task should be deleted");

        boolean deleted = manager.deleteTask(task.getId());

        assertTrue(deleted);
        assertTrue(manager.getAllTasks().isEmpty());
    }

    @Test
    void deleteTaskReturnsFalseWhenTaskDoesNotExist() {
        TaskManager manager = new TaskManager();

        boolean deleted = manager.deleteTask(99);

        assertFalse(deleted);
    }
}
