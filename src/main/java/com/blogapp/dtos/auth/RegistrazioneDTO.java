package com.blogapp.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrazioneDTO {

    private String nome;
    private String cognome;
    private String username;
    private String password;
    private String email;
}
