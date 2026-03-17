package com.gereciador.estabelecimento.controllers.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gereciador.estabelecimento.enums.Status;

public record PedidoResponseDTO(
    Long id,
    List<ItemPedidoResponseDTO> itensPedido,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate data,
    Status status) {
}
