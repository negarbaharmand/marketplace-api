package com.baharmand.marketplaceapi.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for receiving input data to create a new advertisement.
 */
@Data
@Builder
public class AdDTOForm {

    /**
     * Title of the advertisement.
     */
    @NotBlank(message = "Title cannot be blank")
    private String title;
    /**
     * Description of the advertisement.
     */
    @NotBlank(message = "Description cannot be blank")
    private String description;

    /**
     * User associated with the advertisement.
     */
    @Valid
    @NotNull(message = "User cannot be blank")
    private UserDTOForm user;


}
