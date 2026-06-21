package br.ufal.ic.jackut.exception;

public class SenhaInvalidaException extends JackutException {
    private static final long serialVersionUID = 1L;

    public SenhaInvalidaException() {
        super("Senha inv\u00e1lida.");
    }

    public SenhaInvalidaException(String message) {
        super(message);
    }

    public SenhaInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }

    public SenhaInvalidaException(Throwable cause) {
        super(cause);
    }
}
