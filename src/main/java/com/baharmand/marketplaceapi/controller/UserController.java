package com.baharmand.marketplaceapi.controller;

import com.baharmand.marketplaceapi.converter.Converter;
import com.baharmand.marketplaceapi.domain.dto.AdDTOView;
import com.baharmand.marketplaceapi.domain.dto.AdUpdateDTOForm;
import com.baharmand.marketplaceapi.domain.dto.UserDTOForm;
import com.baharmand.marketplaceapi.exception.AuthenticationException;
import com.baharmand.marketplaceapi.service.AdServiceImpl;
import com.baharmand.marketplaceapi.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Controller class handling user-related API endpoints.</p>
 */
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserServiceImpl userService;
    private final AdServiceImpl adService;

    @Autowired
    public UserController(UserServiceImpl userService, AdServiceImpl adService) {
        this.userService = userService;
        this.adService = adService;
    }


    /**
     * <p>Authenticates a user and retrieves associated advertisements.</p>
     *
     * @param userDTOForm Form containing user credentials.
     * @return List of advertisements if authentication is successful, otherwise UNAUTHORIZED status.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> doAuthenticateAndRetrieveAds(@Valid @RequestBody UserDTOForm userDTOForm) {
        try {
            authenticateUser(userDTOForm);
            List<AdDTOView> userAds = userService.getUserAdvertisements(userDTOForm.getEmail());
            return new ResponseEntity<>(userAds, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Updates an advertisement after authenticating the user.
     *
     * @param adUpdateDTOForm Form containing updated advertisement details and user credentials.
     * @return Updated advertisement if authentication is successful, otherwise UNAUTHORIZED status.
     */
    @PostMapping("/update")
    public ResponseEntity<?> doUpdateAd(@Valid @RequestBody AdUpdateDTOForm adUpdateDTOForm) {
        try {
            authenticateUser(adUpdateDTOForm.getAdDetails().getUser());
            String userEmail = adUpdateDTOForm.getAdDetails().getUser().getEmail();
            String adId = adUpdateDTOForm.getAdId();

            if (!adService.isAdvertisementBelongsToUser(adId, userEmail)) {
                throw new AuthenticationException("User can't access this advertisement.");
            }

            AdDTOView updatedAd = adService.updateAd(adUpdateDTOForm);

            return new ResponseEntity<>(updatedAd, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }

    private void authenticateUser(UserDTOForm userDTOForm) {
        boolean isAuthenticated = userService.authenticateUser(userDTOForm);
        if (!isAuthenticated) {
            throw new AuthenticationException("Authentication failed");
        }
    }
}
