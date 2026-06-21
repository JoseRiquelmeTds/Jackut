package br.ufal.ic.jackut.exception;

public class PerfilNaoCriadoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public PerfilNaoCriadoException() {
        super("Perfil não criado.");
    }

    public PerfilNaoCriadoException(String message) {
        super(message);
    }

    public PerfilNaoCriadoException(String message, Throwable cause) {
        super(message, cause);
    }

    public PerfilNaoCriadoException(Throwable cause) {
        super(cause);
    }
}
