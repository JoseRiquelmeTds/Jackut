package br.ufal.ic.jackut.exception;

public class ContaJaExisteException extends JackutException {
    private static final long serialVersionUID = 1L;

    public ContaJaExisteException() {
        super("Conta com esse nome já existe.");
    }

    public ContaJaExisteException(String message) {
        super(message);
    }

    public ContaJaExisteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContaJaExisteException(Throwable cause) {
        super(cause);
    }
}
