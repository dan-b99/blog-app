package com.blogapp.repositories.blog;

import com.blogapp.entities.blog.Articolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ArticoloRepository extends JpaRepository<Articolo, Long> {
    @Query(value = "SELECT a FROM Articolo a JOIN a.categorie c WHERE c.id IN (:insIds)")
    List<Articolo> findByCategorie(@Param(value = "insIds") Long... idCategorie);
    @Query(value = "SELECT a FROM Articolo a JOIN a.tags t WHERE t.id IN (:insIds)")
    List<Articolo> findByTags(@Param(value = "insIds") Long... idTags);
    /*@Query(value = """
    SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END
    FROM Articolo a JOIN a.voti v
    WHERE a.id = :articoloId AND v.utente.id = :userId
    """)
    boolean existsVotoByUtenteId(@Param(value = "articoloId") Long articoloId, @Param(value = "userId") Long userId);*/
}
