package txst.myApp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskFileStorage {
    private static final String FIELD_SEPARATOR = "|";
    private final Path filePath;

    public TaskFileStorage(Path filePath) {
        this.filePath = filePath;
    }

    public List<Task> loadTasks() throws IOException {
        if (Files.notExists(filePath)) {
            return new ArrayList<>();
        }

        List<Task> tasks = new ArrayList<>();
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        for (String line : lines) {
            if (!line.isBlank()) {
                tasks.add(parseTask(line));
            }
        }
        return tasks;
    }

    public void saveTasks(List<Task> tasks) throws IOException {
        Path parentPath = filePath.getParent();
        if (parentPath != null) {
            Files.createDirectories(parentPath);
        }

        List<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(formatTask(task));
        }
        Files.write(filePath, lines, StandardCharsets.UTF_8);
    }

    private String formatTask(Task task) {
        return task.getId()
                + FIELD_SEPARATOR
                + escape(task.getTitle())
                + FIELD_SEPARATOR
                + escape(task.getDescription())
                + FIELD_SEPARATOR
                + task.isCompleted();
    }

    private Task parseTask(String line) {
        List<String> fields = splitEscapedFields(line);
        if (fields.size() != 4) {
            throw new IllegalArgumentException("Invalid task record: " + line);
        }

        int id = Integer.parseInt(fields.get(0));
        String title = unescape(fields.get(1));
        String description = unescape(fields.get(2));
        boolean completed = Boolean.parseBoolean(fields.get(3));
        return new Task(id, title, description, completed);
    }

    private List<String> splitEscapedFields(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean escaping = false;

        for (int index = 0; index < line.length(); index++) {
            char currentCharacter = line.charAt(index);
            if (escaping) {
                currentField.append(currentCharacter);
                escaping = false;
            } else if (currentCharacter == '\\') {
                currentField.append(currentCharacter);
                escaping = true;
            } else if (currentCharacter == '|') {
                fields.add(currentField.toString());
                currentField.setLength(0);
            } else {
                currentField.append(currentCharacter);
            }
        }

        fields.add(currentField.toString());
        return fields;
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("|", "\\|")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }

    private String unescape(String value) {
        StringBuilder unescapedValue = new StringBuilder();
        boolean escaping = false;

        for (int index = 0; index < value.length(); index++) {
            char currentCharacter = value.charAt(index);
            if (escaping) {
                if (currentCharacter == 'n') {
                    unescapedValue.append('\n');
                } else if (currentCharacter == 'r') {
                    unescapedValue.append('\r');
                } else {
                    unescapedValue.append(currentCharacter);
                }
                escaping = false;
            } else if (currentCharacter == '\\') {
                escaping = true;
            } else {
                unescapedValue.append(currentCharacter);
            }
        }

        if (escaping) {
            unescapedValue.append('\\');
        }
        return unescapedValue.toString();
    }
}
