package com.blogapp.services.auth.impl;

import com.blogapp.dtos.auth.AutenticazioneDTO;
import com.blogapp.dtos.auth.LoginDTO;
import com.blogapp.dtos.auth.RegistrazioneDTO;
import com.blogapp.dtos.auth.UtenteOutputDTO;
import com.blogapp.entities.auth.Ruolo;
import com.blogapp.entities.auth.Utente;
import com.blogapp.repositories.auth.RuoloRepository;
import com.blogapp.repositories.auth.UtenteRepository;
import com.blogapp.services.auth.UserService;
import com.blogapp.util.JWTUtil;
import com.blogapp.util.PasswordUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UtenteServiceImpl implements UserService {

    private final UtenteRepository utenteRepository;
    private final RuoloRepository ruoloRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final PasswordUtil passwordUtil;
    private final JWTUtil jwtUtil;

    @Override
    public UtenteOutputDTO registrazione(RegistrazioneDTO registrazioneDTO) {
        Utente utente = modelMapper.map(registrazioneDTO, Utente.class);
        utente.setPassword(passwordUtil.crypt(registrazioneDTO.getPassword()));
        utente.setRuoli(Set.of(
                ruoloRepository.findByAuthority("ROLE_USER").orElseGet(
                        () ->  ruoloRepository.save(new Ruolo("ROLE_USER")))
        ));
        return modelMapper.map(utenteRepository.save(utente), UtenteOutputDTO.class);
    }

    @Override
    public AutenticazioneDTO login(LoginDTO loginDTO) {
        Utente utente = utenteRepository.findByEmail(loginDTO.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
        if(!passwordUtil.isMatch(loginDTO.getPassword(), utente.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email o password errati");
        }
        try {
            UtenteOutputDTO utenteOutput = modelMapper.map(utente, UtenteOutputDTO.class);
            Map<String, String> claimsPrivati = Map.of("user", objectMapper.writeValueAsString(utenteOutput));
            return new AutenticazioneDTO(jwtUtil.generate(utenteOutput.getEmail(), claimsPrivati), utenteOutput);
        } catch(Exception ex) {
           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore del server");
        }
    }
}
