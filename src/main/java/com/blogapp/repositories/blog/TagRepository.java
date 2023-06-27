package com.blogapp.repositories.blog;

import com.blogapp.entities.blog.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByNome(String nome);
}
