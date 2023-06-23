package com.blogapp.repositories.blog;

import com.blogapp.entities.blog.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
