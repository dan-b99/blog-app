package com.blogapp.services.blog.impl;

import com.blogapp.dtos.blog.AggiuntaCommentoDTO;
import com.blogapp.dtos.blog.AggiuntaRispostaDTO;
import com.blogapp.dtos.blog.VisualizzaCommentoDTO;
import com.blogapp.dtos.blog.VisualizzaRispostaDTO;
import com.blogapp.entities.auth.Utente;
import com.blogapp.entities.blog.Articolo;
import com.blogapp.entities.blog.Commento;
import com.blogapp.repositories.auth.UtenteRepository;
import com.blogapp.repositories.blog.ArticoloRepository;
import com.blogapp.repositories.blog.CommentoRepository;
import com.blogapp.services.blog.CommentoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentoServiceImpl implements CommentoService {

    private final CommentoRepository commentoRepository;
    private final ArticoloRepository articoloRepository;
    private final UtenteRepository utenteRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addComment(AggiuntaCommentoDTO comment) {
        if(articoloRepository.findById(comment.getArticolo()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID articolo errato");
        }
        if((utenteRepository.findById(comment.getAutore()).isEmpty())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID utente errato");
        }
        Articolo articolo = articoloRepository.findById(comment.getArticolo()).get();
        Utente utente = utenteRepository.findById(comment.getAutore()).get();
        Commento commentoDaAggiungere = modelMapper.map(comment, Commento.class);
        commentoDaAggiungere.setArticolo(articolo);
        commentoDaAggiungere.setAutore(utente);
        articolo.getCommenti().add(commentoDaAggiungere);
        utente.getCommenti().add(commentoDaAggiungere);
        commentoRepository.save(commentoDaAggiungere);
        utenteRepository.save(utente);
        articoloRepository.save(articolo);
    }

    //TUTTI i commenti padre
    @Override
    public List<VisualizzaCommentoDTO> getCommentsByArtId(Long id) {
        if(articoloRepository.findById(id).isPresent()) {
            return commentoRepository.byArticoloId(id).stream()
                    .map(comm -> modelMapper.map(comm, VisualizzaCommentoDTO.class))
                    .toList();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non trovato");
    }

    @Override
    public void addReply(AggiuntaRispostaDTO reply) {
        if(utenteRepository.findById(reply.getAutore()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente inesistente");
        }
        if(articoloRepository.findById(reply.getArticolo()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo inesistente");
        }
        if(commentoRepository.findById(reply.getPadre()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Commento padre inesistente");
        }
        Utente autore = utenteRepository.findById(reply.getAutore()).get();
        Articolo articolo = articoloRepository.findById(reply.getArticolo()).get();
        Commento commentoPadre = commentoRepository.findById(reply.getPadre()).get();
        int idxPadre = articolo.getCommenti().indexOf(commentoPadre);
        Commento risposta = modelMapper.map(reply, Commento.class);
        risposta.setAutore(autore);
        risposta.setArticolo(articolo);
        risposta.setPadre(commentoPadre);
        autore.getCommenti().add(risposta);
        commentoPadre.getRisposte().add(risposta);
        articolo.getCommenti().set(idxPadre, commentoPadre);
        commentoRepository.save(risposta);
        commentoRepository.save(commentoPadre);
        utenteRepository.save(autore);
        articoloRepository.save(articolo);
    }

    @Override
    public List<VisualizzaRispostaDTO> getRepliesByArtIdAndCommId(Long artId, Long commId) {
        if(articoloRepository.findById(artId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non trovato");
        }
        if(commentoRepository.findById(commId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Commento non trovato");
        }
        List<Commento> replies = commentoRepository.repliesByArticoloAndId(artId, commId);
        if(!replies.isEmpty()) {
            int idx = 0;
            Long currentId = commId;
            while(!commentoRepository.repliesByArticoloAndId(artId, currentId).isEmpty()) {
                currentId = replies.get(idx++).getId();
                replies.addAll(commentoRepository.repliesByArticoloAndId(artId, currentId));
            }
        }
        return replies.stream().map(risps -> modelMapper.map(risps, VisualizzaRispostaDTO.class)).toList();
    }
}
