package com.baharmand.marketplaceapi.repository;

import com.baharmand.marketplaceapi.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {

    UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setUp() {
        User user = userRepository.save(new User("test@gmail.com", "password"));
    }

    @Test
    public void testExistsAndFindByEmail() {
        assertTrue(userRepository.existsByEmail("test@gmail.com"));
        assertNotNull(userRepository.findByEmail("test@gmail.com").get().getUserId());
    }


}
