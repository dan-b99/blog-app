package com.blogapp.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UtenteOutputDTO {

    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private List<RuoloOutputDTO> ruoli;
    private boolean bloccato;
}
