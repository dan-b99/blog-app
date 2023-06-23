package com.blogapp.repositories.blog;

import com.blogapp.entities.blog.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {
}
