package br.ufal.ic.jackut.exception;

public class AtributoNaoPreenchidoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public AtributoNaoPreenchidoException() {
        super("Atributo n\u00e3o preenchido.");
    }

    public AtributoNaoPreenchidoException(String message) {
        super(message);
    }

    public AtributoNaoPreenchidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public AtributoNaoPreenchidoException(Throwable cause) {
        super(cause);
    }
}
