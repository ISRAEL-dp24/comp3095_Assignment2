package com.gbc.goalservice.service;

import com.gbc.goalservice.test.MongoContainerBaseTest;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * Integration test for goal suggestion logic, which requires external calls
 * to wellness-service, mocked by WireMock.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Start WireMock on the port the goal-tracking-service expects for the wellness-service (e.g., 8081)
@AutoConfigureWireMock(port = 8081)
public class GoalSuggestionServiceIntegrationTest extends MongoContainerBaseTest {

    // Assuming you have a service class that handles the suggestion logic and makes the REST call
    @Autowired
    private GoalService goalService;

    // --- CRITICAL STEP: Reroute the Wellness Service URL to WireMock's address ---
    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        // This ensures goal-tracking-service calls localhost:8081 (WireMock) instead of the actual wellness-service.
        registry.add("wellness.service.url", () -> "http://localhost:8081");
    }

    @Test
    void shouldFetchSuggestedResourcesForGoalCategory() {
        // 1. GIVEN: Define the expected JSON response from the mocked wellness-service
        String expectedJsonResponse =
                "[{\"title\":\"Daily Planner Resource\",\"description\":\"Plan your day\",\"category\":\"mindfulness\",\"url\":\"http://link.to/planner\"}]";

        // 2. STUB: Configure WireMock to return the mock response when called
        WireMock.stubFor(WireMock.get(WireMock.urlMatching("/api/resources\\?category=mindfulness")) // Matches the expected call
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(expectedJsonResponse)));


        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/api/resources?category=mindfulness")));


    }
}