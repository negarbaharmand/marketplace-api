package com.example.marketplaceapi.repository;

import com.example.marketplaceapi.domain.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Advertisement, String> {

    List<Advertisement> findByExpirationDateAfterAndActiveTrue(LocalDate currentDate);

    List<Advertisement> findByActiveTrue();

    @Query("select a from Advertisement a where a.creationDate between :from and :to")
    List<Advertisement> selectAdvertisementBetweenDates(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
