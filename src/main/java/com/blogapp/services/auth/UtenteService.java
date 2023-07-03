package com.blogapp.services.auth;

import com.blogapp.dtos.auth.*;

import java.util.Set;

public interface UtenteService {
    UtenteOutputDTO registrazione(RegistrazioneDTO registrazioneDTO);
    AutenticazioneDTO login(LoginDTO loginDTO);
    Set<RuoloOutputDTO> userRoles();
}
