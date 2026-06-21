package br.ufal.ic.jackut.exception;

public class UsuarioNaoCadastradoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoCadastradoException() {
        super("Usu\u00e1rio n\u00e3o cadastrado.");
    }

    public UsuarioNaoCadastradoException(String message) {
        super(message);
    }

    public UsuarioNaoCadastradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsuarioNaoCadastradoException(Throwable cause) {
        super(cause);
    }
}
