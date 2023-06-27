package com.blogapp.services.blog;

import com.blogapp.dtos.blog.AggiuntaCategoriaDTO;
import com.blogapp.dtos.blog.VisualizzaCategoriaDTO;
import java.util.List;

public interface CategoriaService {
    void addCategoria(AggiuntaCategoriaDTO categoria);
    List<VisualizzaCategoriaDTO> getAll();
}
