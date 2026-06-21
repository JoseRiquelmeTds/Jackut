package br.ufal.ic.jackut.exception;

public class NaoHaRecadosException extends JackutException {
    private static final long serialVersionUID = 1L;

    public NaoHaRecadosException() {
        super("N\u00e3o h\u00e1 recados.");
    }

    public NaoHaRecadosException(String message) {
        super(message);
    }

    public NaoHaRecadosException(String message, Throwable cause) {
        super(message, cause);
    }

    public NaoHaRecadosException(Throwable cause) {
        super(cause);
    }
}
