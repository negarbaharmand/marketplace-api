package com.example.marketplaceapi.service;

import com.example.marketplaceapi.domain.dto.UserDTOForm;
import com.example.marketplaceapi.domain.dto.UserDTOView;
import com.example.marketplaceapi.domain.entity.User;
import com.example.marketplaceapi.domain.exception.DataDuplicateException;
import com.example.marketplaceapi.domain.exception.DataNotFoundException;
import com.example.marketplaceapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTOView register(UserDTOForm userDTOForm) {
        if (userDTOForm == null) throw new IllegalArgumentException("user form is null.");
        boolean isExistEmail = userRepository.existsByEmail(userDTOForm.getEmail());
        if (isExistEmail) throw new DataDuplicateException("Email does already exist.");
        User user = User.builder()
                .email(userDTOForm.getEmail())
                .password(userDTOForm.getPassword())
                .build();

        User savedUser = userRepository.save(user);
        return UserDTOView.builder()
                .userId(savedUser.getUserId())
                .email(savedUser.getEmail())
                .build();
    }

    @Override
    public UserDTOView getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("Email does not exist."));
        return UserDTOView.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .build();
    }

    @Override
    public void disableByEmail(String email) {
        isEmailTaken(email);
        userRepository.updateExpiredByEmail(email, true);
    }

    @Override
    public void enableByEmail(String email) {
        isEmailTaken(email);
        userRepository.updateExpiredByEmail(email, false);
    }

    @Override
    public List<UserDTOView> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> UserDTOView.builder()
                        .userId(user.getUserId())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UserDTOView update(UserDTOForm userDTOForm) {
        User user = userRepository.findById(userDTOForm.getId()).orElseThrow(() -> new DataNotFoundException("User Id is not valid."));

        return UserDTOView.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .build();
    }


    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User id is not valid."));
        userRepository.delete(user);
    }

    private void isEmailTaken(String email) {
        if (!userRepository.existsByEmail(email))
            throw new DataNotFoundException("Email does not exist.");
    }
}