package com.blogapp.entities.blog;

import com.blogapp.entities.auth.Utente;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Transactional
@DynamicUpdate
@Entity
public class Articolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String titolo;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenuto;
    @Column(nullable = false)
    @ManyToMany(mappedBy = "articoli")
    private Set<Tag> tags = new HashSet<>();
    @JoinColumn(name = "utente_id")
    @ManyToOne
    private Utente utente;
    @ManyToMany
    private Set<Categoria> categorie;
    @OneToMany(mappedBy = "articolo")
    private List<Commento> commenti = new ArrayList<>();
    @OneToMany(mappedBy = "articolo")
    private List<Voto> voti = new ArrayList<>();
    private boolean approvato;

    public Articolo(String titolo, String contenuto, Utente utente, Set<Categoria> categorie) {
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.utente = utente;
        this.categorie = categorie;
    }
}
