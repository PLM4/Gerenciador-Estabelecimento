package com.gereciador.estabelecimento.controllers;

import com.gereciador.estabelecimento.controllers.dto.request.PagamentoRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.request.TipoPagamentoDTO;
import com.gereciador.estabelecimento.controllers.dto.response.PagamentoResponseDTO;
import com.gereciador.estabelecimento.services.PagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> create(@RequestBody PagamentoRequestDTO dto) {
        PagamentoResponseDTO pagamentoResponseDTO = this.pagamentoService.save(dto);
        return new ResponseEntity<>(pagamentoResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> findById(@PathVariable Long id) {
        PagamentoResponseDTO pagamentoResponseDTO = this.pagamentoService.getById(id);
        return new ResponseEntity<>(pagamentoResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> update(@PathVariable Long id, @RequestBody PagamentoRequestDTO dto) {
        PagamentoResponseDTO pagamentoResponseDTO = this.pagamentoService.update(id, dto);
        return new ResponseEntity<>(pagamentoResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        this.pagamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> findAll() {
        List<PagamentoResponseDTO> pagamentoResponseDTOS = this.pagamentoService.getAll();
        return new ResponseEntity<>(pagamentoResponseDTOS, HttpStatus.OK);
    }

    @PatchMapping("/finalizar/{id}")
    public ResponseEntity<PagamentoResponseDTO> finalizarPedido(@PathVariable Long id, @RequestBody TipoPagamentoDTO tipoPagamentoDTO) {
        PagamentoResponseDTO dto = this.pagamentoService.finalizarPedido(id, tipoPagamentoDTO.tipoPagamento());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}
