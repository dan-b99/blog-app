package com.blogapp.entities.blog;

import com.blogapp.entities.auth.Utente;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.SQLException;

@Getter
@Setter
@NoArgsConstructor
@Transactional(rollbackOn = {SQLException.class, RuntimeException.class})
@DynamicUpdate
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UN_voto", columnNames = {"utente_id", "articolo_id"})
})
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "utente_id")
    @ManyToOne
    private Utente utente;
    @JoinColumn(name = "articolo_id")
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
