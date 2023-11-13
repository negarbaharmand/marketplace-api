package com.example.marketplaceapi.converter;

import com.example.marketplaceapi.domain.dto.UserDTOForm;
import com.example.marketplaceapi.domain.dto.UserDTOView;
import com.example.marketplaceapi.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {

    @Override
    public UserDTOView toDTOView(User user) {
        return UserDTOView.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .build();
    }

    @Override
    public User toUser(UserDTOForm userDTOForm) {
        return User.builder()
                .email(userDTOForm.getEmail())
                .password(userDTOForm.getPassword())
                .build();
    }
}
