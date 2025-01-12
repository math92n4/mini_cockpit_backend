package com.example.mini_cockpit_backend.repository;

import com.example.mini_cockpit_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
}
