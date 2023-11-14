package com.example.marketplaceapi.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Advertisement {

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

    @PrePersist
    public void setDefaultExpirationDate() {
        this.creationDate = LocalDate.now();
        this.expirationDate = creationDate.plusDays(10);
        System.out.println("Expiration Date: " + this.expirationDate);


    }
}
