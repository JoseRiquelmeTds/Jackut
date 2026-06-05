package br.ufal.ic.jackut.exception;

public class NaoHaRecadosException extends Exception {
    public NaoHaRecadosException() {
        super("N\u00e3o h\u00e1 recados.");
    }
}