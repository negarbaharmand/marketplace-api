package com.example.marketplaceapi.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * User Entity represents user table in the database
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "_user")
public class User {
    /**
     * Unique identifier for a user.
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id", columnDefinition = "VARCHAR(255)")
    private String userId;

    /**
     * User's email address, must be unique.
     */
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    /**
     * List of advertisements associated with the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Advertisement> advertisements = new ArrayList<>();

    /**
     * List of advertisements associated with the user.
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Adds an advertisement to the user's list of advertisements.
     *
     * @param advertisement The advertisement to be added.
     * @throws IllegalArgumentException If the provided advertisement is null.
     */
    public void addAdvertisement(Advertisement advertisement) {
        if (advertisement == null) throw new IllegalArgumentException("Advertisement is null.");

        if (advertisements == null) {
            advertisements = new ArrayList<>();
        }

        this.advertisements.add(advertisement);

        if (advertisement.getUser() != null) {
            advertisement.setUser(this);
        }
    }

    /**
     * Removes expired advertisements from the user's list.
     * An advertisement is considered expired if its expiration date is before the current date.
     */
    public void removeExpiredAdvertisements() {
        List<Advertisement> advertisementsToRemove = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (Advertisement ad : advertisements) {
            if (ad.getExpirationDate().isBefore(currentDate)) {
                advertisementsToRemove.add(ad);
            }
        }

        advertisements.removeAll(advertisementsToRemove);
        advertisementsToRemove.forEach(advertisement -> advertisement.setUser(null));
    }

}
