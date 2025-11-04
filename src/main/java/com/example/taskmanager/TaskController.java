package com.example.taskmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid; 
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks") // All endpoints in this class start with /tasks
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // 1. GET /tasks: Retrieve all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // 2. POST /tasks: Create a new task
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        // @Valid triggers validation (from @NotBlank in Task.java)
        // @RequestBody converts the incoming JSON to a Task object
        Task savedTask = taskRepository.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED); 
    }

    // 3. GET /tasks/{id}: Retrieve a specific task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) {
        // @PathVariable pulls the {id} from the URL
        return taskRepository.findById(id)
                .map(task -> ResponseEntity.ok(task)) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }

    // 4. PUT /tasks/{id}: Update an existing task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id, @Valid @RequestBody Task taskDetails) {
        return taskRepository.update(id, taskDetails)
                .map(updatedTask -> ResponseEntity.ok(updatedTask)) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }

    // 5. DELETE /tasks/{id}: Delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        if (taskRepository.deleteById(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // 6. PATCH /tasks/{id}/complete: Mark a task as complete
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> markTaskAsComplete(@PathVariable long id) {
        return taskRepository.findById(id)
            .map(task -> {
                task.setStatus(Status.COMPLETED);
                task.setUpdatedAt(LocalDateTime.now());
                taskRepository.update(id, task); // Save the change
                return ResponseEntity.ok(task); // 200 OK
            })
            .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }
}