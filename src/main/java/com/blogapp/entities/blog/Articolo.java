package com.blogapp.entities.blog;

import com.blogapp.entities.auth.Utente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
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
    private Set<String> tags;
    @JoinColumn(name = "utente_id")
    @ManyToOne
    private Utente utente;
    @ManyToMany
    private Set<Categoria> categorie;
    @OneToMany(mappedBy = "articolo")
    private List<Commento> commenti = new ArrayList<>();
    @OneToMany(mappedBy = "articolo")
    private List<Voto> voti = new ArrayList<>();

    public Articolo(String titolo, String contenuto, Set<String> tags, Utente utente, Set<Categoria> categorie) {
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.tags = tags;
        this.utente = utente;
        this.categorie = categorie;
    }
}
