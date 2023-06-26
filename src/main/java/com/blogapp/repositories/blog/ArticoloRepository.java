package com.blogapp.repositories.blog;

import com.blogapp.entities.blog.Articolo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticoloRepository extends JpaRepository<Articolo, Long> {
    List<Articolo> findByCategoria(Long idCategoria);
    List<Articolo> findByTag(Long idTag);
}
