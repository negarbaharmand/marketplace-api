package com.baharmand.marketplaceapi.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdUpdateDTOForm {
    /**
     * Unique identifier for the advertisement.
     */
    @NotBlank(message = "Advertisement's id cannot be null.")
    private String adId;

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
