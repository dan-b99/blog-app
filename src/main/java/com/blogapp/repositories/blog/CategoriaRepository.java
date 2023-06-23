package com.blogapp.repositories.blog;

import com.blogapp.entities.blog.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
