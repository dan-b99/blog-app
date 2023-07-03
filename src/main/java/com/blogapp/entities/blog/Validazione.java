package com.blogapp.entities.blog;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor
@Getter
@Setter
@Transactional
@DynamicUpdate
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UN_validazione", columnNames = {"campo", "minimo", "massimo"})
})
public class Validazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String campo;
    @Column(nullable = false)
    private int minimo;
    @Column(nullable = false)
    private int massimo;

    public Validazione(String campo, int minimo, int massimo) {
        this.campo = campo;
        this.minimo = minimo;
        this.massimo = massimo;
    }
}
