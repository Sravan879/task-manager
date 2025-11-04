package com.example.taskmanager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import lombok.Data; 

@Data // Creates getters, setters, equals, hashCode, toString
public class Task {

    private long id;

    @NotBlank(message = "Title is mandatory") // Validation
    private String title;

    private String description;
    private LocalDate dueDate;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor to set defaults for new tasks
    public Task() {
        this.status = Status.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}