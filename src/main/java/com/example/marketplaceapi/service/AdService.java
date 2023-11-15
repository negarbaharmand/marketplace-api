package com.example.marketplaceapi.service;

import com.example.marketplaceapi.domain.dto.AdDTOForm;
import com.example.marketplaceapi.domain.dto.AdDTOView;
import com.example.marketplaceapi.domain.dto.UserDTOForm;
import com.example.marketplaceapi.domain.dto.UserDTOView;

import java.util.List;

public interface AdService {


    AdDTOView createAd(AdDTOForm adDTOForm);

    List<AdDTOView> getAllAds();

    AdDTOView getAdById(String id);

    List<AdDTOView> getActiveAdvertisements();

    void deleteAd(String id);

}
