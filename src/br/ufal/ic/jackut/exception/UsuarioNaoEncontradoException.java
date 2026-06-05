package br.ufal.ic.jackut.exception;

public class UsuarioNaoEncontradoException extends Exception {
    public UsuarioNaoEncontradoException() {
        super("Usu\u00e1rio n\u00e3o encontrado.");
    }
}