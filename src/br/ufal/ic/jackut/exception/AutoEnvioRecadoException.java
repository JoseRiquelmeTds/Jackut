package br.ufal.ic.jackut.exception;

public class AutoEnvioRecadoException extends Exception{
    public AutoEnvioRecadoException(){
        super("Usu\u00e1rio n\u00e3o pode enviar recado para si mesmo.");
    }
}
