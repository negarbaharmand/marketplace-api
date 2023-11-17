package com.baharmand.marketplaceapi.controller;

import com.baharmand.marketplaceapi.domain.dto.AdDTOForm;
import com.baharmand.marketplaceapi.domain.dto.AdDTOView;
import com.baharmand.marketplaceapi.service.AdServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Controller class handling advertisement-related API endpoints.</p>
 */
@RequestMapping("/api/v1/ads")
@RestController
@Validated
public class AdController {

    AdServiceImpl adService;

    @Autowired
    public AdController(AdServiceImpl adService) {
        this.adService = adService;
    }

    /**
     * <p>Creates a new advertisement.</p>
     *
     * @param adDTOForm Form containing advertisement details.
     * @return Created advertisement if successful, otherwise BAD_REQUEST status.
     */
    @PostMapping
    public ResponseEntity<AdDTOView> doCreateAd(@Valid @RequestBody AdDTOForm adDTOForm) {
        AdDTOView createdAd = adService.createAd(adDTOForm);
        if (createdAd != null) {
            return new ResponseEntity<>(createdAd, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * <p>Retrieves a list of active advertisements.</p>
     *
     * @return List of active advertisements.
     */
    @GetMapping("/active")
    public ResponseEntity<List<AdDTOView>> doGetActiveAdvertisements() {
        List<AdDTOView> activeAds = adService.getActiveAdvertisements();
        return new ResponseEntity<>(activeAds, HttpStatus.OK);
    }

    /**
     * <p>Retrieves advertisements from a specified number of days ago.</p>
     *
     * @param daysAgo Number of days ago to retrieve advertisements.
     * @return List of advertisements if successful, otherwise BAD_REQUEST status.
     */
    @GetMapping("/for-days-ago")
    public ResponseEntity<List<AdDTOView>> doGetAdsDaysAgo(@RequestParam(required = true)
                                                           @Min(value = 1, message = "daysAgo must be greater than or equal to 1")
                                                           @Max(value = 10, message = "daysAgo must be less than or equal to 10")
                                                           Integer daysAgo) {
        List<AdDTOView> foundAds = adService.getAdsForDaysAgo(daysAgo);
        return new ResponseEntity<>(foundAds, HttpStatus.OK);
    }


}
