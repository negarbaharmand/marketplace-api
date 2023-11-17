package com.baharmand.marketplaceapi.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Data Transfer Object (DTO) representing user input for user registration.
 * Used to capture user email and password during the registration process.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTOForm {

    /**
     * User's email address.
     */
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    /**
     * User's chosen password.
     */
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
