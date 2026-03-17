package com.gereciador.estabelecimento.exceptions;

public class ProdutoNotFoundException extends RuntimeException {
  public ProdutoNotFoundException() {
    super("Produto não encontrado");
  }
}
