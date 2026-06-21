package br.ufal.ic.jackut.exception;

public class AutoEnvioRecadoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public AutoEnvioRecadoException() {
        super("Usu\u00e1rio n\u00e3o pode enviar recado para si mesmo.");
    }

    public AutoEnvioRecadoException(String message) {
        super(message);
    }

    public AutoEnvioRecadoException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutoEnvioRecadoException(Throwable cause) {
        super(cause);
    }
}
