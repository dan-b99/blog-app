package com.blogapp.dtos.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValidazioneDinamicaBlogDTO {

    private String campo;
    private int minimo;
    private int massimo;
}
