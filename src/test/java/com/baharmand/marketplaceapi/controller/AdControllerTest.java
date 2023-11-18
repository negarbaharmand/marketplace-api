package com.baharmand.marketplaceapi.controller;

import com.baharmand.marketplaceapi.converter.Converter;
import com.baharmand.marketplaceapi.domain.dto.AdDTOForm;
import com.baharmand.marketplaceapi.domain.dto.AdDTOView;
import com.baharmand.marketplaceapi.domain.dto.UserDTOForm;
import com.baharmand.marketplaceapi.service.AdServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdControllerTest {
    @Mock
    private AdServiceImpl adService;

    @Mock
    private Converter converter;

    @InjectMocks
    private AdController adController;


    @Test
    void doCreateAd_InvalidAdDTOForm_ShouldReturnBadRequest() {
        UserDTOForm user = new UserDTOForm("user@test.com", "password");
        AdDTOForm adDTOForm = new AdDTOForm("Car", "Car for sale.", user);

        when(adService.createAd(adDTOForm)).thenReturn(null);

        ResponseEntity<AdDTOView> response = adController.doCreateAd(adDTOForm);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(adService).createAd(adDTOForm);
    }
}
