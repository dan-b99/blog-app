package com.blogapp.controllers.blog;

import com.blogapp.dtos.blog.AggiuntaArticoloDTO;
import com.blogapp.dtos.blog.ValidazioneDinamicaDTO;
import com.blogapp.dtos.blog.VisualizzaArticoloDTO;
import com.blogapp.services.blog.ArticoloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class ArticoloController {

    private final ArticoloService articoloService;

    @PostMapping("/add")
    public ResponseEntity<Void> aggiungiArticolo(@RequestBody AggiuntaArticoloDTO articolo) {
        articoloService.aggiungi(articolo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<VisualizzaArticoloDTO>> getAll() {
        return new ResponseEntity<>(articoloService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VisualizzaArticoloDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(articoloService.byId(id), HttpStatus.OK);
    }
    @PostMapping("/by-categories")
    public ResponseEntity<List<VisualizzaArticoloDTO>> byCategorie(@RequestBody Long... ids) {
        return new ResponseEntity<>(articoloService.byCategorie(ids), HttpStatus.OK);
    }
    @PostMapping("/by-tags")
    public ResponseEntity<List<VisualizzaArticoloDTO>> byTags(@RequestBody Long... ids) {
        return new ResponseEntity<>(articoloService.byTags(ids), HttpStatus.OK);
    }
    @PutMapping("/custom-validation")
    public ResponseEntity<Void> setValidations(@RequestBody ValidazioneDinamicaDTO validazioneDinamicaDTO) {
        articoloService.setValidazioni(validazioneDinamicaDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
