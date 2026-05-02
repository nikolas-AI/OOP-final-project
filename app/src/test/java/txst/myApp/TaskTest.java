package txst.myApp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskTest {
    @Test
    void taskStoresConstructorValues() {
        Task task = new Task(1, "Finish project", "Build the todo list app", false);

        assertEquals(1, task.getId());
        assertEquals("Finish project", task.getTitle());
        assertEquals("Build the todo list app", task.getDescription());
        assertFalse(task.isCompleted());
    }

    @Test
    void taskCanBeUpdatedWithSetters() {
        Task task = new Task(1, "Old title", "Old description", false);

        task.setId(2);
        task.setTitle("New title");
        task.setDescription("New description");
        task.setCompleted(true);

        assertEquals(2, task.getId());
        assertEquals("New title", task.getTitle());
        assertEquals("New description", task.getDescription());
        assertTrue(task.isCompleted());
    }

    @Test
    void toStringReturnsTaskTitle() {
        Task task = new Task(1, "Read assignment", "Review the project description", false);

        assertEquals("Read assignment", task.toString());
    }
}
