package com.example.marketplaceapi.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id", columnDefinition = "VARCHAR(255)")
    private String userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean expired = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Advertisement> advertisements = new ArrayList<>();


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void addAdvertisement(Advertisement advertisement) {
        if (advertisement == null) throw new IllegalArgumentException("Advertisement is null.");
        this.advertisements.add(advertisement);
        if (advertisement.getUser() != null) {
            advertisement.setUser(this);
        }
    }

    public void removeAdvertisement(Advertisement advertisement) {
        if (this.advertisements.remove(advertisement) && advertisement.getUser() == this) {
            advertisement.setUser(null);
        }
    }
}
