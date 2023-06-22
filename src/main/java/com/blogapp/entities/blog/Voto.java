package com.blogapp.entities.blog;

import com.blogapp.entities.auth.Utente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Utente utente;
    @ManyToMany
    private List<Articolo> articoli;
    @Column(unique = true)
    private Boolean voto;

    public Voto(Utente utente, List<Articolo> articoli, Boolean voto) {
        this.utente = utente;
        this.articoli = articoli;
        this.voto = voto;
    }
}
