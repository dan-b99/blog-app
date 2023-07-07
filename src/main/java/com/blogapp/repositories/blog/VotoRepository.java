package com.blogapp.repositories.blog;

import com.blogapp.entities.blog.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findByUtenteIdAndArticoloId(Long utenteId, Long articoloId);
}
