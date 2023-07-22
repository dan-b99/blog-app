package com.blogapp.repositories.blog;

import com.blogapp.entities.blog.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findByUtenteIdAndArticoloId(Long utenteId, Long articoloId);
    @Query(value = "SELECT v FROM Voto v JOIN v.articolo a WHERE a.id = :artId AND v.voto = true")
    List<Voto> getLikesByArticoloId(@Param(value = "artId") Long articoloId);
    @Query(value = "SELECT v FROM Voto v JOIN v.articolo a WHERE a.id = :artId AND v.voto = false")
    List<Voto> getDislikesByArticoloId(@Param(value = "artId") Long articoloId);
}
