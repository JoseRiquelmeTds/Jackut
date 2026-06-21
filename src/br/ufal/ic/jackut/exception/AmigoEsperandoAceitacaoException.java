package br.ufal.ic.jackut.exception;

public class AmigoEsperandoAceitacaoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public AmigoEsperandoAceitacaoException() {
        super("Usu\u00e1rio j\u00e1 est\u00e1 adicionado como amigo, esperando aceita\u00e7\u00e3o do convite.");
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