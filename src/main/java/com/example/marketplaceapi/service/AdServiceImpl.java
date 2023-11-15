package com.example.marketplaceapi.service;

import com.example.marketplaceapi.converter.AdConverter;
import com.example.marketplaceapi.domain.dto.AdDTOForm;
import com.example.marketplaceapi.domain.dto.AdDTOView;
import com.example.marketplaceapi.domain.entity.Advertisement;
import com.example.marketplaceapi.domain.entity.User;
import com.example.marketplaceapi.exception.DataNotFoundException;
import com.example.marketplaceapi.repository.AdRepository;
import com.example.marketplaceapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AdConverter adConverter;


    @Autowired
    public AdServiceImpl(AdRepository adRepository, UserRepository userRepository, UserService userService, AdConverter adConverter) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.adConverter = adConverter;
    }


    @Override
    public AdDTOView createAd(AdDTOForm adDTOForm) {
        Advertisement advertisement = adConverter.toAdvertisement(adDTOForm);
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
        return adConverter.toAdDTOView(advertisement);

    }

    @Override
    public List<AdDTOView> getAllAds() {
        List<Advertisement> advertisements = adRepository.findAll();
        return advertisements.stream()
                .map(adConverter::toAdDTOView)
                .collect(Collectors.toList());
    }

    @Override
    public AdDTOView getAdById(String id) {
        Advertisement ad = adRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Advertisement was not found"));

        return adConverter.toAdDTOView(ad);

    }

    @Override
    public List<AdDTOView> getActiveAdvertisements() {
        List<Advertisement> activeAdvertisements = adRepository.findByActiveTrue();
        return activeAdvertisements.stream()
                .map(adConverter::toAdDTOView)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAd(String id) {
        adRepository.deleteById(id);
    }

    @Scheduled(cron = "0 0 0 * * *") // This will run the task every day at midnight
    public void updateAdStatus() {
        LocalDate currentDate = LocalDate.now();
        List<Advertisement> expiredAds = adRepository.findByExpirationDateAfterAndActiveTrue(currentDate);

        for (Advertisement ad : expiredAds) {
            ad.setActive(false);
        }
        adRepository.saveAll(expiredAds);
    }
}
