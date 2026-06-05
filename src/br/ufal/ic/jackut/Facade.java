package br.ufal.ic.jackut;

import br.ufal.ic.jackut.controller.SistemaJackut;

public class Facade {
    private SistemaJackut sistema = new SistemaJackut();

    public void zerarSistema() {
        sistema.zerarSistema();
    }

    public void criarUsuario(String login, String senha, String nome) throws Exception {
        sistema.criarUsuario(login, senha, nome);
    }

    public String abrirSessao(String login, String senha) throws Exception {
        return sistema.abrirSessao(login, senha);
    }

    public String getAtributoUsuario(String login, String atributo) throws Exception {
        return sistema.getAtributoUsuario(login, atributo);
    }

    public void encerrarSistema() {
        sistema.encerrarSistema();
    }
}