package com.blogapp.services.blog.impl;

import com.blogapp.dtos.blog.VisualizzaVotoDTO;
import com.blogapp.repositories.blog.ArticoloRepository;
import com.blogapp.repositories.blog.VotoRepository;
import com.blogapp.services.blog.VotoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VotoServiceImpl implements VotoService {

    private final VotoRepository votoRepository;
    private final ArticoloRepository articoloRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<VisualizzaVotoDTO> getLikesByArticoloId(Long articoloId) {
        if(articoloRepository.findById(articoloId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non trovato");
        }
        return votoRepository.getLikesByArticoloId(articoloId).stream()
                .map(voto -> modelMapper.map(voto, VisualizzaVotoDTO.class))
                .toList();
    }

    @Override
    public List<VisualizzaVotoDTO> getDislikesByArticoloid(Long articoloId) {
        if(articoloRepository.findById(articoloId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non trovato");
        }
        return votoRepository.getDislikesByArticoloId(articoloId).stream()
                .map(voto -> modelMapper.map(voto, VisualizzaVotoDTO.class))
                .toList();
    }
}
