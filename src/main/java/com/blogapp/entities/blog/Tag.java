package com.blogapp.entities.blog;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.SQLException;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Transactional(rollbackOn = {SQLException.class, RuntimeException.class})
@DynamicUpdate
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nome;
    @ManyToMany
    private List<Articolo> articoli;

    public Tag(String nome, List<Articolo> articoli) {
        this.nome = nome;
        this.articoli = articoli;
    }
}
