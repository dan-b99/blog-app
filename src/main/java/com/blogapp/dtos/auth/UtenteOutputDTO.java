package com.blogapp.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UtenteOutputDTO {

    private Long id;
    private String nome;
    private String cognome;
    private String email;
}