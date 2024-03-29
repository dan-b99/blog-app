package com.blogapp.util;

import com.blogapp.entities.blog.Validazione;
import com.blogapp.repositories.blog.ValidazioneRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ValidazioniLoader {

    private final ValidazioneRepository validazioneRepository;

    @PostConstruct
    public void load() {
        if(validazioneRepository.count() == 0) {
            Validazione validazione1 = new Validazione("titolo", 1, Integer.MAX_VALUE);
            Validazione validazione2 = new Validazione("contenuto", 1, Integer.MAX_VALUE);
            Validazione validazione3 = new Validazione("password", 8, Integer.MAX_VALUE);
            validazione3.setMaiuscole(false);
            validazione3.setCaratteriSpeciali(false);
            validazione3.setRegexPass(String.format("[a-z0-9]{%d,%d}", validazione3.getMinimo(), validazione3.getMassimo()));
            validazioneRepository.saveAll(List.of(validazione1, validazione2, validazione3));
        }
    }
}
