package com.blogapp.controllers.blog;

import com.blogapp.dtos.blog.AggiuntaCategoriaDTO;
import com.blogapp.dtos.blog.VisualizzaCategoriaDTO;
import com.blogapp.services.blog.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping("/add")
    public ResponseEntity<Void> aggiungi(@RequestBody AggiuntaCategoriaDTO categoria) {
        categoriaService.addCategoria(categoria);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<VisualizzaCategoriaDTO>> getAll() {
        return new ResponseEntity<>(categoriaService.getAll(), HttpStatus.OK);
    }
}
