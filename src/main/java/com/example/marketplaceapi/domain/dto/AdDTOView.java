package com.example.marketplaceapi.domain.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for viewing advertisement details.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdDTOView {
    /**
     * Unique identifier for the advertisement.
     */
    private String adId;

    /**
     * Title of the advertisement.
     */
    private String title;

    /**
     * Description of the advertisement.
     */
    private String description;

    /**
     * Date when the advertisement was created.
     */
    private LocalDate creationDate;

    /**
     * Date when the advertisement will expire.
     */
    private LocalDate expirationDate;

    /**
     * Email of the user associated with the advertisement.
     */
    private String email;

}
