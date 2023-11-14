package com.example.marketplaceapi.controller;

import com.example.marketplaceapi.domain.dto.AdDTOForm;
import com.example.marketplaceapi.domain.dto.AdDTOView;
import com.example.marketplaceapi.service.AdServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/ads")
@RestController
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

}
