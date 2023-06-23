package com.blogapp.entities.blog;

import com.blogapp.entities.auth.Utente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    public Commento(String testo, Utente autore, Articolo articolo) {
        this.testo = testo;
        this.autore = autore;
        this.articolo = articolo;
    }
}
