# Todo List Course Project

Java Swing todo list application for the CS 3354 course project.

## Overview

This project is a desktop task manager built with Java, Swing, Gradle, and text-file storage. It allows users to create, update, complete, delete, and view todo tasks through a graphical interface.

## Features

- Create a new task with a title and description.
- Update an existing task.
- Mark a task as completed.
- Delete a task.
- Display all tasks.
- Display only completed tasks.
- Save and load task data from a text file.
- Use Java Collection Framework classes for task storage.
- Use Swing for the graphical user interface.

## Project Structure

```text
app/src/main/java/txst/myApp/
```

Main classes:

- `App` starts the Swing application.
- `TodoFrame` contains the main GUI window.
- `TaskDialog` handles add/edit task input.
- `Task` represents a todo item.
- `TaskManager` manages task business logic.
- `TaskService` connects business logic to file persistence.
- `TaskFileStorage` saves and loads tasks from a text file.

## Data Storage

Task data is stored in:

```text
data/tasks.txt
```

The file is created automatically when tasks are saved. Each task is stored as a text record containing its ID, title, description, and completion status.

## Requirements

- Java 21
- Gradle wrapper included with the project

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

## Final Verification

Before submission, run these in PowerShell:

```powershell
.\gradlew.bat clean test
.\gradlew.bat run
```

Or run these in Command Prompt:

```cmd
gradlew.bat clean test
gradlew.bat run
```

