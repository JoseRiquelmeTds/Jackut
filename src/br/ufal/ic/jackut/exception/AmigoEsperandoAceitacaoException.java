package br.ufal.ic.jackut.exception;

public class AmigoEsperandoAceitacaoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public AmigoEsperandoAceitacaoException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }

    public AmigoEsperandoAceitacaoException(String message) {
        super(message);
    }

    public AmigoEsperandoAceitacaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public AmigoEsperandoAceitacaoException(Throwable cause) {
        super(cause);
    }
}