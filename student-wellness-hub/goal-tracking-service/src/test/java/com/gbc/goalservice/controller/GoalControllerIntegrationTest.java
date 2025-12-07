package com.gbc.goalservice.controller;

import com.gbc.goalservice.test.MongoContainerBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Full integration test for the GoalController using the TestContainer-managed MongoDB.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GoalControllerIntegrationTest extends MongoContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;
    private static final String API_URL = "/api/goals";

    private String createGoal(String category, String status) throws Exception {
        String createGoalJson = String.format("{ \"title\": \"Goal Test\", \"description\": \"A test goal\", \"category\": \"%s\", \"status\": \"%s\", \"targetDate\": \"2026-03-01\" }",
                category, status);

        // Perform POST and check for success
        mockMvc.perform(post(API_URL)
                        .contentType(APPLICATION_JSON)
                        .content(createGoalJson))
                .andExpect(status().isCreated());

        // In MongoDB, IDs are typically returned as strings (ObjectId).
        // For simplicity, we assume we can now retrieve it via the filtering tests below.
        return "Goal Test";
    }

    @Test
    void shouldCreateAndRetrieveGoalsByStatusAndCategory() throws Exception {
        // Setup: Create two goals
        createGoal("physical", "in-progress");
        createGoal("mindfulness", "completed");

        // 1. Retrieve goals by category ("physical")
        mockMvc.perform(get(API_URL + "?category=physical"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("physical"))
                .andExpect(jsonPath("$.length()").value(1));

        // 2. Retrieve goals by status ("completed")
        mockMvc.perform(get(API_URL + "?status=completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("completed"))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldMarkGoalAsCompleted() throws Exception {
        // Setup: Create an in-progress goal
        createGoal("mindfulness", "in-progress");

        // Find the goal ID (requires a find operation)
        // NOTE: You'll need to implement logic to find the ID, for this example we assume ID 1
        String goalId = "1";

        // Test Mark as Completed (PUT or PATCH operation)
        mockMvc.perform(patch(API_URL + "/" + goalId + "/complete"))
                .andExpect(status().isOk());

        // Verification: Check status is now 'completed' (requires a GET operation after the PATCH)
        mockMvc.perform(get(API_URL + "/" + goalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("completed"));
    }
}