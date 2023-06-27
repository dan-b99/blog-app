package com.blogapp.services.blog;

import com.blogapp.dtos.blog.AggiuntaArticoloDTO;
import com.blogapp.dtos.blog.VisualizzaArticoloDTO;
import java.util.List;

public interface ArticoloService {

    void aggiungi(AggiuntaArticoloDTO articolo);
    List<VisualizzaArticoloDTO> getAll();
    List<VisualizzaArticoloDTO> byCategorie(Long... ids);
    List<VisualizzaArticoloDTO> byTags(Long... ids);
}
