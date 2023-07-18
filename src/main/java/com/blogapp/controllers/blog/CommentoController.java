package com.blogapp.controllers.blog;

import com.blogapp.dtos.blog.AggiuntaCommentoDTO;
import com.blogapp.dtos.blog.AggiuntaRispostaDTO;
import com.blogapp.dtos.blog.VisualizzaCommentoDTO;
import com.blogapp.dtos.blog.VisualizzaRispostaDTO;
import com.blogapp.services.blog.CommentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentoController {

    private final CommentoService commentoService;

    @PostMapping("/add")
    public ResponseEntity<Void> addComment(@RequestBody AggiuntaCommentoDTO commento) {
        commentoService.addComment(commento);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/by-art-id")
    public ResponseEntity<List<VisualizzaCommentoDTO>> commentsById(@RequestParam Long id) {
        return new ResponseEntity<>(commentoService.getCommentsByArtId(id), HttpStatus.OK);
    }
    @PostMapping("/add-reply")
    public ResponseEntity<Void> addReply(@RequestBody AggiuntaRispostaDTO reply) {
        commentoService.addReply(reply);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//    @GetMapping("/replies-by-art-id")
//    public ResponseEntity<List<VisualizzaRispostaDTO>> repliesByArtId(@RequestParam Long id) {
//        return new ResponseEntity<>(commentoService.getRepliesByArtId(id), HttpStatus.OK);
//    }
    @GetMapping("/replies-by-artId-commId")
    public ResponseEntity<List<VisualizzaRispostaDTO>> repliesByArtIndAndCommId(@RequestParam Long artId, @RequestParam Long commId) {
        return new ResponseEntity<>(commentoService.getRepliesByArtIdAndCommId(artId, commId), HttpStatus.OK);
    }
}
