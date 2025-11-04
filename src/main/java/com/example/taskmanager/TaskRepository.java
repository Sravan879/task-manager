package com.example.taskmanager;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskRepository {

    // Use a thread-safe Map for in-memory storage
    private final Map<Long, Task> taskStore = new ConcurrentHashMap<>();
    // Use an atomic counter for generating unique IDs
    private final AtomicLong idCounter = new AtomicLong();

    // GET /tasks (all)
    public List<Task> findAll() {
        return new ArrayList<>(taskStore.values());
    }

    // GET /tasks/{id}
    public Optional<Task> findById(long id) {
        return Optional.ofNullable(taskStore.get(id));
    }

    // POST /tasks (create)
    public Task save(Task task) {
        long newId = idCounter.incrementAndGet();
        task.setId(newId);
        task.setCreatedAt(java.time.LocalDateTime.now());
        task.setUpdatedAt(java.time.LocalDateTime.now());
        taskStore.put(newId, task);
        return task;
    }

    // PUT /tasks/{id} (update)
    public Optional<Task> update(long id, Task updatedTask) {
        return findById(id).map(existingTask -> {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setDueDate(updatedTask.getDueDate());
            existingTask.setStatus(updatedTask.getStatus());
            existingTask.setUpdatedAt(java.time.LocalDateTime.now());
            taskStore.put(id, existingTask);
            return existingTask;
        });
    }

    // DELETE /tasks/{id}
    public boolean deleteById(long id) {
        return taskStore.remove(id) != null;
    }
}