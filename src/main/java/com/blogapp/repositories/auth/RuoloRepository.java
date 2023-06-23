package com.blogapp.repositories.auth;

import com.blogapp.entities.auth.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    Optional<Ruolo> findByAuthority(String authority);
}
