package com.blogapp.controllers.blog;

import com.blogapp.dtos.blog.VisualizzaVotoDTO;
import com.blogapp.services.blog.VotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/votes")
public class VotoController {

    private final VotoService votoService;

    @GetMapping("/likes/{artId}")
    public ResponseEntity<List<VisualizzaVotoDTO>> getLikes(@PathVariable Long artId) {
        return new ResponseEntity<>(votoService.getLikesByArticoloId(artId), HttpStatus.OK);
    }
    @GetMapping("/dislikes/{artId}")
    public ResponseEntity<List<VisualizzaVotoDTO>> getDislikes(@PathVariable Long artId) {
        return new ResponseEntity<>(votoService.getDislikesByArticoloid(artId), HttpStatus.OK);
    }
}
