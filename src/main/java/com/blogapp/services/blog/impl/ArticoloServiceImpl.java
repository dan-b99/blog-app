package com.blogapp.services.blog.impl;

import com.blogapp.dtos.blog.*;
import com.blogapp.entities.blog.Articolo;
import com.blogapp.entities.blog.Tag;
import com.blogapp.entities.blog.Validazione;
import com.blogapp.entities.blog.Voto;
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
import java.util.stream.Collectors;

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
        Set<Tag> tagDaAggiungere = articolo.getTags().stream().map(nt -> modelMapper.map(nt, Tag.class)).collect(Collectors.toSet());
        tagDaAggiungere.forEach(t -> t.setArticoli(List.of(nuovoArticolo)));
        nuovoArticolo.setTags(tagDaAggiungere);
        articoloRepository.save(nuovoArticolo);
    }

    @Override
    public List<VisualizzaArticoloDTO> getAllNotApprovati() {
        return articoloRepository.findAllNotApprovati().stream()
                .map(art -> modelMapper.map(art, VisualizzaArticoloDTO.class))
                .toList();
    }

    @Override
    public List<VisualizzaArticoloDTO> getAllApprovati() {
        return articoloRepository.findAllApprovati().stream()
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
    public List<VisualizzaArticoloDTO> byTags(String... tags) {
        List<VisualizzaArticoloDTO> risultato = articoloRepository.findByTags(tags).stream()
                .map(art -> modelMapper.map(art, VisualizzaArticoloDTO.class))
                .toList();
        if(!risultato.isEmpty()) {
            return risultato;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun risultato");
    }

    @Override
    public List<VisualizzaArticoloDTO> byContenutoOrTitolo(String keyword) {
        List<VisualizzaArticoloDTO> lista = articoloRepository.findByContenutoOrTitolo(keyword).stream()
                .map(art -> modelMapper.map(art, VisualizzaArticoloDTO.class))
                .toList();
        if(!lista.isEmpty()) {
            return lista;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun risultato");
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
    public void setVote(AggiuntaVotoDTO voto) {
        if(votoRepository.findByUtenteIdAndArticoloId(voto.getUtente(), voto.getArticolo()).isPresent()) {
            Voto votoPresente = votoRepository.findByUtenteIdAndArticoloId(voto.getUtente(), voto.getArticolo()).get();
            if(voto.getVoto() != votoPresente.getVoto()) {
                votoPresente.setVoto(voto.getVoto());
                votoRepository.save(votoPresente);
            }
            else if(voto.getVoto() == votoPresente.getVoto()) {
                votoRepository.delete(votoPresente);
            }
        }
        else {
            Voto votoDaAggiungere = modelMapper.map(voto, Voto.class);
            votoDaAggiungere.setUtente(utenteRepository.findById(voto.getUtente())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato")));
            votoDaAggiungere.setArticolo(articoloRepository.findById(voto.getArticolo())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non trovato")));
            votoRepository.save(votoDaAggiungere);
        }
    }
}
