package br.ufal.ic.jackut.exception;

public class SenhaInvalidaException extends JackutException {
    private static final long serialVersionUID = 1L;

    public SenhaInvalidaException() {
        super("Senha inválida.");
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
