package com.gereciador.estabelecimento.exceptions;

public class PagamentoNotFoundException extends RuntimeException {
  public PagamentoNotFoundException() {
    super("Pagamento não encontrado");
  }
}
