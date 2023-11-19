package com.baharmand.marketplaceapi.controller;

import com.baharmand.marketplaceapi.domain.dto.AdDTOView;
import com.baharmand.marketplaceapi.exception.AuthenticationException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.baharmand.marketplaceapi.domain.dto.UserDTOForm;
import com.baharmand.marketplaceapi.service.AdServiceImpl;
import com.baharmand.marketplaceapi.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link UserController} class, which validates the behavior
 * of the controller responsible for handling user-related operations.
 * Uses Mockito for mocking dependencies and ensuring the proper functioning of
 * methods under different scenarios.
 */
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserServiceImpl userService;

    @Mock
    private AdServiceImpl adService;

    @InjectMocks
    private UserController userController;

    /**
     * Tests the successful authentication of a user and retrieval of advertisements.
     * Expects a response with HTTP status code 200 (OK).
     */
    @Test
    public void testDoAuthenticateAndRetrieveAds_Success() {
        // Mocking the behavior of the userService
        when(userService.authenticateUser(any())).thenReturn(true);
        List<AdDTOView> userAds = Arrays.asList(
                new AdDTOView("1", "Ad 1", "Description 1", LocalDate.now(), null, "test@example.com"),
                new AdDTOView("2", "Ad 2", "Description 2", LocalDate.now(), null, "test@example.com")
        );

        when(userService.getUserAdvertisements(anyString())).thenReturn(userAds);

        UserDTOForm userDTOForm = new UserDTOForm("test@example.com", "password");

        ResponseEntity<?> responseEntity = userController.doAuthenticateAndRetrieveAds(userDTOForm);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Tests the scenario where user authentication fails.
     * Expects a response with HTTP status code 401 (Unauthorized).
     */
    @Test
    public void testDoAuthenticateAndRetrieveAds_AuthenticationFailure() {
        when(userService.authenticateUser(any())).thenThrow(new AuthenticationException("Authentication failed"));

        UserDTOForm userDTOForm = new UserDTOForm("test@example.com", "password");

        ResponseEntity<?> responseEntity = userController.doAuthenticateAndRetrieveAds(userDTOForm);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

}
