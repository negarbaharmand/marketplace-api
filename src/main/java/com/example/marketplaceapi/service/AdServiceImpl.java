package com.example.marketplaceapi.service;

import com.example.marketplaceapi.converter.Converter;
import com.example.marketplaceapi.domain.dto.AdDTOForm;
import com.example.marketplaceapi.domain.dto.AdDTOView;
import com.example.marketplaceapi.domain.entity.Advertisement;
import com.example.marketplaceapi.domain.entity.User;
import com.example.marketplaceapi.exception.DataNotFoundException;
import com.example.marketplaceapi.repository.AdRepository;
import com.example.marketplaceapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final Converter converter;


    @Autowired
    public AdServiceImpl(AdRepository adRepository, UserRepository userRepository, UserService userService, Converter converter) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.converter = converter;
    }


    @Override
    public AdDTOView createAd(AdDTOForm adDTOForm) {
        Advertisement advertisement = converter.toAdvertisement(adDTOForm);
        User user;
        // Check if the user with the given email exists
        if (!userRepository.existsByEmail(adDTOForm.getUser().getEmail())) {
            user = userService.register(adDTOForm.getUser());
        } else {
            user = userRepository.findByEmail(adDTOForm.getUser().getEmail())
                    .orElseThrow(() -> new DataNotFoundException("User not found"));
        }
        user.addAdvertisement(advertisement);
        // Save the Advertisement
        adRepository.save(advertisement);

        // Convert and return the saved Advertisement
        return converter.toAdDTOView(advertisement);

    }

    @Override
    public List<AdDTOView> getActiveAdvertisements() {
        List<Advertisement> activeAdvertisements = adRepository.findByActiveTrue();
        return activeAdvertisements.stream()
                .map(converter::toAdDTOView)
                .collect(Collectors.toList());
    }


    @Override
    public List<AdDTOView> getAdsForDaysAgo(Integer daysAgo) {
        try {
            LocalDate start = LocalDate.now().minusDays(daysAgo);
            LocalDate end = LocalDate.now();
            List<Advertisement> adsBetween = adRepository.selectAdvertisementBetweenDates(start, end);
            return adsBetween.stream()
                    .map(converter::toAdDTOView)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataNotFoundException("No advertisement found in the period.");
        }
    }

}
