package com.example.marketplaceapi.service;

import com.example.marketplaceapi.domain.dto.AdDTOForm;
import com.example.marketplaceapi.domain.dto.AdDTOView;

import java.util.List;

/**
 * Service interface for advertisement-related operations.
 */
public interface AdService {

    /**
     * Creates a new advertisement based on the provided form.
     *
     * @param adDTOForm Form containing information for creating the advertisement.
     * @return View representation of the created advertisement.
     */
    AdDTOView createAd(AdDTOForm adDTOForm);

    /**
     * Retrieves a list of active advertisements.
     *
     * @return List of active advertisements.
     */
    List<AdDTOView> getActiveAdvertisements();

    /**
     * Retrieves advertisements between today and specified number of days.
     *
     * @param daysAgo Number of days ago to consider for filtering advertisements.
     * @return List of advertisements within the specified time frame.
     */
    List<AdDTOView> getAdsForDaysAgo(Integer daysAgo);

}
