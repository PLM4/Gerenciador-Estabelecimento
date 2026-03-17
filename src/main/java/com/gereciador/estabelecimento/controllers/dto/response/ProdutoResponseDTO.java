package com.gereciador.estabelecimento.controllers.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ProdutoResponseDTO(
    Long id,
    String nome,
    List<CategoriaResponseDTO> categorias,
    BigDecimal preco,
    Integer quantidade,
    List<FornecedorResponseDTO> fornecedores,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate validade) {
}
