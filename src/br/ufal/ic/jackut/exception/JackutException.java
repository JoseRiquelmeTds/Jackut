package br.ufal.ic.jackut.exception;

public class JackutException extends Exception {
    private static final long serialVersionUID = 1L;

    public JackutException() {
        super();
    }

    public JackutException(String message) {
        super(message);
    }

    public JackutException(String message, Throwable cause) {
        super(message, cause);
    }

    public JackutException(Throwable cause) {
        super(cause);
    }
}
