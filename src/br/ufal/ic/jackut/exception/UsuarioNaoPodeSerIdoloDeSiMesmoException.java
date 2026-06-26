package br.ufal.ic.jackut.exception;

public class UsuarioNaoPodeSerIdoloDeSiMesmoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoPodeSerIdoloDeSiMesmoException() {
        super("Usu\u00e1rio n\u00e3o pode ser f\u00e3 de si mesmo.");
    }
}
