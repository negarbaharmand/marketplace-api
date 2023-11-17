package com.baharmand.marketplaceapi.repository;

import com.baharmand.marketplaceapi.domain.entity.Advertisement;
import com.baharmand.marketplaceapi.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AdRepositoryTest {

    AdRepository adRepository;
    UserRepository userRepository;

    @Autowired
    public AdRepositoryTest(AdRepository adRepository, UserRepository userRepository) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setUp() {
        User user = userRepository.save(new User("test@gmail.com", "password"));
        Advertisement advertisement1 = new Advertisement("Ad1", "This is ad 1.", user);
        Advertisement advertisement2 = new Advertisement("Ad2", "This is ad 2.", user);
        Advertisement advertisement3 = new Advertisement("Ad3", "This is ad 3.", user);
        List<Advertisement> advertisements = new ArrayList<>(Arrays.asList(advertisement1, advertisement2, advertisement3));
        adRepository.saveAll(advertisements);
    }

    @Test
    public void testFindByActiveTrue() {
      List<Advertisement> activeAds = adRepository.findByActiveTrue();
        assertNotNull(activeAds);
        assertFalse(activeAds.isEmpty());
        assertTrue(activeAds.stream().allMatch(Advertisement::isActive));

    }

    @Test
    public void TestSelectAdvertisementBetweenDates() {
        List<Advertisement> activeAds = adRepository.selectAdvertisementBetweenDates(LocalDate.now().minusDays(7), LocalDate.now());
        assertEquals(3, activeAds.size());

    }
}
