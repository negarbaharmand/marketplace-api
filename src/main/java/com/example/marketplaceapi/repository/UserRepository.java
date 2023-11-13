package com.example.marketplaceapi.repository;

import com.example.marketplaceapi.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("update User u set u.password = :password where u.email = :email")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String newPassword);
}
