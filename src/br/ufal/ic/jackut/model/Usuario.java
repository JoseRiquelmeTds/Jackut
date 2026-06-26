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
    private final java.util.Queue<RecadoInterno> muralDeRecados = new java.util.LinkedList<>();

    private Set<String> amigos;
    private Set<String> convitesEnviados;
    private Set<String> comunidades;
    private Set<String> idolos;
    private Set<String> fas;
    private Set<String> paqueras;
    private Set<String> inimigos;
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
        this.idolos = new LinkedHashSet<>();
        this.fas = new LinkedHashSet<>();
        this.paqueras = new LinkedHashSet<>();
        this.inimigos = new LinkedHashSet<>();
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

    public void adicionarFa(String login) {
        fas.add(login);
    }

    public boolean ehFaDe(String login) {
        return fas.contains(login);
    }

    public Set<String> getFas() {
        return Collections.unmodifiableSet(fas);
    }

    public void adicionarIdolo(String login) {
        idolos.add(login);
    }

    public boolean ehIdoloDe(String login) {
        return idolos.contains(login);
    }

    public Set<String> getIdolos() {
        return Collections.unmodifiableSet(idolos);
    }

    public void adicionarPaquera(String login) {
        paqueras.add(login);
    }

    public boolean ehPaqueraDe(String login) {
        return paqueras.contains(login);
    }

    public Set<String> getPaqueras() {
        return Collections.unmodifiableSet(paqueras);
    }

    public void adicionarInimigo(String login) {
        inimigos.add(login);
    }

    public boolean ehInimigoDe(String login) {
        return inimigos.contains(login);
    }

    public Set<String> getInimigos() {
        return Collections.unmodifiableSet(inimigos);
    }

    public void removerAmigo(String login) {
        amigos.remove(login);
    }

    public void removerConviteEnviadoPara(String login) {
        convitesEnviados.remove(login);
    }

    public void removerComunidade(String nomeComunidade) {
        comunidades.remove(nomeComunidade);
    }

    public void removerFa(String login) {
        fas.remove(login);
    }

    public void removerIdolo(String login) {
        idolos.remove(login);
    }

    public void removerPaquera(String login) {
        paqueras.remove(login);
    }

    public void removerInimigo(String login) {
        inimigos.remove(login);
    }

    public void limparPerfil() {
        perfil.clear();
        nome = "";
    }

    public void limparMensagensERecados() {
        muralDeMensagens.clear();
        muralDeRecados.clear();
    }

    // --- M\u00e9todos da US4_1 ---

    public void receberRecado(String remetente, String recado) {
        this.muralDeRecados.add(new RecadoInterno(remetente, recado));
    }

    public String lerProximoRecado() throws br.ufal.ic.jackut.exception.NaoHaRecadosException {
        if (this.muralDeRecados.isEmpty()) {
            throw new br.ufal.ic.jackut.exception.NaoHaRecadosException();
        }
        return this.muralDeRecados.poll().getTexto();
    }

    public void removerRecadosDe(String remetente) {
        java.util.Queue<RecadoInterno> filtrados = new java.util.LinkedList<>();
        for (RecadoInterno recado : muralDeRecados) {
            if (!recado.getRemetente().equals(remetente)) {
                filtrados.add(recado);
            }
        }
        muralDeRecados.clear();
        muralDeRecados.addAll(filtrados);
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
