package com.blogapp.services.auth.impl;

import com.blogapp.dtos.auth.AutenticazioneDTO;
import com.blogapp.dtos.auth.LoginDTO;
import com.blogapp.dtos.auth.RegistrazioneDTO;
import com.blogapp.dtos.auth.UtenteOutputDTO;
import com.blogapp.entities.auth.Utente;
import com.blogapp.repositories.auth.UtenteRepository;
import com.blogapp.services.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class UtenteServiceImpl implements UserService {

    private final UtenteRepository utenteRepository;
    private final ModelMapper modelMapper;

    @Override
    public UtenteOutputDTO registrazione(RegistrazioneDTO registrazioneDTO) {
        Utente utente = modelMapper.map(registrazioneDTO, Utente.class);

        return null;
    }

    @Override
    public AutenticazioneDTO login(LoginDTO loginDTO) {
        return null;
    }
}
