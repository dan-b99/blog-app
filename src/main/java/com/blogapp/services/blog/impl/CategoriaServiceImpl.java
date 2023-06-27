package com.blogapp.services.blog.impl;

import com.blogapp.dtos.blog.AggiuntaCategoriaDTO;
import com.blogapp.dtos.blog.VisualizzaCategoriaDTO;
import com.blogapp.entities.blog.Categoria;
import com.blogapp.repositories.blog.CategoriaRepository;
import com.blogapp.services.blog.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;
    @Override
    public void addCategoria(AggiuntaCategoriaDTO categoria) {
        if(categoriaRepository.findByNome(categoria.getNome()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria già presente");
        }
        categoriaRepository.save(modelMapper.map(categoria, Categoria.class));
    }
    @Override
    public List<VisualizzaCategoriaDTO> getAll() {
        return categoriaRepository.findAll().stream()
                .map(c -> modelMapper.map(c, VisualizzaCategoriaDTO.class))
                .toList();
    }
}
