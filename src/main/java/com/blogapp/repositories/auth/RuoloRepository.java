package com.blogapp.repositories.auth;

import com.blogapp.entities.auth.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
}
