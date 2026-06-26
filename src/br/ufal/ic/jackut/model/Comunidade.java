package br.ufal.ic.jackut.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Comunidade implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String nome;
    private final String descricao;
    private final String dono;
    private final Set<String> membros;

    public Comunidade(String nome, String descricao, String dono) {
        this.nome = nome;
        this.descricao = descricao;
        this.dono = dono;
        this.membros = new LinkedHashSet<>();
        this.membros.add(dono);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDono() {
        return dono;
    }

    public Set<String> getMembros() {
        return Collections.unmodifiableSet(membros);
    }
}
