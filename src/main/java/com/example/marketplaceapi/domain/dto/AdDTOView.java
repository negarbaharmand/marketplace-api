package com.example.marketplaceapi.domain.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdDTOView {
    private Long adId;
    private String title;
    private String description;
    private LocalDate creationDate;
    private LocalDate expirationDate;

}
