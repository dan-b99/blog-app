package com.blogapp.services.auth;

import com.blogapp.dtos.auth.AutenticazioneDTO;
import com.blogapp.dtos.auth.LoginDTO;
import com.blogapp.dtos.auth.RegistrazioneDTO;
import com.blogapp.dtos.auth.UtenteOutputDTO;

public interface UserService {
    UtenteOutputDTO registrazione(RegistrazioneDTO registrazioneDTO);
    AutenticazioneDTO login(LoginDTO loginDTO);
}
