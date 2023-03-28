package com.example.security.repository;

import com.example.security.domain.Secret;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecretRepository extends JpaRepository<Secret, Long> {
    Optional<Secret> findByLink(String link);
}
