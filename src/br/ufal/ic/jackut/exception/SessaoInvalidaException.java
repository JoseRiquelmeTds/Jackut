package br.ufal.ic.jackut.exception;

public class SessaoInvalidaException extends JackutException {
    private static final long serialVersionUID = 1L;

    public SessaoInvalidaException() {
        super("Sessão inválida.");
    }

    public SessaoInvalidaException(String message) {
        super(message);
    }

    public SessaoInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessaoInvalidaException(Throwable cause) {
        super(cause);
    }
}
