package com.gereciador.estabelecimento.exceptions;

public class PedidoNotFoundException extends RuntimeException {
  public PedidoNotFoundException(String message) {
    super(message);
  }
}
