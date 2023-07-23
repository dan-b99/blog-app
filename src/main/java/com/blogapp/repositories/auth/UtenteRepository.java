package com.blogapp.repositories.auth;

import com.blogapp.entities.auth.Ruolo;
import com.blogapp.entities.auth.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);
    Optional<Utente> findByUsername(String username);
    @Query("SELECT u.ruoli FROM Utente u WHERE u.email = :email")
    Set<Ruolo> findRolesByEmail(@Param(value = "email") String email);
    @Query(value = """
            SELECT u From Utente u JOIN u.ruoli r GROUP BY u HAVING COUNT(r) <= 1
            """)
    Set<Utente> getAllExceptAdmin();
    @Query(value = "SELECT u FROM Utente u WHERE u.iscritto = true")
    Set<Utente> getAllByIscritto();
}
