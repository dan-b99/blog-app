package com.blogapp.dtos.blog;

import com.blogapp.dtos.auth.UtenteOutputDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VisualizzaVotoDTO {

    private Long id;
    private UtenteOutputDTO utente;
    private Boolean voto;
}
