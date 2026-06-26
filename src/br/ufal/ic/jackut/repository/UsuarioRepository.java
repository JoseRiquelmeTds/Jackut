package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.model.Usuario;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.Map;

public class UsuarioRepository {
    static final String ARQUIVO_DADOS = "jackut.dat";
    static DadosPersistidos dadosPersistidos = new DadosPersistidos();
    private Map<String, Usuario> usuarios;

    public UsuarioRepository() {
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

    public Collection<Usuario> todos() {
        return usuarios.values();
    }

    public void remover(String login) {
        usuarios.remove(login);
    }

    public void limpar() {
        usuarios.clear();
        dadosPersistidos.usuarios.clear();
        new File(ARQUIVO_DADOS).delete();
    }

    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            dadosPersistidos.usuarios = usuarios;
            oos.writeObject(dadosPersistidos);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public void carregarDados() {
        this.usuarios = dadosPersistidos.usuarios;
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                Object objeto = ois.readObject();
                if (objeto instanceof DadosPersistidos) {
                    dadosPersistidos = (DadosPersistidos) objeto;
                    usuarios = dadosPersistidos.usuarios;
                } else if (objeto instanceof Map) {
                    usuarios = (Map<String, Usuario>) objeto;
                    dadosPersistidos.usuarios = usuarios;
                }
            } catch (IOException | ClassNotFoundException e) {
                usuarios = new LinkedHashMap<>();
                dadosPersistidos.usuarios = usuarios;
                System.err.println("Erro ao carregar dados, iniciando com mapa vazio: " + e.getMessage());
            }
        } else if (usuarios == null) {
            usuarios = new LinkedHashMap<>();
            dadosPersistidos.usuarios = usuarios;
        }
    }
}
