package com.gereciador.estabelecimento.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("Usuario não encontrado");
  }
}
