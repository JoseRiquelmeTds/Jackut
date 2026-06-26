package br.ufal.ic.jackut.exception;

public class UsuarioNaoPodeSerPaqueraDeSiMesmoException extends JackutException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoPodeSerPaqueraDeSiMesmoException() {
        super("Usu\u00e1rio n\u00e3o pode ser paquera de si mesmo.");
    }
}
