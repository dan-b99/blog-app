package com.blogapp.repositories.blog;

import com.blogapp.entities.blog.Commento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CommentoRepository extends JpaRepository<Commento, Long> {
    @Query(value = "SELECT c FROM Commento c JOIN c.articolo a WHERE a.id = :artId AND c.padre IS NULL")
    List<Commento> byArticoloId(@Param(value = "artId") Long id);
    @Query(value = "SELECT c FROM Commento c JOIN c.articolo a WHERE a.id = :artId AND c.padre IS NOT NULL")
    List<Commento> repliesByArticleId(@Param(value = "artId") Long id);
    @Query(value = "SELECT c.risposte FROM Commento c JOIN c.articolo a WHERE a.id = :artId AND c.id = :commId")
    List<Commento> repliesByArticoloAndId(@Param(value = "artId") Long art, @Param(value = "commId") Long commId);
}
