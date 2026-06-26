package br.ufal.ic.jackut.exception;

public class UsuarioJaFazParteDaComunidadeException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioJaFazParteDaComunidadeException() {
        super("Usuario já faz parte dessa comunidade.");
    }

    public UsuarioJaFazParteDaComunidadeException(String message) {
        super(message);
    }

    public UsuarioJaFazParteDaComunidadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsuarioJaFazParteDaComunidadeException(Throwable cause) {
        super(cause);
    }
}
