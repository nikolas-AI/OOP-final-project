# Todo List Course Project

Java Swing todo list application for the CS 3354 course project.

## Overview

This project is a desktop task manager built with Java, Swing, Gradle, Java Collections, and text-file storage. The application provides a graphical interface where users can create, update, complete, delete, and view tasks.

The project was designed to satisfy the course project requirements:

- Gradle-based Java project structure.
- Swing graphical user interface.
- File-based data storage.
- Java Collection Framework usage.
- Object-oriented class design.
- Support for all required todo list operations.

## Core Features

- Create a new task with a title and description.
- Update an existing task's title and description.
- Mark a task as completed.
- Delete a selected task.
- Display all tasks.
- Display only completed tasks.
- Save task data to a text file.
- Load saved task data when the app starts.
- Automatically save after task changes.
- Validate that task titles are not blank.

## User Workflow

1. Start the application.
2. Use the main window to view existing tasks.
3. Select **Add** to create a new task.
4. Select a task and choose **Edit** to update it.
5. Select a task and choose **Complete** to mark it as completed.
6. Select a task and choose **Delete** to remove it.
7. Use the filter dropdown to switch between **All Tasks** and **Completed Tasks**.
8. Close and reopen the app to confirm tasks are saved and loaded.

## Project Structure

Main source files are located in:

```text
app/src/main/java/txst/myApp/
```

Test files are located in:

```text
app/src/test/java/txst/myApp/
```

Important project files:

```text
README.md
Documentation.md
settings.gradle.kts
app/build.gradle.kts
gradlew
gradlew.bat
```

## Main Classes

| Class | Responsibility |
| --- | --- |
| `App` | Application entry point. Creates the task service and starts the Swing GUI. |
| `Task` | Entity class representing one todo task. |
| `TaskManager` | Handles task business logic such as add, update, complete, delete, and filter. |
| `TaskFileStorage` | Saves and loads tasks from a text file. |
| `TaskService` | Connects the GUI/business logic to file persistence and autosave behavior. |
| `TodoFrame` | Main Swing window containing the task table, filters, and action buttons. |
| `TaskDialog` | Modal dialog used to add or edit task information. |

## Data Model

Each task contains:

- `id`: unique integer task ID
- `title`: task title
- `description`: task details
- `completed`: task completion status

New tasks are created as pending tasks. The application generates task IDs automatically and continues from the highest loaded ID when saved tasks already exist.

## Data Storage

Task data is stored in a text file:

```text
data/tasks.txt
```

The file is created automatically when tasks are saved.

Each saved task record contains:

```text
id|title|description|completed
```

The storage layer escapes separators, backslashes, and line breaks so task titles and descriptions can be safely saved and loaded.

Note: depending on the Gradle run location, the generated data folder may appear relative to the app execution directory. If you create sample data for submission, keep it clean and relevant.

## Requirements

- Java 21
- Windows PowerShell or Command Prompt
- Gradle wrapper included with the project

You do not need to install Gradle separately if you use the included wrapper scripts.

## Run The App

From the project root in PowerShell:

```powershell
.\gradlew.bat run
```

From the project root in Command Prompt:

```cmd
gradlew.bat run
```

## Run Tests

From the project root in PowerShell:

```powershell
.\gradlew.bat test
```

From the project root in Command Prompt:

```cmd
gradlew.bat test
```

## Clean And Test

From the project root in PowerShell:

```powershell
.\gradlew.bat clean test
```

From the project root in Command Prompt:

```cmd
gradlew.bat clean test
```

## Manual Verification Checklist

After running the app, verify:

- The Swing window opens successfully.
- Add creates a new task.
- Edit updates the selected task.
- Complete changes the selected task status to completed.
- Delete removes the selected task after confirmation.
- All Tasks shows pending and completed tasks.
- Completed Tasks shows only completed tasks.
- Closing and reopening the app reloads saved tasks.

## Tests

The project includes unit tests for the non-GUI logic:

- `TaskTest`
- `TaskManagerTest`
- `TaskFileStorageTest`
- `TaskServiceTest`
- `AppTest`

These tests verify task data behavior, manager operations, file persistence, automatic saving, and application startup support.

## Business Rules

- A task title cannot be blank.
- A task description may be blank.
- New tasks start as pending.
- Completed tasks remain visible in the completed task list.
- Deleted tasks are removed from memory and saved storage.
- Task changes are saved automatically after successful modifications.
- If the task file does not exist, the app starts with an empty task list.

## Submission Notes

The final submission should include:

- Full Gradle project.
- Source files.
- Test files.
- `README.md`.
- `Documentation.md`.
- Object-oriented design document.
- Gradle wrapper files.



