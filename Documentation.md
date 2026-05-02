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
