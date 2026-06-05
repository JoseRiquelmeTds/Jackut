package br.ufal.ic.jackut;

import easyaccept.EasyAccept;

public class Main {
    public static void main(String[] args) {
        String facadeClass = "br.ufal.ic.jackut.Facade";

        // Executa os testes sequencialmente no IntelliJ
        EasyAccept.main(new String[] { facadeClass, "src/tests/us1_1.txt" });
        EasyAccept.main(new String[] { facadeClass, "src/tests/us1_2.txt" });
    }
}