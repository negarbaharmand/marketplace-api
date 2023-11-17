package com.baharmand.marketplaceapi.converter;

import com.baharmand.marketplaceapi.domain.dto.AdDTOForm;
import com.baharmand.marketplaceapi.domain.dto.AdDTOView;
import com.baharmand.marketplaceapi.domain.dto.UserDTOForm;
import com.baharmand.marketplaceapi.domain.entity.Advertisement;
import com.baharmand.marketplaceapi.domain.entity.User;
import org.springframework.stereotype.Component;

/**
 * Converter class responsible for converting between different data transfer objects (DTOs) and entities.
 */
@Component
public class Converter {

    /**
     * Converts an Advertisement entity to its corresponding view DTO.
     *
     * @param ad The Advertisement entity to convert.
     * @return The AdDTOView representing the Advertisement for viewing purposes.
     */
    public AdDTOView toAdDTOView(Advertisement ad) {
        return AdDTOView.builder()
                .adId(ad.getAdId())
                .title(ad.getTitle())
                .description(ad.getDescription())
                .creationDate(ad.getCreationDate())
                .expirationDate(ad.getExpirationDate())
                .email(ad.getUser().getEmail())
                .build();
    }

    /**
     * Converts a UserDTOForm to a User entity.
     *
     * @param userDTOForm The UserDTOForm to convert.
     * @return The User entity representing the user.
     */
    public User toUser(UserDTOForm userDTOForm) {
        return User.builder()
                .email(userDTOForm.getEmail())
                .password(userDTOForm.getPassword())
                .build();
    }

    /**
     * Converts an AdDTOForm to an Advertisement entity.
     *
     * @param adDTOForm The AdDTOForm to convert.
     * @return The Advertisement entity representing the advertisement.
     */
    public Advertisement toAdvertisement(AdDTOForm adDTOForm) {
        User user = this.toUser(adDTOForm.getUser());
        return Advertisement.builder()
                .title(adDTOForm.getTitle())
                .description(adDTOForm.getDescription())
                .user(user)
                .active(true)
                .build();
    }

}
