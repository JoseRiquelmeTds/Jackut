package br.ufal.ic.jackut.exception;

public class SessaoInvalidaException extends Exception {
    public SessaoInvalidaException() {
        super("Sess\u00e3o inv\u00e1lida."); // "Sessão inválida."
    }
}