package com.blogapp.repositories.blog;

import com.blogapp.entities.blog.Validazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidazioneRepository extends JpaRepository<Validazione, Long> {
    Optional<Validazione> findByCampo(String campo);
}
