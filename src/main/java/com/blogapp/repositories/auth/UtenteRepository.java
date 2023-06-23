package com.blogapp.repositories.auth;

import com.blogapp.entities.auth.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
}
