package com.example.marketplaceapi.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

/**
 * Advertisement Entity represents the advertisement table in the database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Advertisement {

    /**
     * Unique identifier for an advertisement.
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ad_id", columnDefinition = "VARCHAR(255)")
    private String adId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Column(nullable = false)
    private LocalDate expirationDate;

    /**
     * User associated with the advertisement.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //Shows if the ad is expired or not
    @Column(nullable = false)
    private boolean active = true;

    public Advertisement(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }

    /**
     * Sets the default expiration date for the advertisement before persisting it.
     */
    @PrePersist
    public void setDefaultExpirationDate() {
        this.creationDate = LocalDate.now();
        this.expirationDate = creationDate.plusDays(10);
        System.out.println("Expiration Date: " + this.expirationDate);

    }

    /**
     * Checks if the advertisement has expired based on the current date.
     *
     * @return true if the advertisement has expired, false otherwise.
     */
    public boolean hasExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }
}
