package br.ufal.ic.jackut.exception;

public class UsuarioJaAdicionadoComoIdoloException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioJaAdicionadoComoIdoloException() {
        super("Usu\u00e1rio j\u00e1 est\u00e1 adicionado como \u00eddolo.");
    }
}
