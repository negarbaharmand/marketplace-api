package com.baharmand.marketplaceapi.service;

import com.baharmand.marketplaceapi.domain.dto.AdDTOForm;
import com.baharmand.marketplaceapi.domain.dto.AdDTOView;
import com.baharmand.marketplaceapi.domain.dto.AdUpdateDTOForm;

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
     * Updates an existing advertisement based on the provided form.
     *
     * @param adDTOForm Form containing information for updating the advertisement.
     * @return View representation of the updated advertisement.
     */
    AdDTOView updateAd(AdUpdateDTOForm adDTOForm);

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
    /**
     * Checks if the advertisement belongs to user or not.
     *
     * @param adId The id associated with the advertisement
     * @param userEmail User's email
     * @return A boolean showing if the email matches the email associated withe advertisement or not
     * */
    boolean isAdvertisementBelongsToUser(String adId, String userEmail);


}
