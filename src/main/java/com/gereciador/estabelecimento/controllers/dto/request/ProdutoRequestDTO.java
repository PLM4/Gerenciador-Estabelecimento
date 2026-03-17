package com.gereciador.estabelecimento.controllers.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ProdutoRequestDTO(
    String nome,
    List<Long> idsCategorias,
    BigDecimal preco,
    Integer quantidade,
    List<Long> idsFornecedores,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate validade) {
}
