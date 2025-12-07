package com.gbc.wellnessservice.test;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.containers.wait.strategy.Wait;

/**
 * Combines PostgreSQL and Redis TestContainer setups for full Wellness Service integration.
 * Inherits PostgreSQL setup from PostgreSqlContainerBaseTest.
 */
public abstract class WellnessContainerBaseTest extends PostgreSqlContainerBaseTest {

    // 1. Define the Redis Container (using redis:7 image from your docker-compose)
    @Container
    public static GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:7"))
            .withExposedPorts(6379) // Default Redis port
            .waitingFor(Wait.forListeningPort());

    // 2. Configure Spring Boot to use the dynamic properties of the running Redis container
    @DynamicPropertySource
    static void setRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379).toString());
    }
}