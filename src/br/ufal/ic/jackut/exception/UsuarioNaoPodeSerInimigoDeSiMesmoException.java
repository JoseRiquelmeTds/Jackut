package br.ufal.ic.jackut.exception;

public class UsuarioNaoPodeSerInimigoDeSiMesmoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoPodeSerInimigoDeSiMesmoException() {
        super("Usu\u00e1rio n\u00e3o pode ser inimigo de si mesmo.");
    }
}
