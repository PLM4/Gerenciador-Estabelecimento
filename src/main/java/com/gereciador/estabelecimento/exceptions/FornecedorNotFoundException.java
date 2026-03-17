package com.gereciador.estabelecimento.exceptions;

public class FornecedorNotFoundException extends RuntimeException {

  public FornecedorNotFoundException() {
    super("Fornecedor não encontrado");
  }
}
