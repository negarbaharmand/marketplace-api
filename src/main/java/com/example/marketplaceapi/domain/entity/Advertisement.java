package com.example.marketplaceapi.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adId;

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

    public Advertisement(String title, String description, LocalDate creationDate, User user) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.user = user;
    }

    @PrePersist
    public void setDefaultExpirationDate() {
        if (creationDate != null) {
            this.expirationDate = creationDate.plusDays(10);
        }
    }
}
