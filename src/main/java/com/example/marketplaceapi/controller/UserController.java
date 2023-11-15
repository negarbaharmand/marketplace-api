package com.example.marketplaceapi.controller;

import com.example.marketplaceapi.domain.dto.AdDTOView;
import com.example.marketplaceapi.domain.dto.UserDTOForm;
import com.example.marketplaceapi.domain.dto.UserDTOView;
import com.example.marketplaceapi.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<List<AdDTOView>> doAuthenticateAndRetrieveAds(@Valid @RequestBody UserDTOForm userDTOForm) {
        // Authenticate user
        boolean isAuthenticated = userService.authenticateUser(userDTOForm);
        if (!isAuthenticated) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Get advertisements for the authenticated user
        List<AdDTOView> userAds = userService.getUserAdvertisements(userDTOForm.getEmail());
        return new ResponseEntity<>(userAds, HttpStatus.OK);
    }
}
