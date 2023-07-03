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
            validazioneRepository.saveAll(List.of(validazione1, validazione2));
        }
    }
}
