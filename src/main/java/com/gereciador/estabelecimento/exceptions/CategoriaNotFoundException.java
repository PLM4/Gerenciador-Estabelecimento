package com.gereciador.estabelecimento.exceptions;

public class CategoriaNotFoundException extends RuntimeException {

  public CategoriaNotFoundException() {
    super("Categoria não encontrada");
  }
}
