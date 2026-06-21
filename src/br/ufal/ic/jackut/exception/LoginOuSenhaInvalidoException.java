package br.ufal.ic.jackut.exception;

public class LoginOuSenhaInvalidoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public LoginOuSenhaInvalidoException() {
        super("Login ou senha inválidos.");
    }

    public LoginOuSenhaInvalidoException(String message) {
        super(message);
    }

    public LoginOuSenhaInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginOuSenhaInvalidoException(Throwable cause) {
        super(cause);
    }
}
