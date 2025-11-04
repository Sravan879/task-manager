# Task Manager API (Spring Boot)

This is a simple RESTful API for a task management system, built with Java and Spring Boot.
It allows users to perform full CRUD (Create, Read, Update, Delete) operations on tasks.

------------------------------------------------------------

🚀 How to Run

1. Prerequisites:
   - Java 17 (or newer)
   - Apache Maven

2. Run from Command Line:
   Open a terminal in the project root folder and run:
   mvn spring-boot:run

3. The API will be running at:
   http://localhost:8080

------------------------------------------------------------

🧪 API Endpoints (Testing)

You can test the API using Invoke-WebRequest in PowerShell, cURL, or an API tool like Postman.

1. POST /tasks
   Creates a new task.

   PowerShell:
   Invoke-WebRequest -Uri http://localhost:8080/tasks -Method POST -ContentType "application/json" -Body '{"title": "My first task", "description": "Learn Spring Boot"}'

2. GET /tasks
   Retrieves a list of all tasks.

   PowerShell:
   Invoke-WebRequest -Uri http://localhost:8080/tasks

3. GET /tasks/{id}
   Retrieves a specific task by its ID.

   PowerShell:
   Invoke-WebRequest -Uri http://localhost:8080/tasks/1

4. PUT /tasks/{id}
   Updates an existing task. This replaces the entire task.

   PowerShell:
   Invoke-WebRequest -Uri http://localhost:8080/tasks/1 -Method PUT -ContentType "application/json" -Body '{"title": "My UPDATED task", "description": "Learn Spring Boot", "status": "IN_PROGRESS"}'

5. PATCH /tasks/{id}/complete
   Marks a specific task as "COMPLETED".

   PowerShell:
   Invoke-WebRequest -Uri http://localhost:8080/tasks/1/complete -Method PATCH

6. DELETE /tasks/{id}
   Deletes a specific task.

   PowerShell:
   Invoke-WebRequest -Uri http://localhost:8080/tasks/1 -Method DELETE

------------------------------------------------------------

📐 Design Decisions & Assumptions

Framework:
- Used Java + Spring Boot as specified. Spring Boot is ideal for building REST APIs quickly with minimal configuration.

Data Storage:
- Used a ConcurrentHashMap as an in-memory database (TaskRepository). 
  This provides a simple, thread-safe way to store data without a real database.
  It offers fast O(1) lookups by ID.

ID Generation:
- Used an AtomicLong to generate unique, thread-safe IDs for new tasks.

Error Handling:
- Implemented a @ControllerAdvice class (GlobalExceptionHandler) to centrally handle validation errors (like a blank title),
  returning a clean JSON error message with a 400 Bad Request status.

Validation:
- Used the spring-boot-starter-validation dependency to enable @Valid and @NotBlank annotations for simple input validation on the Task model.

Security:
- Added spring-boot-starter-security to resolve a 403 Forbidden error.
- A SecurityConfig bean was created to permitAll() all requests and csrf.disable() to allow POST/PUT/PATCH requests from API testing tools.

Architecture (Simplifying Assumption):
- A standard 3-layer (Controller, Service, Repository) architecture was simplified for this exercise.
- The business logic was placed directly in the TaskController instead of a separate TaskService layer.

Model: Task.java
View: JSON responses
Controller: TaskController.java
