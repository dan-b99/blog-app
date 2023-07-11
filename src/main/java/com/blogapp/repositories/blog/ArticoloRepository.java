package com.blogapp.repositories.blog;

import com.blogapp.entities.blog.Articolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ArticoloRepository extends JpaRepository<Articolo, Long> {
    @Query(value = "SELECT a FROM Articolo a WHERE a.approvato = false")
    List<Articolo> findAllNotApprovati();
    @Query(value = "SELECT a FROM Articolo a WHERE a.approvato = true")
    List<Articolo> findAllApprovati();
    @Query(value = "SELECT a FROM Articolo a JOIN a.categorie c WHERE c.id IN (:insIds)")
    List<Articolo> findByCategorie(@Param(value = "insIds") Long... idCategorie);
    @Query(value = "SELECT a FROM Articolo a JOIN a.tags t WHERE t.nome IN (:insTags)")
    List<Articolo> findByTags(@Param(value = "insTags") String... tags);
    @Query(value = "SELECT a FROM Articolo a WHERE a.titolo LIKE %:keyword% OR a.contenuto LIKE %:keyword%")
    List<Articolo> findByContenutoOrTitolo(@Param(value = "keyword") String keyword);
}
