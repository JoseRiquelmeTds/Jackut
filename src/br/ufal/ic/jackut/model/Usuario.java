package br.ufal.ic.jackut.model;

import br.ufal.ic.jackut.exception.LoginInvalidoException;
import br.ufal.ic.jackut.exception.SenhaInvalidaException;
import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;
    private String nome;

    public Usuario(String login, String senha, String nome) throws LoginInvalidoException, SenhaInvalidaException {
        if (login == null || login.trim().isEmpty()) {
            throw new LoginInvalidoException();
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new SenhaInvalidaException();
        }
        this.login = login;
        this.senha = senha;
        // O teste us1_1 mostra que nome vazio é permitido: criarUsuario login=jpsauve3 senha=3sauvejp nome=""
        this.nome = nome;
    }

    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public String getNome()  { return nome;  }
}