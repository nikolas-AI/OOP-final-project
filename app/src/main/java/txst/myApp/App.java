package txst.myApp;

import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class App {
    private static final Path TASK_FILE_PATH = Path.of("data", "tasks.txt");

    public String getGreeting() {
        return "Todo List application ready.";
    }

    public TaskService createTaskService() throws IOException {
        return new TaskService(new TaskFileStorage(TASK_FILE_PATH));
    }

    public TodoFrame createTodoFrame(TaskService taskService) {
        return new TodoFrame(taskService);
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            try {
                TaskService taskService = createTaskService();
                TodoFrame todoFrame = createTodoFrame(taskService);
                todoFrame.setVisible(true);
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(
                        null,
                        "Could not load task data:\n" + exception.getMessage(),
                        "Startup Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    public static void main(String[] args) {
        new App().start();
    }
}
