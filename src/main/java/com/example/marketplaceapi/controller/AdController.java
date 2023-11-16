package com.example.marketplaceapi.controller;

import com.example.marketplaceapi.domain.dto.AdDTOForm;
import com.example.marketplaceapi.domain.dto.AdDTOView;
import com.example.marketplaceapi.service.AdServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/v1/ads")
@RestController
@Validated
public class AdController {

    AdServiceImpl adService;

    @Autowired
    public AdController(AdServiceImpl adService) {
        this.adService = adService;
    }

    @PostMapping
    public ResponseEntity<AdDTOView> doCreateAd(@Valid @RequestBody AdDTOForm adDTOForm) {
        AdDTOView createdAd = adService.createAd(adDTOForm);
        if (createdAd != null) {
            return new ResponseEntity<>(createdAd, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/active")
    public ResponseEntity<List<AdDTOView>> doGetActiveAdvertisements() {
        List<AdDTOView> activeAds = adService.getActiveAdvertisements();
        return new ResponseEntity<>(activeAds, HttpStatus.OK);
    }

    @GetMapping("/for-days-ago")
    public ResponseEntity<List<AdDTOView>> doGetAdsDaysAgo(@RequestParam(required = true)
                                                               @Min(value = 1, message = "daysAgo must be greater than or equal to 1")
                                                               @Max(value = 10, message = "daysAgo must be less than or equal to 10")
                                                               Integer daysAgo) {
        List<AdDTOView> foundAds = adService.getAdsForDaysAgo(daysAgo);
        return new ResponseEntity<>(foundAds, HttpStatus.OK);
    }


}
