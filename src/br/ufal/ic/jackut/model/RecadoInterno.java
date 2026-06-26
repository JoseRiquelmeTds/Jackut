package br.ufal.ic.jackut.model;

import java.io.Serializable;

class RecadoInterno implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String remetente;
    private final String texto;

    RecadoInterno(String remetente, String texto) {
        this.remetente = remetente;
        this.texto = texto;
    }

    String getRemetente() {
        return remetente;
    }

    String getTexto() {
        return texto;
    }
}
