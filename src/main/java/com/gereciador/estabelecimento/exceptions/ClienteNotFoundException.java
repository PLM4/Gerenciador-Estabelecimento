package com.gereciador.estabelecimento.exceptions;

public class ClienteNotFoundException extends RuntimeException {
  public ClienteNotFoundException() {
    super("Cliente não encontrado");
  }
}
