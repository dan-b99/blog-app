package com.blogapp.dtos.blog;

import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Il titolo non può essere vuoto")
    private String titolo;
    @NotEmpty(message = "Il contenuto non può essere vuoto")
    private String contenuto;
    private Set<AggiuntaTagDTO> tags = new HashSet<>();
    private Set<Long> categorie;
}
