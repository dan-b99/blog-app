package com.blogapp.dtos.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AggiuntaArticoloDTO {

    private String titolo;
    private String contenuto;
    private Long utente;
    private Set<AggiuntaTagDTO> tags = new HashSet<>();
    private Set<AggiuntaCategoriaDTO> categorie;
}
