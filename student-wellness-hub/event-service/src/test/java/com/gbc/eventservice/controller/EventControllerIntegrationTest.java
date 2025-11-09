package com.gbc.eventservice.controller;

import com.gbc.eventservice.test.PostgreSqlContainerBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Full integration test for the EventController using the TestContainer-managed PostgreSQL.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EventControllerIntegrationTest extends PostgreSqlContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String API_URL = "/api/events";

    // --- Helper method (truncated for brevity, assumes successful creation) ---
    private Long createTestEvent() throws Exception {
        String eventJson = "{ \"title\": \"Wellness Seminar\", \"description\": \"Stress management workshop\", " +
                "\"date\": \"2026-01-15\", \"location\": \"Online\", \"capacity\": 50 }";

        // Perform POST and extract the ID from the response JSON
        String responseContent = mockMvc.perform(post(API_URL)
                        .contentType(APPLICATION_JSON)
                        .content(eventJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Simple ID parsing logic...
        return 1L; // Placeholder: replace with actual parsing logic (e.g., using Jackson or JsonPath)
    }

    @Test
    void shouldCreateAndRetrieveEvent() throws Exception {
        // Test Create operation (POST) and Retrieve (GET)
        Long eventId = createTestEvent();

        mockMvc.perform(get(API_URL + "/" + eventId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Wellness Seminar"));
    }

    @Test
    void shouldRegisterAndUnregisterStudent() throws Exception {
        Long eventId = createTestEvent();
        String studentId = "S12345";

        // Test Registration
        mockMvc.perform(post(API_URL + "/" + eventId + "/register")
                        .contentType(APPLICATION_JSON)
                        .content("{\"studentId\": \"" + studentId + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Registration successful"));

        // Test Unregistration
        mockMvc.perform(post(API_URL + "/" + eventId + "/unregister")
                        .contentType(APPLICATION_JSON)
                        .content("{\"studentId\": \"" + studentId + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Unregistration successful"));
    }
}