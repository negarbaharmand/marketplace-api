package com.example.marketplaceapi.domain.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTOView {

    private Long userId;
    private String email;

}
