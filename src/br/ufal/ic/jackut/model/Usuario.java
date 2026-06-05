package br.ufal.ic.jackut.model;

import br.ufal.ic.jackut.exception.AtributoNaoPreenchidoException;
import br.ufal.ic.jackut.exception.LoginInvalidoException;
import br.ufal.ic.jackut.exception.SenhaInvalidaException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;
    private String nome;

    // Mapa dinâmico para armazenar os atributos do perfil (US2_1)
    private Map<String, String> perfil;

    public Usuario(String login, String senha, String nome) throws LoginInvalidoException, SenhaInvalidaException {
        if (login == null || login.trim().isEmpty()) {
            throw new LoginInvalidoException();
        }
        if (senha == null || Math.abs(senha.length()) == 0 || senha.isEmpty()) { // Garante que qualquer tipo de vazio pegue
            throw new SenhaInvalidaException();
        }
        this.login = login;
        this.senha = senha;
        // Se o nome vier nulo ou vazio, garanta que salve como ""
        this.nome = (nome == null) ? "" : nome;
        this.perfil = new HashMap<>();
    }

    // --- Métodos da US2_1 ---

    public String getAtributoPerfil(String atributo) throws AtributoNaoPreenchidoException {

        if ("nome".equalsIgnoreCase(atributo)) {
            return this.nome;
        }


        if (!perfil.containsKey(atributo) || perfil.get(atributo) == null || perfil.get(atributo).trim().isEmpty()) {
            throw new AtributoNaoPreenchidoException();
        }

        return perfil.get(atributo);
    }

    /**
     * Altera ou adiciona dinamicamente um atributo ao perfil do usuário.
     */
    public void alterarPerfil(String atributo, String valor) {
        if ("nome".equalsIgnoreCase(atributo)) {
            this.nome = valor;
        } else {
            perfil.put(atributo, valor);
        }
    }

    // --- Getters e Setters Básicos ---
    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }
}