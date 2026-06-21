package br.ufal.ic.jackut.exception;

public class AmigoJaAdicionadoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public AmigoJaAdicionadoException() {
        super("Usuário já está adicionado como amigo.");
    }

    public AmigoJaAdicionadoException(String message) {
        super(message);
    }

    public AmigoJaAdicionadoException(String message, Throwable cause) {
        super(message, cause);
    }

    public AmigoJaAdicionadoException(Throwable cause) {
        super(cause);
    }
}