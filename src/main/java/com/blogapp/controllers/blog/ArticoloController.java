package com.blogapp.controllers.blog;

import com.blogapp.dtos.blog.*;
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
    @GetMapping("/not-approved")
    public ResponseEntity<List<VisualizzaArticoloDTO>> getNotApproved() {
        return new ResponseEntity<>(articoloService.getAllNotApprovati(), HttpStatus.OK);
    }
    @GetMapping("/all-approved")
    public ResponseEntity<List<VisualizzaArticoloDTO>> getAll() {
        return new ResponseEntity<>(articoloService.getAllApprovati(), HttpStatus.OK);
    }
    @GetMapping("/not-approved/{id}")
    public ResponseEntity<VisualizzaArticoloDTO> readNotApproved(@PathVariable Long id) {
        return new ResponseEntity<>(articoloService.notApprovedById(id), HttpStatus.OK);
    }
    @GetMapping("/approved/{id}")
    public ResponseEntity<VisualizzaArticoloDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(articoloService.approvedById(id), HttpStatus.OK);
    }
    @PostMapping("/by-categories")
    public ResponseEntity<List<VisualizzaArticoloDTO>> byCategorie(@RequestBody Long... ids) {
        return new ResponseEntity<>(articoloService.byCategorie(ids), HttpStatus.OK);
    }
    @PostMapping("/by-tags")
    public ResponseEntity<List<VisualizzaArticoloDTO>> byTags(@RequestBody String... tags) {
        return new ResponseEntity<>(articoloService.byTags(tags), HttpStatus.OK);
    }
    @GetMapping("/by-keyword")
    public ResponseEntity<List<VisualizzaArticoloDTO>> byKeyword(@RequestParam String keyword) {
        return new ResponseEntity<>(articoloService.byContenutoOrTitolo(keyword), HttpStatus.OK);
    }
    @PutMapping("/approve")
    public ResponseEntity<Void> approve(@RequestBody Long id) {
        articoloService.approveArticle(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@RequestBody Long id) {
        articoloService.deleteArticolo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/custom-blog-validation")
    public ResponseEntity<Void> setBlogValidations(@RequestBody ValidazioneDinamicaBlogDTO validazioneDinamicaBlogDTO) {
        articoloService.setValidazioniArticolo(validazioneDinamicaBlogDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/voted")
    public ResponseEntity<Void> voted(@RequestBody AggiuntaVotoDTO voto) {
        articoloService.setVote(voto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/add-comment")
    public ResponseEntity<Void> addComment(@RequestBody AggiuntaCommentoDTO commento) {
        articoloService.addComment(commento);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
