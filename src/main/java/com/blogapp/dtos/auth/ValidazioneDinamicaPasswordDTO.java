package com.blogapp.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidazioneDinamicaPasswordDTO {

    private String campo;
    private int minimo;
    private int massimo;
    private Boolean caratteriSpeciali;
    private Boolean maiuscole;
    private String regexPass;
}
