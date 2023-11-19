package com.baharmand.marketplaceapi.controller;

import com.baharmand.marketplaceapi.converter.Converter;
import com.baharmand.marketplaceapi.domain.dto.AdDTOForm;
import com.baharmand.marketplaceapi.domain.dto.AdDTOView;
import com.baharmand.marketplaceapi.domain.dto.UserDTOForm;
import com.baharmand.marketplaceapi.exception.DataDuplicateException;
import com.baharmand.marketplaceapi.service.AdServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link AdController} class.
 */
@ExtendWith(MockitoExtension.class)
public class AdControllerTest {
    @Mock
    private AdServiceImpl adService;

    @Mock
    private Converter converter;

    @InjectMocks
    private AdController adController;

    /**
     * Tests the behavior of {@link AdController#doCreateAd(AdDTOForm)} when an invalid AdDTOForm is provided.
     * Expects a response with HTTP status code 400 (Bad Request).
     */
    @Test
    void doCreateAd_InvalidAdDTOForm_ShouldReturnBadRequest() {
        UserDTOForm user = new UserDTOForm("user@test.com", "password");
        AdDTOForm adDTOForm = new AdDTOForm("Car", "Car for sale.", user);

        when(adService.createAd(adDTOForm)).thenReturn(null);

        ResponseEntity<AdDTOView> response = adController.doCreateAd(adDTOForm);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(adService).createAd(adDTOForm);
    }

    /**
     * Tests the behavior of {@link AdController#doCreateAd(AdDTOForm)} when a {@link DataDuplicateException} occurs.
     * Expects a thrown exception with the message "Duplicate advertisement" and a response with HTTP status code 409 (Conflict).
     */
    @Test
    void doCreateAd_DataDuplicateException_ShouldReturnConflict() {
        UserDTOForm user = new UserDTOForm("user@test.com", "password");
        AdDTOForm adDTOForm = new AdDTOForm("Car", "Car for sale.", user);

        when(adService.createAd(adDTOForm)).thenThrow(new DataDuplicateException("Duplicate advertisement"));
        DataDuplicateException exception = assertThrows(DataDuplicateException.class, () -> {
            adController.doCreateAd(adDTOForm);
        });
        Assertions.assertEquals("Duplicate advertisement", exception.getMessage());

        verify(adService).createAd(adDTOForm);
    }

}
