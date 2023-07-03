package com.blogapp.controllers.auth;

import com.blogapp.dtos.auth.*;
import com.blogapp.services.auth.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UtenteController {

    private final UtenteService utenteService;

    @PostMapping("/signIn")
    public ResponseEntity<UtenteOutputDTO> registrazione(@RequestBody RegistrazioneDTO registrazioneDTO) {
        return new ResponseEntity<>(utenteService.registrazione(registrazioneDTO), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<AutenticazioneDTO> accesso(@RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(utenteService.login(loginDTO), HttpStatus.OK);
    }
    @GetMapping("/user-roles")
    public ResponseEntity<Set<RuoloOutputDTO>> ruoliUtente() {
        return new ResponseEntity<>(utenteService.userRoles(), HttpStatus.OK);
    }
}
