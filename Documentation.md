# Project Documentation

## Step 1 - Package Rename

Date: May 2, 2026

Changes completed:

- Created the new main source package path `app/src/main/java/txst/myApp`.
- Moved `App.java` from `org/example` to `txst/myApp`.
- Updated `App.java` package declaration from `org.example` to `txst.myApp`.
- Created the matching test package path `app/src/test/java/txst/myApp`.
- Moved `AppTest.java` from `org/example` to `txst/myApp`.
- Updated `AppTest.java` package declaration from `org.example` to `txst.myApp`.
- Updated Gradle `application.mainClass` from `org.example.App` to `txst.myApp.App`.

Verification:

- Passed: ran `.\gradlew.bat test` successfully after the package rename.

## Step 2 - Task Entity

Date: May 2, 2026

Changes completed:

- Added `Task.java` in the `txst.myApp` package.
- Defined the task entity with four fields: `id`, `title`, `description`, and `completed`.
- Added a constructor for creating task objects with all field values.
- Added getters and setters for each field.
- Added `isCompleted()` for boolean completion status.
- Added `toString()` so task objects display using their title.
- Added `TaskTest.java` with unit tests for constructor values, setters, completion status, and `toString()`.

Verification:

- Passed: ran `.\gradlew.bat test` successfully after adding the `Task` entity and unit tests.

## Step 3 - Task Manager

Date: May 2, 2026

Changes completed:

- Added `TaskManager.java` in the `txst.myApp` package.
- Added a Java Collection Framework `List<Task>` to store tasks.
- Added automatic task ID generation starting at `1`.
- Added `addTask()` to create pending tasks.
- Added `updateTask()` to edit an existing task's title and description.
- Added `markTaskCompleted()` to mark a task as completed.
- Added `deleteTask()` to remove a task by ID.
- Added `getAllTasks()` to display every task.
- Added `getCompletedTasks()` to display only completed tasks.
- Added `findTaskById()` for lookup support.
- Added basic business rule validation so task titles cannot be blank.
- Added `TaskManagerTest.java` with unit tests for add, update, complete, delete, completed-task filtering, and missing-task behavior.

Verification:

- Passed: ran `.\gradlew.bat test` successfully after adding the `TaskManager` and unit tests.

## Step 4 - File Storage

Date: May 2, 2026

Changes completed:

- Added `TaskFileStorage.java` in the `txst.myApp` package.
- Added `saveTasks()` to write task data to a text file.
- Added `loadTasks()` to read task data from a text file.
- Used one task per line with the format `id|title|description|completed`.
- Added escaping so titles and descriptions can safely contain separators, backslashes, and line breaks.
- Added behavior so loading from a missing file returns an empty task list.
- Added directory creation before saving when the target file is inside a folder that does not exist yet.
- Added a `TaskManager(List<Task> startingTasks)` constructor so tasks loaded from storage can initialize the manager.
- Added ID continuation logic so new tasks created after loading use the next available ID.
- Added `TaskFileStorageTest.java` with tests for missing files, saving, loading, special-character preservation, and ID continuation after loading.

Verification:

- Passed: ran `.\gradlew.bat test` successfully after adding file storage and fixing escaped backslash parsing.

## Step 5 - Connect Storage To App Flow

Date: May 2, 2026

Changes completed:

- Added `TaskService.java` in the `txst.myApp` package.
- Connected `TaskService` to `TaskFileStorage` so saved tasks are loaded when the service is created.
- Connected `TaskService` to `TaskManager` so business logic remains separate from file storage.
- Added automatic saving after successful add, update, complete, and delete operations.
- Added read methods for all tasks, completed tasks, and task lookup through the service.
- Updated `App.java` to create a `TaskService` using the default storage file `data/tasks.txt`.
- Updated the application startup message to report how many tasks were loaded.
- Updated `AppTest.java` to test the new application greeting.
- Added `TaskServiceTest.java` with tests for loading saved tasks and automatic saving after changes.

Verification:

- Passed: ran `.\gradlew.bat test` successfully after connecting storage through the service layer and updating app startup.

## Step 6 - Swing Main Window

Date: May 2, 2026

Changes completed:

- Added `TodoFrame.java` in the `txst.myApp` package.
- Built the main Swing application window using `JFrame`.
- Added a non-editable `JTable` for task display.
- Added table columns for ID, title, description, and status.
- Added an All Tasks / Completed Tasks filter using `JComboBox`.
- Added buttons for Add, Edit, Complete, and Delete.
- Wired the Complete button to mark the selected task completed through `TaskService`.
- Wired the Delete button to confirm and delete the selected task through `TaskService`.
- Added table refresh behavior after filtering, completing, and deleting tasks.
- Added user-facing error dialogs for storage failures.
- Updated `App.java` to start the Swing GUI with `SwingUtilities.invokeLater()`.
- Added `createTodoFrame()` and `start()` methods to support GUI startup.

Verification:

- Passed: ran `.\gradlew.bat test` successfully after adding the Swing main window and GUI startup path.

## Step 7 - Task Dialog And Add/Edit GUI

Date: May 2, 2026

Changes completed:

- Added `TaskDialog.java` in the `txst.myApp` package.
- Built a modal Swing dialog using `JDialog`.
- Added title input with `JTextField`.
- Added description input with `JTextArea`.
- Added Save and Cancel buttons.
- Added dialog validation so task titles cannot be blank.
- Added `TaskFormData` record to return title and description values from the dialog.
- Enabled the Add button in `TodoFrame`.
- Wired Add to open `TaskDialog`, create a task through `TaskService`, save automatically, and refresh the table.
- Enabled the Edit button in `TodoFrame`.
- Wired Edit to load the selected task, open `TaskDialog` with existing values, update through `TaskService`, save automatically, and refresh the table.
- Improved selected task ID lookup by reading from the table model row.

Verification:

- Passed: ran `.\gradlew.bat test` successfully after adding the task dialog and enabling Add/Edit in the GUI.
