package com.blogapp.entities.auth;

import com.blogapp.entities.blog.Articolo;
import com.blogapp.entities.blog.Commento;
import com.blogapp.entities.blog.Voto;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Transactional(rollbackOn = {SQLException.class, RuntimeException.class})
@DynamicUpdate
@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Ruolo> ruoli;
    @OneToMany(mappedBy = "utente", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<Articolo> articoli = new ArrayList<>();
    @OneToMany(mappedBy = "autore", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<Commento> commenti = new ArrayList<>();
    @OneToMany(mappedBy = "utente", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<Voto> votazioni = new ArrayList<>();
    private boolean bloccato;
    private boolean regexMatch;
    private boolean iscritto;

    public Utente(String username, String password, Set<Ruolo> ruoli) {
        this.username = username;
        this.password = password;
        this.ruoli = ruoli;
    }
}
