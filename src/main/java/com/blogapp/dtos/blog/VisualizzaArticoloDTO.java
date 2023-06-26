package com.blogapp.dtos.blog;

import com.blogapp.dtos.auth.UtenteOutputDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisualizzaArticoloDTO {

    private Long id;
    private String titolo;
    private String contenuto;
    private Set<VisualizzaTagDTO> tags = new HashSet<>();
    private UtenteOutputDTO utente;
    private Set<VisualizzaCategoriaDTO> categorie;
    private List<VisualizzaCommentoDTO> commenti = new ArrayList<>();
    private int voti;
}
