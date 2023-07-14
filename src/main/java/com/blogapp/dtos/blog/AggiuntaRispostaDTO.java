package com.blogapp.dtos.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AggiuntaRispostaDTO {

    private String testo;
    private Long autore;
    private Long articolo;
    private Long padre;
}
