package br.ufal.ic.jackut.exception;

public class AutoEnvioRecadoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public AutoEnvioRecadoException() {
        super("Usuário não pode enviar recado para si mesmo.");
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
