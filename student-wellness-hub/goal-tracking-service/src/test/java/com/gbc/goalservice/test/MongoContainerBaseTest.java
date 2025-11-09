package com.gbc.goalservice.test;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Base class to start a MongoDB TestContainer once for all extending integration tests.
 */
@Testcontainers
public abstract class MongoContainerBaseTest {

    // Define the MongoDB Container. Use the image version 'mongo:6' from your docker-compose
    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:6"));

    /**
     * Dynamically sets Spring Boot properties to connect to the running MongoDB container.
     */
    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        // Use the replica set URL which provides the correct connection string including the dynamic port
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
}