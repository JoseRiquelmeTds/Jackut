package br.ufal.ic.jackut.exception;

public class ComunidadeJaExisteException extends JackutException {
    private static final long serialVersionUID = 1L;

    public ComunidadeJaExisteException() {
        super("Comunidade com esse nome já existe.");
    }

    public ComunidadeJaExisteException(String message) {
        super(message);
    }

    public ComunidadeJaExisteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComunidadeJaExisteException(Throwable cause) {
        super(cause);
    }
}
