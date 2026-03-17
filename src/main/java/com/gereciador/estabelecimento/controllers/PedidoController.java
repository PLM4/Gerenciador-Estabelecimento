package com.gereciador.estabelecimento.controllers;


import com.gereciador.estabelecimento.controllers.dto.request.PedidoRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.PedidoResponseDTO;
import com.gereciador.estabelecimento.services.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> create(@RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO pedidoResponseDTO = this.pedidoService.save(dto);
        return new ResponseEntity<>(pedidoResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> findById(@PathVariable Long id) {
        PedidoResponseDTO pedidoResponseDTO = this.pedidoService.getById(id);
        return new ResponseEntity<>(pedidoResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> update(@PathVariable Long id, @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO pedidoResponseDTO = this.pedidoService.update(id, dto);
        return new ResponseEntity<>(pedidoResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        this.pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>>  findAll() {
        List<PedidoResponseDTO> pedidoResponseDTOS = this.pedidoService.getAll();
        return new ResponseEntity<>(pedidoResponseDTOS, HttpStatus.OK);
    }

}
