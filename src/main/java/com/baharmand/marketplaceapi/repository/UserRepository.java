package com.baharmand.marketplaceapi.repository;

import com.baharmand.marketplaceapi.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Checks if a user with the specified email exists.
     *
     * @param email The email to check.
     * @return True if a user with the specified email exists, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Retrieves an optional user by their email.
     *
     * @param email The email of the user.
     * @return Optional containing the user with the specified email, or empty if not found.
     */
    Optional<User> findByEmail(String email);


}