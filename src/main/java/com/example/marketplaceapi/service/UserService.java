package com.example.marketplaceapi.service;

import com.example.marketplaceapi.domain.dto.UserDTOForm;
import com.example.marketplaceapi.domain.dto.UserDTOView;

import java.util.List;

public interface UserService {
    UserDTOView register(UserDTOForm userDTOForm);

    UserDTOView getByEmail(String email);

    void disableByEmail(String email);

    void enableByEmail(String email);

    List<UserDTOView> findAll();
    UserDTOView update(UserDTOForm userDTOForm);
    void delete(Long id);
}
