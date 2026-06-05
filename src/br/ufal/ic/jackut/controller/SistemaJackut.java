package br.ufal.ic.jackut.controller;

import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.exception.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
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

    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }

        if ("nome".equalsIgnoreCase(atributo)) {
            return usuario.getNome();
        }
        return "";
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