package com.example.marketplaceapi.service;

import com.example.marketplaceapi.converter.UserConverter;
import com.example.marketplaceapi.domain.dto.AdDTOView;
import com.example.marketplaceapi.domain.dto.UserDTOForm;
import com.example.marketplaceapi.domain.dto.UserDTOView;
import com.example.marketplaceapi.domain.entity.User;
import com.example.marketplaceapi.exception.DataDuplicateException;
import com.example.marketplaceapi.exception.DataNotFoundException;
import com.example.marketplaceapi.repository.UserRepository;
import com.example.marketplaceapi.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final CustomPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter, CustomPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User register(UserDTOForm userDTOForm) {
        if (userDTOForm == null) throw new IllegalArgumentException("user form is null.");
        boolean isExistEmail = userRepository.existsByEmail(userDTOForm.getEmail());
        if (isExistEmail) throw new DataDuplicateException("Email does already exist.");

        User user = userConverter.toUser(userDTOForm);
        user.setPassword(passwordEncoder.encode(userDTOForm.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDTOView getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("Email does not exist."));
        return userConverter.toDTOView(user);
    }

    @Override
    public boolean authenticateUser(UserDTOForm userDTOForm) {
        User user = userRepository.findByEmail(userDTOForm.getEmail()).orElseThrow(() -> new ArithmeticException("User not found"));

        return passwordEncoder.matches(userDTOForm.getPassword(), user.getPassword());
    }

    @Override
    public List<AdDTOView> getUserAdvertisements(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        return (user.getAdvertisements().stream()
                .map(ad -> AdDTOView.builder()
                        .adId(ad.getAdId())
                        .title(ad.getTitle())
                        .description(ad.getDescription())
                        .creationDate(ad.getCreationDate())
                        .expirationDate(ad.getExpirationDate())
                        .build())
                .collect(Collectors.toList()));


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
                .map(userConverter::toDTOView)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTOView update(UserDTOForm userDTOForm) {
        User user = userRepository.findByEmail(userDTOForm.getEmail()).orElseThrow(() -> new DataNotFoundException("User email is not valid."));

        return userConverter.toDTOView(user);
    }


    @Override
    public void delete(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User id is not valid."));
        userRepository.delete(user);
    }

    private void isEmailTaken(String email) {
        if (!userRepository.existsByEmail(email))
            throw new DataNotFoundException("Email does not exist.");
    }
}
