package com.blogapp.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrazioneDTO {
    @NotEmpty
    private String nome;
    @NotEmpty
    private String cognome;
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    @Email(message = "Email invalida")
    private String email;
}
