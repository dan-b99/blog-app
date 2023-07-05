package com.blogapp.dtos.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AggiuntaVotoDTO {

    private Long utente;
    private Long articolo;
    private Boolean voto;
}
