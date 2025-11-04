package com.example.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Use @WebMvcTest to test only the web (controller) layer
@WebMvcTest(TaskController.class)
class TaskManagerApplicationTests {

    @Autowired
    private MockMvc mockMvc; // Lets us simulate HTTP requests

    @MockBean // Creates a fake version of our repository
    private TaskRepository taskRepository;

    @Test
    void getTaskById_WhenTaskExists_ReturnsTask() throws Exception {
        // 1. Arrange (Set up the test)
        Task testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setStatus(Status.PENDING);

        // "When" taskRepository.findById(1) is called, "then" return our testTask
        given(taskRepository.findById(1L)).willReturn(Optional.of(testTask));

        // 2. Act & 3. Assert (Perform the action and check the result)
        mockMvc.perform(get("/tasks/1")) // Perform a GET request
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Check that the returned JSON is correct
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void getTaskById_WhenTaskNotExists_ReturnsNotFound() throws Exception {
        // 1. Arrange
        // "When" taskRepository.findById(99) is called, "then" return empty
        given(taskRepository.findById(99L)).willReturn(Optional.empty());

        // 2. Act & 3. Assert
        mockMvc.perform(get("/tasks/99"))
                .andExpect(status().isNotFound()); // Expect HTTP 404 Not Found
    }
}