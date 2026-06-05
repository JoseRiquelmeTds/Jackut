package br.ufal.ic.jackut.exception;

public class SenhaInvalidaException extends Exception {
    public SenhaInvalidaException() {
        super("Senha inv\u00e1lida.");
    }
}