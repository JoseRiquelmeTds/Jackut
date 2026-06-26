package br.ufal.ic.jackut.exception;

public class UsuarioJaAdicionadoComoPaqueraException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioJaAdicionadoComoPaqueraException() {
        super("Usu\u00e1rio j\u00e1 est\u00e1 adicionado como paquera.");
    }
}
