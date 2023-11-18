package com.baharmand.marketplaceapi.service;

import com.baharmand.marketplaceapi.converter.Converter;
import com.baharmand.marketplaceapi.domain.dto.UserDTOForm;
import com.baharmand.marketplaceapi.domain.entity.User;
import com.baharmand.marketplaceapi.exception.DataDuplicateException;
import com.baharmand.marketplaceapi.repository.UserRepository;
import com.baharmand.marketplaceapi.util.CustomPasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Converter converter;

    @Mock
    private CustomPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void register_ValidUserDTOForm_ShouldReturnRegisteredUser() {
        UserDTOForm userDTOForm = new UserDTOForm("test@example.com", "password");
        User user = new User("test@example.com", "encodedPassword");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(converter.toUser(userDTOForm)).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User registeredUser = userService.register(userDTOForm);

        assertNotNull(registeredUser);
        assertEquals("test@example.com", registeredUser.getEmail());
        assertEquals("encodedPassword", registeredUser.getPassword());

        verify(userRepository).existsByEmail("test@example.com");
        verify(converter).toUser(userDTOForm);
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(user);
    }

    @Test
    void register_DuplicateUserEmail_ShouldThrowDataDuplicateException() {
        UserDTOForm userDTOForm = new UserDTOForm("test@example.com", "password");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertThrows(DataDuplicateException.class, () -> userService.register(userDTOForm));

        verify(userRepository).existsByEmail("test@example.com");
        verify(converter, never()).toUser(userDTOForm);
        verify(passwordEncoder, never()).encode("password");
        verify(userRepository, never()).save(any());
    }

}
