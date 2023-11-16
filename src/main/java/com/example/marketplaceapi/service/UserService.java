package com.example.marketplaceapi.service;

import com.example.marketplaceapi.domain.dto.AdDTOView;
import com.example.marketplaceapi.domain.dto.UserDTOForm;
import com.example.marketplaceapi.domain.entity.User;

import java.util.List;

/**
 * Service interface for user-related operations.
 */
public interface UserService {

    /**
     * Registers a new user based on the provided user information.
     *
     * @param userDTOForm User information for registration.
     * @return The registered user.
     */
    User register(UserDTOForm userDTOForm);


    /**
     * Authenticates a user based on the provided credentials.
     *
     * @param userDTOForm User credentials for authentication.
     * @return True if the authentication is successful, false otherwise.
     */
    boolean authenticateUser(UserDTOForm userDTOForm);

    /**
     * Retrieves advertisements associated with a specific user.
     *
     * @param userEmail Email address of the user.
     * @return List of advertisements associated with the user.
     */
    List<AdDTOView> getUserAdvertisements(String userEmail);



}
