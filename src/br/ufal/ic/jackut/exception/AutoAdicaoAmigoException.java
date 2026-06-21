package br.ufal.ic.jackut.exception;

public class AutoAdicaoAmigoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public AutoAdicaoAmigoException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }

    public AutoAdicaoAmigoException(String message) {
        super(message);
    }

    public AutoAdicaoAmigoException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutoAdicaoAmigoException(Throwable cause) {
        super(cause);
    }
}
