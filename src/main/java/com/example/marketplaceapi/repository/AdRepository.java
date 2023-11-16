package com.example.marketplaceapi.repository;

import com.example.marketplaceapi.domain.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing Advertisement entities in the database.
 */
@Repository
public interface AdRepository extends JpaRepository<Advertisement, String> {

    /**
     * Retrieves a list of active advertisements.
     *
     * @return List of active Advertisement entities.
     */
    List<Advertisement> findByActiveTrue();

    /**
     * Retrieves a list of advertisements created between the specified dates.
     *
     * @param from The start date for the range.
     * @param to   The end date for the range.
     * @return List of Advertisement entities created between the specified dates.
     */
    @Query("select a from Advertisement a where a.creationDate between :from and :to")
    List<Advertisement> selectAdvertisementBetweenDates(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
