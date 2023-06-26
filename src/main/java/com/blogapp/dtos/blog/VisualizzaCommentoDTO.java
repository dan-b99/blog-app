package com.blogapp.dtos.blog;

import com.blogapp.dtos.auth.UtenteCommentoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisualizzaCommentoDTO {

    private Long id;
    private String testo;
    private UtenteCommentoDTO autore;
}
