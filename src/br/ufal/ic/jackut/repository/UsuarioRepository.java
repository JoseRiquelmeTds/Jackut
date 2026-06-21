package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.model.Usuario;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UsuarioRepository {
    private Map<String, Usuario> usuarios;
    private final String ARQUIVO_DADOS = "jackut.dat";

    public UsuarioRepository() {
        this.usuarios = new HashMap<>();
        carregarDados();
    }

    public boolean existe(String login) {
        return usuarios.containsKey(login);
    }

    public void salvar(Usuario usuario) {
        usuarios.put(usuario.getLogin(), usuario);
    }

    public Usuario buscarPorLogin(String login) {
        return usuarios.get(login);
    }

    public void remover(String login) {
        usuarios.remove(login);
    }

    public void limpar() {
        usuarios.clear();
        File f = new File(ARQUIVO_DADOS);
        if (f.exists()) {
            f.delete();
        }
    }

    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDados() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                usuarios = (Map<String, Usuario>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                usuarios = new HashMap<>();
                System.err.println("Erro ao carregar dados, iniciando com mapa vazio: " + e.getMessage());
            }
        } else {
            usuarios = new HashMap<>();
        }
    }
}
