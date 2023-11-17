package com.baharmand.marketplaceapi.service;

import com.baharmand.marketplaceapi.converter.Converter;
import com.baharmand.marketplaceapi.domain.dto.AdDTOForm;
import com.baharmand.marketplaceapi.domain.dto.AdDTOView;
import com.baharmand.marketplaceapi.domain.dto.AdUpdateDTOForm;
import com.baharmand.marketplaceapi.domain.entity.Advertisement;
import com.baharmand.marketplaceapi.domain.entity.User;
import com.baharmand.marketplaceapi.exception.AuthenticationException;
import com.baharmand.marketplaceapi.exception.DataNotFoundException;
import com.baharmand.marketplaceapi.repository.AdRepository;
import com.baharmand.marketplaceapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
            boolean isAuthenticated = userService.authenticateUser(adDTOForm.getUser());

            if (!isAuthenticated) {
                // If authentication fails, throw an error
                throw new AuthenticationException("Authentication failed. Please enter the correct password.");
            }

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
    public AdDTOView updateAd(AdUpdateDTOForm adDTOForm) {
        //1. Retrieve the existing advertisement
        Advertisement existingAd = adRepository.findById(adDTOForm.getAdId())
                .orElseThrow(() -> new EntityNotFoundException("Advertisement not found"));

        //2. Update the fields
        existingAd.setTitle(adDTOForm.getTitle());
        existingAd.setDescription(adDTOForm.getDescription());

        //3. Save the updated advertisement
        Advertisement updatedAd = adRepository.save(existingAd);

        //4. Convert and return the updated advertisement
        return converter.toAdDTOView(updatedAd);
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

    @Override
    public boolean isAdvertisementBelongsToUser(String adId, String userEmail) {
        Optional<Advertisement> optionalAdvertisement = adRepository.findById(adId);

        return optionalAdvertisement.isPresent() &&
                optionalAdvertisement.get().getUser().getEmail().equals(userEmail);

    }
}
