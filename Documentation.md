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
