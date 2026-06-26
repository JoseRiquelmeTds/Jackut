package br.ufal.ic.jackut.exception;

public class ComunidadeNaoExisteException extends JackutException {
    private static final long serialVersionUID = 1L;

    public ComunidadeNaoExisteException() {
        super("Comunidade não existe.");
    }

    public ComunidadeNaoExisteException(String message) {
        super(message);
    }

    public ComunidadeNaoExisteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComunidadeNaoExisteException(Throwable cause) {
        super(cause);
    }
}
