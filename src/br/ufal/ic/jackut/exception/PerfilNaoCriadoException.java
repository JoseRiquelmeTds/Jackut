package br.ufal.ic.jackut.exception;

public class PerfilNaoCriadoException extends Exception{
    public PerfilNaoCriadoException(){
        super("Perfil não criado.");
    }
}
