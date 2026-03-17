package com.gereciador.estabelecimento.controllers;

import com.gereciador.estabelecimento.controllers.dto.request.ProdutoRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.ProdutoResponseDTO;
import com.gereciador.estabelecimento.services.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> create(@RequestBody ProdutoRequestDTO dto){
        ProdutoResponseDTO produtoResponseDTO = this.produtoService.save(dto);
        return new ResponseEntity<>(produtoResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> findById(@PathVariable Long id) {
        ProdutoResponseDTO produtoResponseDTO = this.produtoService.getById(id);
        return new ResponseEntity<>(produtoResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> update(@PathVariable Long id, @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO produtoResponseDTO = this.produtoService.update(id, dto);
        return new ResponseEntity<>(produtoResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        this.produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> findAll() {
        List<ProdutoResponseDTO> produtoResponseDTOS = this.produtoService.getAll();
        return new ResponseEntity<>(produtoResponseDTOS, HttpStatus.OK);
    }
}
