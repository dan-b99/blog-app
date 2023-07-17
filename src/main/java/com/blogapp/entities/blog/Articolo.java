package com.blogapp.entities.blog;

import com.blogapp.entities.auth.Utente;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Transactional(rollbackOn = {SQLException.class, RuntimeException.class})
@DynamicUpdate
@Entity
public class Articolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String titolo;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String contenuto;
    @Column(nullable = false)
    @ManyToMany(mappedBy = "articoli", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Tag> tags = new HashSet<>();
    @JoinColumn(name = "utente_id")
    @ManyToOne
    private Utente utente;
    @ManyToMany
    private Set<Categoria> categorie;
    @OneToMany(mappedBy = "articolo", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<Commento> commenti = new ArrayList<>();
    @OneToMany(mappedBy = "articolo", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<Voto> voti = new ArrayList<>();
    private boolean approvato;

    public Articolo(String titolo, String contenuto, Utente utente, Set<Categoria> categorie) {
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.utente = utente;
        this.categorie = categorie;
    }
}
