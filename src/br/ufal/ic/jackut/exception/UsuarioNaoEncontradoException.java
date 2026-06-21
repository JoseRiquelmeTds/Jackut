package br.ufal.ic.jackut.exception;

public class UsuarioNaoEncontradoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException() {
        super("Usu\u00e1rio n\u00e3o encontrado.");
    }

    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsuarioNaoEncontradoException(Throwable cause) {
        super(cause);
    }
}
