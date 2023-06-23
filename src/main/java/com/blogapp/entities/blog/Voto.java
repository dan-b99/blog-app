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
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UN_voto", columnNames = {"utente_id", "articolo_id"})
})
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "utente_id", unique = true)
    @ManyToOne
    private Utente utente;
    @JoinColumn(name = "articolo_id", unique = true)
    @ManyToOne
    private Articolo articolo;
    @Column(unique = true)
    private Boolean voto;

    public Voto(Utente utente, Articolo articolo, Boolean voto) {
        this.utente = utente;
        this.articolo = articolo;
        this.voto = voto;
    }
}
