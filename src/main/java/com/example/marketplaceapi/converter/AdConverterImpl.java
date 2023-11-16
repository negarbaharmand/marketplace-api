package com.example.marketplaceapi.converter;

import com.example.marketplaceapi.domain.dto.AdDTOForm;
import com.example.marketplaceapi.domain.dto.AdDTOView;
import com.example.marketplaceapi.domain.entity.Advertisement;
import com.example.marketplaceapi.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdConverterImpl implements AdConverter{
    private final UserConverter userConverter;

    @Autowired
    public AdConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }
    @Override
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

    @Override
    public Advertisement toAdvertisement(AdDTOForm adDTOForm) {
        User user = userConverter.toUser(adDTOForm.getUser());
        return Advertisement.builder()
                .title(adDTOForm.getTitle())
                .description(adDTOForm.getDescription())
                .user(user)
                .active(true)
                .build();
    }
}
