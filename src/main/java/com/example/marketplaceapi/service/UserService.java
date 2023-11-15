package com.example.marketplaceapi.service;

import com.example.marketplaceapi.domain.dto.AdDTOView;
import com.example.marketplaceapi.domain.dto.UserDTOForm;
import com.example.marketplaceapi.domain.dto.UserDTOView;
import com.example.marketplaceapi.domain.entity.User;

import java.util.List;

public interface UserService {

    User register(UserDTOForm userDTOForm);

    UserDTOView getByEmail(String email);

    boolean authenticateUser(UserDTOForm userDTOForm);

    List<AdDTOView> getUserAdvertisements(String userEmail);

    void disableByEmail(String email);

    void enableByEmail(String email);

    List<UserDTOView> findAll();

    UserDTOView update(UserDTOForm userDTOForm);

    void delete(String id);
}
