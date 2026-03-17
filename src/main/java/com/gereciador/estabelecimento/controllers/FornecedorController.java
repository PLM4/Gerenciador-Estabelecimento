package com.gereciador.estabelecimento.controllers;

import com.gereciador.estabelecimento.controllers.dto.request.FornecedorRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.FornecedorResponseDTO;
import com.gereciador.estabelecimento.services.FornecedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    public ResponseEntity<FornecedorResponseDTO> create(@RequestBody FornecedorRequestDTO dto){
        FornecedorResponseDTO responseDTO = this.fornecedorService.save(dto);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> findById(@PathVariable Long id) {
        FornecedorResponseDTO responseDTO = this.fornecedorService.getById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> update(@PathVariable Long id, @RequestBody FornecedorRequestDTO dto) {
        FornecedorResponseDTO responseDTO = this.fornecedorService.update(id, dto);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        this.fornecedorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> findAll() {
        List<FornecedorResponseDTO> responseDTO = this.fornecedorService.getAll();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
