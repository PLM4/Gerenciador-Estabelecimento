package com.gereciador.estabelecimento.controllers;


import com.gereciador.estabelecimento.controllers.dto.request.CategoriaRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.CategoriaResponseDTO;
import com.gereciador.estabelecimento.services.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> create(@RequestBody CategoriaRequestDTO dto){
        CategoriaResponseDTO responseDTO = this.categoriaService.save(dto);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) {
        CategoriaResponseDTO responseDTO = this.categoriaService.getById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> update(@PathVariable Long id, @RequestBody CategoriaRequestDTO dto){
        CategoriaResponseDTO responseDTO = this.categoriaService.update(id, dto);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        this.categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> findAll(){
        List<CategoriaResponseDTO> responseDTO = this.categoriaService.getAll();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<CategoriaResponseDTO> findByNome(@RequestParam String nome) {
        CategoriaResponseDTO responseDTO = this.categoriaService.findCategoriaByNome(nome);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
