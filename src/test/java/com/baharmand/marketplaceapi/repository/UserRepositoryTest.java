package com.baharmand.marketplaceapi.repository;

import com.baharmand.marketplaceapi.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for the {@link UserRepository} class, focusing on validating
 * the functionality of methods related to User entity persistence and retrieval.
 * Uses the {@link DataJpaTest} annotation to configure a lightweight JPA test
 * environment with an in-memory database.
 */
@DataJpaTest
public class UserRepositoryTest {

    UserRepository userRepository;

    /**
     * Constructs an instance of {@code UserRepositoryTest} with the necessary
     * dependencies injected via the constructor.
     *
     * @param userRepository The repository for managing User entities.
     */
    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Sets up the test environment by creating a user and saving it to the
     * in-memory database for subsequent testing.
     */
    @BeforeEach
    public void setUp() {
        User user = userRepository.save(new User("test@gmail.com", "password"));
    }

    /**
     * Tests the {@link UserRepository#existsByEmail(String)} method to ensure that
     * it correctly determines the existence of a user with a given email address.
     * Also, tests the {@link UserRepository#findByEmail(String)} method to verify
     * successful retrieval of a user by email.
     */
    @Test
    public void testExistsAndFindByEmail() {
        assertTrue(userRepository.existsByEmail("test@gmail.com"));
        assertNotNull(userRepository.findByEmail("test@gmail.com").get().getUserId());
    }


}
