package br.ufal.ic.jackut.exception;

public class PerfilNaoCriadoException extends Exception {
    public PerfilNaoCriadoException() {
        super("Perfil n\u00e3o criado."); // "Perfil não criado."
    }
}
