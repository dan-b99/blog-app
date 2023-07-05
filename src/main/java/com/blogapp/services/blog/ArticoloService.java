package com.blogapp.services.blog;

import com.blogapp.dtos.blog.AggiuntaArticoloDTO;
import com.blogapp.dtos.blog.ValidazioneDinamicaDTO;
import com.blogapp.dtos.blog.VisualizzaArticoloDTO;
import java.util.List;

public interface ArticoloService {

    void aggiungi(AggiuntaArticoloDTO articolo);
    List<VisualizzaArticoloDTO> getAll();
    VisualizzaArticoloDTO byId(Long id);
    List<VisualizzaArticoloDTO> byCategorie(Long... ids);
    List<VisualizzaArticoloDTO> byTags(Long... ids);
    void setValidazioni(ValidazioneDinamicaDTO validazioneDinamicaDTO);
}
