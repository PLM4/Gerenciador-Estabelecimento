package com.gereciador.estabelecimento.controllers.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gereciador.estabelecimento.enums.Status;
import com.gereciador.estabelecimento.enums.TipoPagamento;

public record PagamentoResponseDTO(
        Long id,
        BigDecimal valor,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate data,
        Long idPedido,
        ClienteResponseDTO cliente,
        TipoPagamento tipoPagamento,
        Status status) {
}
