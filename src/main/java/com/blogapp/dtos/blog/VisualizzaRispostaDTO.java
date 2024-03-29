package com.blogapp.dtos.blog;

import com.blogapp.dtos.auth.UtenteCommentoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VisualizzaRispostaDTO {

    private Long id;
    private String testo;
    private UtenteCommentoDTO autore;
    private VisualizzaCommentoDTO padre;
}
