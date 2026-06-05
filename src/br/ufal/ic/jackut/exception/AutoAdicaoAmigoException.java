package br.ufal.ic.jackut.exception;

public class AutoAdicaoAmigoException extends Exception {
    public AutoAdicaoAmigoException() {
        super("Usu\u00e1rio n\u00e3o pode adicionar a si mesmo como amigo.");
    }
}