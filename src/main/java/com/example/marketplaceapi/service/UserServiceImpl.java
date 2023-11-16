package com.example.marketplaceapi.service;

import com.example.marketplaceapi.converter.Converter;
import com.example.marketplaceapi.domain.dto.AdDTOView;
import com.example.marketplaceapi.domain.dto.UserDTOForm;
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
    private final Converter converter;
    private final CustomPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Converter converter, CustomPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(UserDTOForm userDTOForm) {
        if (userDTOForm == null) throw new IllegalArgumentException("user form is null.");
        boolean isExistEmail = userRepository.existsByEmail(userDTOForm.getEmail());
        if (isExistEmail) throw new DataDuplicateException("Email does already exist.");

        User user = converter.toUser(userDTOForm);
        user.setPassword(passwordEncoder.encode(userDTOForm.getPassword()));
        return userRepository.save(user);
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

        user.removeExpiredAdvertisements();

        return (user.getAdvertisements().stream()
                .map(converter::toAdDTOView)
                .collect(Collectors.toList()));


    }

    private void isEmailTaken(String email) {
        if (!userRepository.existsByEmail(email))
            throw new DataNotFoundException("Email does not exist.");
    }
}
