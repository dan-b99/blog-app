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
    @GetMapping("/all")
    public ResponseEntity<Set<UtenteOutputDTO>> getAll() {
        return new ResponseEntity<>(utenteService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/user-roles")
    public ResponseEntity<Set<RuoloOutputDTO>> ruoliUtente() {
        return new ResponseEntity<>(utenteService.userRoles(), HttpStatus.OK);
    }
    @PutMapping("/custom-password-validation")
    public ResponseEntity<Void> setPasswordValidation(@RequestBody ValidazioneDinamicaPasswordDTO validazioneDinamicaPasswordDTO) {
        utenteService.setValidazionePassword(validazioneDinamicaPasswordDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping("/update-password/{id}")
    public ResponseEntity<UtenteOutputDTO> passUpdate(@PathVariable Long id, @RequestBody String newPassword) {
        return new ResponseEntity<>(utenteService.updatePassword(id, newPassword), HttpStatus.OK);
    }
    @PutMapping("/set-block")
    public ResponseEntity<Void> setBlock(@RequestBody Long id) {
        utenteService.setBlock(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        utenteService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping("/set-notifications")
    public ResponseEntity<UtenteOutputDTO> setNotifications(@RequestBody Long id) {
        return new ResponseEntity<>(utenteService.setNotifiche(id), HttpStatus.OK);
    }
}
