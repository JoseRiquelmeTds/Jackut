package br.ufal.ic.jackut;

import easyaccept.EasyAccept;

public class Main {
    public static void main(String[] args) {
        String facadeClass = "br.ufal.ic.jackut.Facade";

        // Executa os testes sequencialmente no IntelliJ
        EasyAccept.main(new String[] { facadeClass, "src/tests/us1_1.txt" });
        EasyAccept.main(new String[] { facadeClass, "src/tests/us1_2.txt" });
        EasyAccept.main(new String[] { facadeClass, "src/tests/us2_1.txt" });
        EasyAccept.main(new String[] { facadeClass, "src/tests/us2_2.txt" });
        EasyAccept.main(new String[] { facadeClass, "src/tests/us3_1.txt" });
        EasyAccept.main(new String[] { facadeClass, "src/tests/us3_2.txt" });
        EasyAccept.main(new String[] { facadeClass, "src/tests/us4_1.txt" });
        EasyAccept.main(new String[] { facadeClass, "src/tests/us4_2.txt" });
        EasyAccept.main(new String[] { facadeClass, "src/tests/us5_1.txt" });
        EasyAccept.main(new String[] { facadeClass, "src/tests/us5_2.txt" });
        EasyAccept.main(new String[] { facadeClass, "src/tests/us6_1.txt" });
        EasyAccept.main(new String[] { facadeClass, "src/tests/us6_2.txt" });
        EasyAccept.main(new String[] { facadeClass, "src/tests/us7_1.txt" });
        EasyAccept.main(new String[] { facadeClass, "src/tests/us7_2.txt" });
    }
}
