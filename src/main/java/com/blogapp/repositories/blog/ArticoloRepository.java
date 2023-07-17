package com.blogapp.repositories.blog;

import com.blogapp.entities.blog.Articolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ArticoloRepository extends JpaRepository<Articolo, Long> {
    @Query(value = "SELECT a FROM Articolo a WHERE a.approvato = false")
    List<Articolo> findAllNotApprovati();
    @Query(value = "SELECT a FROM Articolo a WHERE a.approvato = false AND a.id = :id")
    Optional<Articolo> readNotApproved(@Param(value = "id") Long id);
    @Query(value = "SELECT a FROM Articolo a WHERE a.approvato = true")
    List<Articolo> findAllApprovati();
    @Query(value = "SELECT a FROM Articolo a WHERE a.approvato = true AND a.id = :id")
    Optional<Articolo> findApprovatoById(@Param(value = "id") Long id);
    @Query(value = "SELECT a FROM Articolo a JOIN a.categorie c WHERE (c.id IN (:insIds) AND a.approvato = true)")
    List<Articolo> findByCategorie(@Param(value = "insIds") Long... idCategorie);
    @Query(value = "SELECT a FROM Articolo a JOIN a.tags t WHERE t.nome IN (:insTags) AND a.approvato = true")
    List<Articolo> findByTags(@Param(value = "insTags") String... tags);
    @Query(value = "SELECT a FROM Articolo a WHERE ((a.titolo LIKE %:parola% OR a.contenuto LIKE %:parola%) AND a.approvato = true)")
    List<Articolo> findByContenutoOrTitolo(@Param(value = "parola") String keyword);
    @Query(value = """
            SELECT a, COUNT(v.id) AS conta
            FROM Articolo a
            LEFT JOIN a.voti v
            GROUP BY a
            ORDER BY conta DESC
            """)
    List<Articolo> getAllOrderByVotesDesc();
    @Query(value = """
            SELECT a, COUNT(v.id) AS conta
            FROM Articolo a
            LEFT JOIN a.voti v
            GROUP BY a
            ORDER BY conta ASC
            """)
    List<Articolo> getAllOrderByVotesAsc();
    @Query(value = """
            SELECT a, SUM(CASE WHEN v.voto = true THEN 1 ELSE 0 END) AS likes
            FROM Articolo a
            LEFT JOIN a.voti v
            GROUP BY a
            ORDER BY likes DESC
            """)
    List<Articolo> getAllOrderedByLikes();
}
