package com.blogapp.entities.blog;

import com.blogapp.entities.auth.Utente;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Transactional
@DynamicUpdate
@Entity
public class Commento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String testo;
    @JoinColumn(name = "autore_id")
    @ManyToOne
    private Utente autore;
    @JoinColumn(name = "articolo_id")
    @ManyToOne
    private Articolo articolo;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Commento> risposte = new ArrayList<>();

    public Commento(String testo, Utente autore, Articolo articolo) {
        this.testo = testo;
        this.autore = autore;
        this.articolo = articolo;
    }
}
