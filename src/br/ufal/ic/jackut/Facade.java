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

    public void editarPerfil(String idSessao, String atributo, String valor) throws Exception {
        sistema.editarPerfil(idSessao, atributo, valor);
    }

    public void encerrarSistema() {
        sistema.encerrarSistema();
    }

    public void adicionarAmigo(String id, String amigo) throws Exception {
        sistema.adicionarAmigo(id, amigo);
    }

    public boolean ehAmigo(String login, String amigo) {
        return sistema.ehAmigo(login, amigo);
    }

    public String getAmigos(String login) {
        return sistema.getAmigos(login);
    }

    public void enviarRecado(String id, String destinatario, String recado) throws Exception {
        sistema.enviarRecado(id, destinatario, recado);
    }

    public String lerRecado(String id) throws Exception {
        return sistema.lerRecado(id);
    }
}