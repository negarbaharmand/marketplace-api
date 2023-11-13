package com.example.marketplaceapi.converter;

import com.example.marketplaceapi.domain.dto.UserDTOForm;
import com.example.marketplaceapi.domain.dto.UserDTOView;
import com.example.marketplaceapi.domain.entity.User;

public interface UserConverter {
    UserDTOView toDTOView(User user);
    User toUser(UserDTOForm userDTOForm);
}
