package com.example.marketplaceapi.converter;

import com.example.marketplaceapi.domain.dto.AdDTOForm;
import com.example.marketplaceapi.domain.dto.AdDTOView;
import com.example.marketplaceapi.domain.entity.Advertisement;
import org.springframework.stereotype.Component;

@Component
public class AdConverterImpl implements AdConverter{
    @Override
    public AdDTOView toAdDTOView(Advertisement ad) {
        return AdDTOView.builder()
                .adId(ad.getAdId())
                .title(ad.getTitle())
                .description(ad.getDescription())
                .creationDate(ad.getCreationDate())
                .expirationDate(ad.getExpirationDate())
                .build();
    }

    @Override
    public Advertisement toAdvertisement(AdDTOForm adDTOForm) {
        return Advertisement.builder()
                .title(adDTOForm.getTitle())
                .description(adDTOForm.getDescription())
                .build();
    }
}
