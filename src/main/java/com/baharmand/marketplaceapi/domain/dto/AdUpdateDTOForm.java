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
     * AdDTOForm containing title, description, and user information.
     */
    @Valid
    @NotNull(message = "Ad details cannot be null")
    private AdDTOForm adDetails;
}
