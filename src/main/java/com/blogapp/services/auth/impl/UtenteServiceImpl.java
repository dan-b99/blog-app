package com.blogapp.services.auth.impl;

import com.blogapp.dtos.auth.*;
import com.blogapp.entities.auth.Ruolo;
import com.blogapp.entities.auth.Utente;
import com.blogapp.repositories.auth.RuoloRepository;
import com.blogapp.repositories.auth.UtenteRepository;
import com.blogapp.services.auth.UtenteService;
import com.blogapp.util.JWTUtil;
import com.blogapp.util.PasswordUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository utenteRepository;
    private final RuoloRepository ruoloRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final PasswordUtil passwordUtil;
    private final JWTUtil jwtUtil;

    @Override
    public UtenteOutputDTO registrazione(RegistrazioneDTO registrazioneDTO) {
        utenteRepository.findByEmail(registrazioneDTO.getEmail()).ifPresent(u -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email già registrata");
        });
        utenteRepository.findByUsername(registrazioneDTO.getUsername()).ifPresent(u -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username già in uso");
        });
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

    @Override
    public Set<RuoloOutputDTO> userRoles() {
        Set<Ruolo> ruoli = utenteRepository.findRolesByEmail(
                (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        );
        return ruoli.stream()
                .map(r -> modelMapper.map(r, RuoloOutputDTO.class))
                .collect(Collectors.toSet());
    }
}
