package com.gbc.eventservice.test;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Base class to start a PostgreSQL TestContainer (postgres:15) once for all extending integration tests.
 */
@Testcontainers
public abstract class PostgreSqlContainerBaseTest {

    // Define the container using the image version from your docker-compose
    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres");

    /**
     * Dynamically sets Spring Boot properties to connect to the running PostgreSQL container.
     */
    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        // Important for JPA: ensures tables are created for testing
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }
}