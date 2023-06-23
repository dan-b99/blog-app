package com.blogapp.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutenticazioneDTO {

    private String jwt;
    private UtenteOutputDTO utenteOutput;
}
