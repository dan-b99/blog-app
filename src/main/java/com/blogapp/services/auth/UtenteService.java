package com.blogapp.services.auth;

import com.blogapp.dtos.auth.*;

import java.util.Set;

public interface UtenteService {
    UtenteOutputDTO registrazione(RegistrazioneDTO registrazioneDTO);
    AutenticazioneDTO login(LoginDTO loginDTO);
    Set<UtenteOutputDTO> getAll();
    Set<RuoloOutputDTO> userRoles();
    void setValidazionePassword(ValidazioneDinamicaPasswordDTO validazioniPassword);
    UtenteOutputDTO updatePassword(Long utenteId, String newPassword);
    void setBlock(Long id);
    UtenteOutputDTO setNotifiche(Long id);
    void deleteUser(Long id);
}
