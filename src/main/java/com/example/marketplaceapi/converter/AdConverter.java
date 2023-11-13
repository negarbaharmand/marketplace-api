package com.example.marketplaceapi.converter;

import com.example.marketplaceapi.domain.dto.AdDTOForm;
import com.example.marketplaceapi.domain.dto.AdDTOView;
import com.example.marketplaceapi.domain.entity.Advertisement;


public interface AdConverter {
    AdDTOView toAdDTOView(Advertisement advertisement);
    Advertisement toAdvertisement(AdDTOForm adDTOForm);
}
