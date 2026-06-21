package br.ufal.ic.jackut.exception;

public class LoginInvalidoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public LoginInvalidoException() {
        super("Login inválido.");
    }

    public LoginInvalidoException(String message) {
        super(message);
    }

    public LoginInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginInvalidoException(Throwable cause) {
        super(cause);
    }
}
