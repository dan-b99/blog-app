package com.blogapp.entities.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Ruolo implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String authority;
    @ManyToMany(mappedBy = "ruoli")
    private Set<Utente> utenti = new HashSet<>();

    public Ruolo(String authority) {
        this.authority = authority;
    }
}
