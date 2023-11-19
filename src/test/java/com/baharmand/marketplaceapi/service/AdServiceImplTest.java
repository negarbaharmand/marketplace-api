package com.baharmand.marketplaceapi.service;

import com.baharmand.marketplaceapi.converter.Converter;
import com.baharmand.marketplaceapi.domain.dto.AdDTOForm;
import com.baharmand.marketplaceapi.domain.dto.AdDTOView;
import com.baharmand.marketplaceapi.domain.dto.UserDTOForm;
import com.baharmand.marketplaceapi.domain.entity.Advertisement;
import com.baharmand.marketplaceapi.domain.entity.User;
import com.baharmand.marketplaceapi.repository.AdRepository;
import com.baharmand.marketplaceapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link AdServiceImpl} class, focusing on validating
 * the functionality of methods related to advertisement creation and retrieval.
 * Uses the {@link MockitoExtension} for simplified Mockito integration with JUnit 5.
 */
@ExtendWith(MockitoExtension.class)
class AdServiceImplTest {

    @Mock
    private AdRepository adRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private Converter converter;

    @InjectMocks
    private AdServiceImpl adService;


    /**
     * Tests the {@link AdServiceImpl#createAd(AdDTOForm)} method with a valid
     * {@link AdDTOForm}. Verifies that the service correctly processes the form,
     * registers the user, saves the advertisement, and returns the created
     * advertisement as a {@link AdDTOView}.
     */
    @Test
    void createAd_ValidAdDTOForm_ShouldReturnAdDTOView() {
        AdDTOForm adDTOForm = new AdDTOForm();
        adDTOForm.setTitle("Test Ad");
        adDTOForm.setDescription("This is a test ad description");
        UserDTOForm userDTOForm = new UserDTOForm("test@example.com", "password");
        adDTOForm.setUser(userDTOForm);

        User user = new User("test@example.com", "password");
        Advertisement advertisement = new Advertisement();
        advertisement.setTitle("Test Ad");
        advertisement.setDescription("This is a test ad description");
        advertisement.setUser(user);

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userService.register(userDTOForm)).thenReturn(user);
        when(converter.toAdvertisement(adDTOForm)).thenReturn(advertisement);
        when(adRepository.save(any(Advertisement.class))).thenReturn(advertisement);
        when(converter.toAdDTOView(advertisement)).thenReturn(new AdDTOView(advertisement.getAdId(), advertisement.getTitle(), advertisement.getDescription(), advertisement.getCreationDate(), advertisement.getExpirationDate(), advertisement.getUser().getEmail()));

        AdDTOView createdAd = adService.createAd(adDTOForm);

        assertNotNull(createdAd);
        assertEquals("Test Ad", createdAd.getTitle());
        assertEquals("This is a test ad description", createdAd.getDescription());

        verify(userRepository).existsByEmail(user.getEmail());
        verify(userService).register(userDTOForm);
        verify(converter).toAdvertisement(adDTOForm);
        verify(adRepository).save(any(Advertisement.class));
        verify(converter).toAdDTOView(advertisement);
    }

    /**
     * Tests the {@link AdServiceImpl#getActiveAdvertisements()} method to ensure
     * that it correctly retrieves a list of active advertisements and converts
     * them into a list of {@link AdDTOView}s.
     */
    @Test
    void getActiveAdvertisements_ShouldReturnListOfAdDTOView() {
        Advertisement ad1 = new Advertisement("Ad1", "Description 1", new User());
        Advertisement ad2 = new Advertisement("Ad2", "Description 2", new User());

        List<Advertisement> activeAds = new ArrayList<>();
        activeAds.add(ad1);
        activeAds.add(ad2);

        when(adRepository.findByActiveTrue()).thenReturn(activeAds);
        when(converter.toAdDTOView(ad1)).thenReturn(new AdDTOView(ad1.getAdId(), ad1.getTitle(), ad1.getDescription(), ad1.getCreationDate(), ad1.getExpirationDate(), ad1.getUser().getEmail()));
        when(converter.toAdDTOView(ad2)).thenReturn(new AdDTOView(ad2.getAdId(), ad2.getTitle(), ad2.getDescription(), ad2.getCreationDate(), ad2.getExpirationDate(), ad2.getUser().getEmail()));

        List<AdDTOView> activeAdDTOs = adService.getActiveAdvertisements();

        assertNotNull(activeAdDTOs);
        assertEquals(2, activeAdDTOs.size());

        verify(adRepository).findByActiveTrue();
        verify(converter).toAdDTOView(ad1);
        verify(converter).toAdDTOView(ad2);
    }
}

