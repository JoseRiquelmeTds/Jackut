package br.ufal.ic.jackut.exception;

public class FuncaoInvalidaException extends JackutException {
    private static final long serialVersionUID = 1L;

    public FuncaoInvalidaException(String mensagem) {
        super("Fun\u00e7\u00e3o inv\u00e1lida: " + mensagem);
    }
}
