package com.gereciador.estabelecimento.exceptions;

public class PagamentoFinalizadoException extends RuntimeException {

    public PagamentoFinalizadoException() {
        super("Pagamento já foi finalizado");
    }

}
