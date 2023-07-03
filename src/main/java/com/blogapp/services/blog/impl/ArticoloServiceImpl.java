package com.blogapp.services.blog.impl;

import com.blogapp.dtos.blog.AggiuntaArticoloDTO;
import com.blogapp.dtos.blog.ValidazioneDinamicaDTO;
import com.blogapp.dtos.blog.VisualizzaArticoloDTO;
import com.blogapp.entities.blog.Articolo;
import com.blogapp.entities.blog.Tag;
import com.blogapp.entities.blog.Validazione;
import com.blogapp.repositories.auth.UtenteRepository;
import com.blogapp.repositories.blog.ArticoloRepository;
import com.blogapp.repositories.blog.CategoriaRepository;
import com.blogapp.repositories.blog.TagRepository;
import com.blogapp.repositories.blog.ValidazioneRepository;
import com.blogapp.services.blog.ArticoloService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticoloServiceImpl implements ArticoloService {

    private final ArticoloRepository articoloRepository;
    private final TagRepository tagRepository;
    private final CategoriaRepository categoriaRepository;
    private final UtenteRepository utenteRepository;
    private final ValidazioneRepository validazioneRepository;
    private final ModelMapper modelMapper;
    private String regex = "<img[^>]*>";

    @Override
    public void aggiungi(AggiuntaArticoloDTO articolo) {
        Set<Tag> tagsArticolo = articolo.getTags().stream().map(t -> modelMapper.map(t, Tag.class)).collect(Collectors.toSet());
        for(Tag tag : tagsArticolo) {
            tagRepository.findByNome(tag.getNome()).orElseGet(() -> tagRepository.save(tag));
        }
        Articolo nuovoArticolo = modelMapper.map(articolo, Articolo.class);
        Validazione validazioneTitolo = validazioneRepository.findByCampo("titolo").get();
        Validazione validazioneCont = validazioneRepository.findByCampo("contenuto").get();
        if((articolo.getTitolo().length() > validazioneTitolo.getMassimo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il titolo è troppo lungo");
        }
        else if(articolo.getTitolo().length() < validazioneTitolo.getMinimo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il titolo è troppo corto");
        }
        if((articolo.getContenuto().replaceAll(regex, "").length()) > validazioneCont.getMassimo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il contenuto è troppo lungo");
        }
        else if((articolo.getContenuto().replaceAll(regex, "").length()) < validazioneCont.getMinimo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il contenuto è troppo corto");
        }
        nuovoArticolo.setUtente(utenteRepository.findByEmail(
                (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).get()
        );
        nuovoArticolo.setCategorie(new HashSet<>(categoriaRepository.findAllById(articolo.getCategorie())));
        articoloRepository.save(nuovoArticolo);
    }
    @Override
    public List<VisualizzaArticoloDTO> getAll() {
        return articoloRepository.findAll().stream()
                .map(a -> modelMapper.map(a, VisualizzaArticoloDTO.class))
                .toList();
    }
    @Override
    public List<VisualizzaArticoloDTO> byCategorie(Long... ids) {
        return articoloRepository.findByCategorie(ids).stream()
                .map(art -> modelMapper.map(art, VisualizzaArticoloDTO.class))
                .toList();
    }
    @Override
    public List<VisualizzaArticoloDTO> byTags(Long... ids) {
        return articoloRepository.findByTags(ids).stream()
                .map(art -> modelMapper.map(art, VisualizzaArticoloDTO.class))
                .toList();
    }
    @Override
    public void setValidazioni(ValidazioneDinamicaDTO validazioneDinamica) {
        Validazione validazione = validazioneRepository.findByCampo(validazioneDinamica.getCampo()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Validazione non trovata")
        );
        validazione.setMinimo(validazioneDinamica.getMinimo());
        validazione.setMassimo(validazioneDinamica.getMassimo());
        validazioneRepository.save(validazione);
    }
}
