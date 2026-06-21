package br.ufal.ic.jackut.exception;

public class SessaoInvalidaException extends JackutException {
    private static final long serialVersionUID = 1L;

    public SessaoInvalidaException() {
        super("Sess\u00e3o inv\u00e1lida.");
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
