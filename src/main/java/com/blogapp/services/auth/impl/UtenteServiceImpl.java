package com.blogapp.services.auth.impl;

import com.blogapp.dtos.auth.*;
import com.blogapp.entities.auth.Ruolo;
import com.blogapp.entities.auth.Utente;
import com.blogapp.entities.blog.Validazione;
import com.blogapp.repositories.auth.RuoloRepository;
import com.blogapp.repositories.auth.UtenteRepository;
import com.blogapp.repositories.blog.ValidazioneRepository;
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
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository utenteRepository;
    private final RuoloRepository ruoloRepository;
    private final ValidazioneRepository validazioneRepository;
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
        if(utente.isBloccato()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sei stato bloccato");
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
    public Set<UtenteOutputDTO> getAll() {
        return utenteRepository.getAllExceptAdmin().stream()
                .map(u -> modelMapper.map(u, UtenteOutputDTO.class))
                .collect(Collectors.toCollection(LinkedHashSet::new));
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

    @Override
    public void setValidazionePassword(ValidazioneDinamicaPasswordDTO validazioniPassword) {
        if(validazioniPassword.getMinimo() > validazioniPassword.getMassimo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valori errati");
        }
        if(validazioniPassword.getCaratteriSpeciali() && validazioniPassword.getMaiuscole()) {
            validazioniPassword.setRegexPass(
                    String.format("[A-Za-z0-9\\p{Punct}]{%d,%d}", validazioniPassword.getMinimo(), validazioniPassword.getMassimo())
            );
        }
        else if(!validazioniPassword.getMaiuscole() && !validazioniPassword.getCaratteriSpeciali()) {
            validazioniPassword.setRegexPass(
                    String.format("[a-z0-9]{%d,%d}", validazioniPassword.getMinimo(), validazioniPassword.getMassimo())
            );
        }
        else if(validazioniPassword.getMaiuscole() && !validazioniPassword.getCaratteriSpeciali()) {
            validazioniPassword.setRegexPass(
                    String.format("[a-zA-Z0-9]{%d,%d}", validazioniPassword.getMinimo(), validazioniPassword.getMassimo())
            );
        }
        else {
            validazioniPassword.setRegexPass(
                    String.format("[a-z0-9\\p{Punct}]{%d,%d}", validazioniPassword.getMinimo(), validazioniPassword.getMassimo())
            );
        }
        Validazione validazione = validazioneRepository.findByCampo(validazioniPassword.getCampo()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Campo non trovato")
        );
        validazione.setMinimo(validazioniPassword.getMinimo());
        validazione.setMassimo(validazioniPassword.getMassimo());
        validazione.setCaratteriSpeciali(validazioniPassword.getCaratteriSpeciali());
        validazione.setMaiuscole(validazioniPassword.getMaiuscole());
        validazione.setRegexPass(validazioniPassword.getRegexPass());
        validazioneRepository.save(validazione);
    }

    @Override
    public void setBlock(Long id) {
        if(utenteRepository.findById(id).isPresent()) {
            Utente utente = utenteRepository.findById(id).get();
            if(!utente.isBloccato()) {
                utente.setBloccato(true);
            }
            else {
                utente.setBloccato(false);
            }
            utenteRepository.save(utente);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato");
        }
    }

    @Override
    public void deleteUser(Long id) {
        if(utenteRepository.findById(id).isPresent()) {
            utenteRepository.deleteById(id);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato");
        }
    }
}
