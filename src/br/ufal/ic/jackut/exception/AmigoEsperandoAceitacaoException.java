package br.ufal.ic.jackut.exception;

public class AmigoEsperandoAceitacaoException extends Exception {
    public AmigoEsperandoAceitacaoException() {
        super("Usu\u00e1rio j\u00e1 est\u00e1 adicionado como amigo, esperando aceita\u00e7\u00e3o do convite.");
    }
}