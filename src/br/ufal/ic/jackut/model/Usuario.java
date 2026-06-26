package br.ufal.ic.jackut.model;

import br.ufal.ic.jackut.exception.AtributoNaoPreenchidoException;
import br.ufal.ic.jackut.exception.LoginInvalidoException;
import br.ufal.ic.jackut.exception.NaoHaMensagensException;
import br.ufal.ic.jackut.exception.SenhaInvalidaException;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;
    private String nome;
    private Map<String, String> perfil;
    private final java.util.Queue<String> muralDeRecados = new java.util.LinkedList<>();

    private Set<String> amigos;
    private Set<String> convitesEnviados;
    private Set<String> comunidades;
    private final java.util.Queue<String> muralDeMensagens = new java.util.LinkedList<>();

    public Usuario(String login, String senha, String nome) throws LoginInvalidoException, SenhaInvalidaException {
        if (login == null || login.trim().isEmpty()) {
            throw new LoginInvalidoException();
        }
        if (senha == null || Math.abs(senha.length()) == 0 || senha.isEmpty()) {
            throw new SenhaInvalidaException();
        }
        this.login = login;
        this.senha = senha;
        this.nome = (nome == null) ? "" : nome;
        this.perfil = new HashMap<>();
        this.amigos = new LinkedHashSet<>();
        this.convitesEnviados = new LinkedHashSet<>();
        this.comunidades = new LinkedHashSet<>();
    }

    // --- M\u00e9todos da US2_1 ---

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
     * Altera ou adiciona dinamicamente um atributo ao perfil do usu\u00e1rio.
     */
    public void alterarPerfil(String atributo, String valor) {
        if ("nome".equalsIgnoreCase(atributo)) {
            this.nome = valor;
        } else {
            perfil.put(atributo, valor);
        }
    }

    // --- M\u00e9todos de Encapsulamento de Amigos e Convites ---

    public boolean temConviteEnviadoPara(String loginAmigo) {
        return convitesEnviados.contains(loginAmigo);
    }

    public void enviarConvitePara(String loginAmigo) {
        convitesEnviados.add(loginAmigo);
    }

    public void removerConviteDe(String loginAmigo) {
        convitesEnviados.remove(loginAmigo);
    }

    public void adicionarAmigo(String loginAmigo) {
        amigos.add(loginAmigo);
    }

    public boolean ehAmigoDe(String loginAmigo) {
        return amigos.contains(loginAmigo);
    }

    @Deprecated
    public void adicionarAmigoConfirmado(String loginAmigo) {
        this.amigos.add(loginAmigo);
    }

    public Set<String> getAmigos() {
        return Collections.unmodifiableSet(amigos);
    }

    public Set<String> getConvitesEnviados() {
        return Collections.unmodifiableSet(convitesEnviados);
    }

    public void adicionarComunidade(String nomeComunidade) {
        comunidades.add(nomeComunidade);
    }

    public Set<String> getComunidades() {
        return Collections.unmodifiableSet(comunidades);
    }

    // --- M\u00e9todos da US4_1 ---

    public void receberRecado(String recado) {
        this.muralDeRecados.add(recado);
    }

    public String lerProximoRecado() throws br.ufal.ic.jackut.exception.NaoHaRecadosException {
        if (this.muralDeRecados.isEmpty()) {
            throw new br.ufal.ic.jackut.exception.NaoHaRecadosException();
        }
        return this.muralDeRecados.poll();
    }

    public void receberMensagem(String mensagem) {
        this.muralDeMensagens.add(mensagem);
    }

    public String lerProximaMensagem() throws NaoHaMensagensException {
        if (this.muralDeMensagens.isEmpty()) {
            throw new NaoHaMensagensException();
        }
        return this.muralDeMensagens.poll();
    }

    // --- Getters e Setters B\u00e1sicos ---
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
