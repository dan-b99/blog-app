package com.blogapp.services.blog.impl;

import com.blogapp.dtos.blog.AggiuntaArticoloDTO;
import com.blogapp.dtos.blog.VisualizzaArticoloDTO;
import com.blogapp.entities.blog.Articolo;
import com.blogapp.repositories.blog.ArticoloRepository;
import com.blogapp.repositories.blog.CategoriaRepository;
import com.blogapp.repositories.blog.TagRepository;
import com.blogapp.services.blog.ArticoloService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticoloServiceImpl implements ArticoloService {
    private ArticoloRepository articoloRepository;
    private CategoriaRepository categoriaRepository;
    private TagRepository tagRepository;
    private ModelMapper modelMapper;

    @Override
    public void aggiungi(AggiuntaArticoloDTO articolo) {
        articoloRepository.save(modelMapper.map(articolo, Articolo.class));
    }

    @Override
    public List<VisualizzaArticoloDTO> byCategorie(Long... ids) {
        return articoloRepository.findByCategorie(ids).stream()
                .filter(Articolo::isApprovato)
                .map(art -> modelMapper.map(art, VisualizzaArticoloDTO.class))
                .toList();
    }

    @Override
    public List<VisualizzaArticoloDTO> byTags(Long... ids) {
        return articoloRepository.findByTags(ids).stream()
                .filter(Articolo::isApprovato)
                .map(art -> modelMapper.map(art, VisualizzaArticoloDTO.class))
                .toList();
    }
}
