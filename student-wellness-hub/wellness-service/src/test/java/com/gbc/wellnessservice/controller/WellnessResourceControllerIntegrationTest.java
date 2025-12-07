package com.gbc.wellnessservice.controller;

import com.gbc.wellnessservice.test.PostgreSqlContainerBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Full integration test for the WellnessResourceController, using the TestContainer-managed PostgreSQL.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WellnessResourceControllerIntegrationTest extends PostgreSqlContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String API_URL = "/api/resources";

    // --- Helper method (truncated for brevity, assumes successful creation) ---
    private Long createTestResource(String category) throws Exception {
        String resourceJson = String.format("{ \"title\": \"Test Resource\", \"description\": \"Test Guide\", " +
                "\"category\": \"%s\", \"url\": \"http://test.com\" }", category);

        mockMvc.perform(post(API_URL)
                        .contentType(APPLICATION_JSON)
                        .content(resourceJson))
                .andExpect(status().isCreated());

        return 1L; // Placeholder: replace with actual parsing logic
    }

    @Test
    void shouldCreateAndFilterResourceByCategory() throws Exception {
        // Setup: Create two resources with different categories
        createTestResource("mindfulness");
        createTestResource("counseling");

        // 1. Filter by Category ("mindfulness")
        mockMvc.perform(get(API_URL + "?category=mindfulness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("mindfulness"))
                .andExpect(jsonPath("$.length()").value(1)); // Should only find one
    }

    // Test to ensure all resources are returned
    @Test
    void shouldRetrieveAllResources() throws Exception {
        // Setup: Ensure resources exist
        createTestResource("mindfulness");
        createTestResource("counseling");

        mockMvc.perform(get(API_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}