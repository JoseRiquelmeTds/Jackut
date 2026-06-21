package br.ufal.ic.jackut.exception;

public class UsuarioNaoEncontradoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException() {
        super("Usuário não encontrado.");
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
