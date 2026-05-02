package txst.myApp;

import java.io.IOException;
import java.nio.file.Path;

public class App {
    private static final Path TASK_FILE_PATH = Path.of("data", "tasks.txt");

    public String getGreeting() {
        return "Todo List application ready.";
    }

    public TaskService createTaskService() throws IOException {
        return new TaskService(new TaskFileStorage(TASK_FILE_PATH));
    }

    public static void main(String[] args) {
        App app = new App();
        try {
            TaskService taskService = app.createTaskService();
            System.out.println(app.getGreeting() + " Loaded " + taskService.getAllTasks().size() + " task(s).");
        } catch (IOException exception) {
            System.err.println("Could not load task data: " + exception.getMessage());
            System.exit(1);
        }
    }
}
