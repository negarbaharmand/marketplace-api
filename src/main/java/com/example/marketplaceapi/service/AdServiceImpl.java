package com.example.marketplaceapi.service;

import com.example.marketplaceapi.converter.AdConverter;
import com.example.marketplaceapi.domain.dto.AdDTOForm;
import com.example.marketplaceapi.domain.dto.AdDTOView;
import com.example.marketplaceapi.domain.dto.UserDTOForm;
import com.example.marketplaceapi.domain.entity.Advertisement;
import com.example.marketplaceapi.domain.exception.DataNotFoundException;
import com.example.marketplaceapi.repository.AdvertisementRepository;
import com.example.marketplaceapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdServiceImpl implements AdService {

    private final AdvertisementRepository adRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AdConverter adConverter;

    @Autowired
    public AdServiceImpl(AdvertisementRepository adRepository, UserRepository userRepository, UserService userService, AdConverter adConverter) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.adConverter = adConverter;
    }

    @Override
    public AdDTOView createAd(AdDTOForm adDTOForm, UserDTOForm userDTOForm) {
        if (!userRepository.existsByEmail(userDTOForm.getEmail())) {
            userService.register(userDTOForm);
        }
        Advertisement advertisement = adConverter.toAdvertisement(adDTOForm);

        adRepository.save(advertisement);

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
    public AdDTOView getAdById(Long id) {
        Advertisement ad = adRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Advertisement was not found"));

        return adConverter.toAdDTOView(ad);

    }

    @Override
    public void deleteAd(Long id) {
        adRepository.deleteById(id);
    }
}
