package com.blogapp.entities.blog;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Transactional
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nome;
    @ManyToMany(mappedBy = "categorie")
    private List<Articolo> articoli = new ArrayList<>();

    public Categoria(String nome) {
        this.nome = nome;
    }
}
