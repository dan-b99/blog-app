package com.blogapp.services.blog.impl;

import com.blogapp.dtos.blog.*;
import com.blogapp.entities.blog.Articolo;
import com.blogapp.entities.blog.Tag;
import com.blogapp.entities.blog.Validazione;
import com.blogapp.repositories.auth.UtenteRepository;
import com.blogapp.repositories.blog.*;
import com.blogapp.services.blog.ArticoloService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ArticoloServiceImpl implements ArticoloService {

    private final ArticoloRepository articoloRepository;
    private final TagRepository tagRepository;
    private final CategoriaRepository categoriaRepository;
    private final VotoRepository votoRepository;
    private final UtenteRepository utenteRepository;
    private final ValidazioneRepository validazioneRepository;
    private final ModelMapper modelMapper;
    private String regex = "<[^>]*>";

    @Override
    public void aggiungi(AggiuntaArticoloDTO articolo) {
        Articolo nuovoArticolo = modelMapper.map(articolo, Articolo.class);
        Validazione validazioneTitolo = validazioneRepository.findByCampo("titolo").get();
        Validazione validazioneCont = validazioneRepository.findByCampo("contenuto").get();
        if((articolo.getTitolo().replaceAll(regex, "").length() > validazioneTitolo.getMassimo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il titolo è troppo lungo");
        }
        else if(articolo.getTitolo().replaceAll(regex, "").length() < validazioneTitolo.getMinimo()) {
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
        nuovoArticolo.setCategorie(new LinkedHashSet<>(categoriaRepository.findAllById(articolo.getCategorie())));
        articoloRepository.save(nuovoArticolo);
        if(!articolo.getTags().isEmpty()) {
            Set<Tag> tagsToAdd = new HashSet<>();
            for(AggiuntaTagDTO addTag : articolo.getTags()) {
                if(tagRepository.findByNome(addTag.getNome()).isPresent()) {
                    Tag presTag = tagRepository.findByNome(addTag.getNome()).get();
                    List<Articolo> articoliAssociati = presTag.getArticoli();
                    articoliAssociati.add(articoloRepository.findById(nuovoArticolo.getId()).get());
                    tagsToAdd.add(tagRepository.save(presTag));
                }
                else {
                    Tag notPres = modelMapper.map(addTag, Tag.class);
                    notPres.setArticoli(List.of(articoloRepository.findById(nuovoArticolo.getId()).get()));
                    tagsToAdd.add(tagRepository.save(notPres));
                }
            }
            nuovoArticolo.setTags(new HashSet<>(tagRepository.findAllById(tagsToAdd.stream().map(Tag::getId).toList())));
            articoloRepository.save(nuovoArticolo);
        }
    }
    @Override
    public List<VisualizzaArticoloDTO> getAll() {
        return articoloRepository.findAll().stream()
                .map(a -> modelMapper.map(a, VisualizzaArticoloDTO.class))
                .toList();
    }

    @Override
    public VisualizzaArticoloDTO byId(Long id) {
        return modelMapper.map(articoloRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "ID errato")), VisualizzaArticoloDTO.class);
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
    public void setValidazioniArticolo(ValidazioneDinamicaBlogDTO validazioneDinamica) {
        if(validazioneDinamica.getMinimo() > validazioneDinamica.getMassimo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valori errati");
        }
        Validazione validazione = validazioneRepository.findByCampo(validazioneDinamica.getCampo()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Validazione non trovata")
        );
        validazione.setMinimo(validazioneDinamica.getMinimo());
        validazione.setMassimo(validazioneDinamica.getMassimo());
        validazioneRepository.save(validazione);
    }
    @Override
    public void setLike(AggiuntaVotoDTO voto) {

    }
}
