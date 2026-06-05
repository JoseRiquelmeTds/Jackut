package br.ufal.ic.jackut.controller;

import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.exception.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class SistemaJackut {
    private Map<String, Usuario> usuarios;
    private Map<String, String> sessoesAtivas; // <idSessao, loginUsuario>
    private final String ARQUIVO_DADOS = "jackut.dat";

    public SistemaJackut() {
        this.usuarios = new HashMap<>();
        this.sessoesAtivas = new HashMap<>();
        carregarDados();
    }

    public void criarUsuario(String login, String senha, String nome) throws Exception {
        if (usuarios.containsKey(login)) {
            throw new ContaJaExisteException();
        }
        Usuario novoUsuario = new Usuario(login, senha, nome);
        usuarios.put(login, novoUsuario);
    }

    public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException {
        Usuario usuario = usuarios.get(login);
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            // Regra da us1_1: Não dizer se o erro foi no login ou na senha por segurança
            throw new LoginOuSenhaInvalidoException();
        }

        String idSessao = UUID.randomUUID().toString();
        sessoesAtivas.put(idSessao, login);
        return idSessao;
    }

    /**
     * US2_1: Atualização do método antigo. Agora ele busca atributos dinâmicos do perfil
     * se o usuário existir no sistema.
     */
    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }

        // Busca o valor dentro do mapa dinâmico do usuário
        return usuario.getAtributoPerfil(atributo);
    }

    public void zerarSistema() {
        usuarios.clear();
        sessoesAtivas.clear();
        File f = new File(ARQUIVO_DADOS);
        if (f.exists()) {
            f.delete();
        }
    }

    public void encerrarSistema() {
        salvarDados();
    }

    // --- Métodos de Persistência (US1_2) ---
    private void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * US2_1: Altera um atributo do perfil do usuário mascarado pela sessão atual.
     */
    public void editarPerfil(String idSessao, String atributo, String valor) throws Exception {
        // Se o ID for inválido, nulo ou vazio, o script espera "Usuário não cadastrado."
        if (idSessao == null || idSessao.trim().isEmpty() || !sessoesAtivas.containsKey(idSessao)) {
            throw new UsuarioNaoCadastradoException();
        }

        String loginUsuario = sessoesAtivas.get(idSessao);

        if (atributo == null || atributo.trim().isEmpty() || valor == null || valor.trim().isEmpty()) {
            throw new AtributoNaoPreenchidoException();
        }

        Usuario usuario = usuarios.get(loginUsuario);
        usuario.alterarPerfil(atributo, valor);
    }

    /**
     * US3_1
     */
    public void adicionarAmigo(String idSessao, String amigo) throws Exception {
        if (idSessao == null || idSessao.trim().isEmpty() || !sessoesAtivas.containsKey(idSessao)) {
            throw new UsuarioNaoCadastradoException();
        }

        String loginRemetente = sessoesAtivas.get(idSessao);

        if (amigo == null || amigo.trim().isEmpty() || !usuarios.containsKey(amigo)) {
            throw new UsuarioNaoCadastradoException();
        }

        if (loginRemetente.equals(amigo)) {
            throw new AutoAdicaoAmigoException();
        }

        Usuario remetente = usuarios.get(loginRemetente);
        Usuario destinatario = usuarios.get(amigo);

        if (remetente.getAmigos().contains(amigo)) {
            throw new AmigoJaAdicionadoException();
        }

        if (remetente.getConvitesEnviados().contains(amigo)) {
            throw new AmigoEsperandoAceitacaoException();
        }

        if (destinatario.getConvitesEnviados().contains(loginRemetente)) {
            destinatario.getConvitesEnviados().remove(loginRemetente);
            remetente.getAmigos().add(amigo);
            destinatario.getAmigos().add(loginRemetente);
        } else {
            remetente.getConvitesEnviados().add(amigo);
        }
    }

    /**
     * US3: Verifica se dois usuários são amigos.
     */
    public boolean ehAmigo(String login, String amigo) {
        if (!usuarios.containsKey(login)) {
            return false;
        }
        return usuarios.get(login).getAmigos().contains(amigo);
    }

    /**
     * US3: Retorna a lista de amigos formatada como String no padrão {amigo1,amigo2}
     */
    public String getAmigos(String login) {
        if (!usuarios.containsKey(login)) {
            return "{}";
        }

        Set<String> listaAmigos = usuarios.get(login).getAmigos();
        if (listaAmigos.isEmpty()) {
            return "{}";
        }

        // Converte a coleção para o formato "amigo1,amigo2"
        StringBuilder sb = new StringBuilder("{");
        for (String nomeAmigo : listaAmigos) {
            sb.append(nomeAmigo).append(",");
        }
        // Remove a última vírgula sobressalente e fecha com chaves
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");

        return sb.toString();
    }


    @SuppressWarnings("unchecked")
    private void carregarDados() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                usuarios = (Map<String, Usuario>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                usuarios = new HashMap<>();
            }
        }
    }
}