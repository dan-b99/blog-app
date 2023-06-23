package com.blogapp.repositories.blog;

import com.blogapp.entities.blog.Commento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentoRepository extends JpaRepository<Commento, Long> {
}
