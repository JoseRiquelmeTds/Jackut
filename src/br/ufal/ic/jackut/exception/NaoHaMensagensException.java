package br.ufal.ic.jackut.exception;

public class NaoHaMensagensException extends JackutException {
    private static final long serialVersionUID = 1L;

    public NaoHaMensagensException() {
        super("Não há mensagens.");
    }

    public NaoHaMensagensException(String message) {
        super(message);
    }

    public NaoHaMensagensException(String message, Throwable cause) {
        super(message, cause);
    }

    public NaoHaMensagensException(Throwable cause) {
        super(cause);
    }
}
