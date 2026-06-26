package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.model.Comunidade;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ComunidadeRepository {
    private Map<String, Comunidade> comunidades;
    private final String ARQUIVO_DADOS = UsuarioRepository.ARQUIVO_DADOS;

    public ComunidadeRepository() {
        carregarDados();
    }

    public boolean existe(String nome) {
        return comunidades.containsKey(nome);
    }

    public void salvar(Comunidade comunidade) {
        comunidades.put(comunidade.getNome(), comunidade);
    }

    public Comunidade buscarPorNome(String nome) {
        return comunidades.get(nome);
    }

    public Map<String, Comunidade> todas() {
        return comunidades;
    }

    public void limpar() {
        comunidades.clear();
        UsuarioRepository.dadosPersistidos.comunidades.clear();
        new File(ARQUIVO_DADOS).delete();
    }

    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            UsuarioRepository.dadosPersistidos.comunidades = comunidades;
            oos.writeObject(UsuarioRepository.dadosPersistidos);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public void carregarDados() {
        this.comunidades = UsuarioRepository.dadosPersistidos.comunidades;
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                Object objeto = ois.readObject();
                if (objeto instanceof DadosPersistidos) {
                    UsuarioRepository.dadosPersistidos = (DadosPersistidos) objeto;
                    comunidades = UsuarioRepository.dadosPersistidos.comunidades;
                } else if (objeto instanceof Map) {
                    comunidades = (Map<String, Comunidade>) objeto;
                    UsuarioRepository.dadosPersistidos.comunidades = comunidades;
                }
            } catch (IOException | ClassNotFoundException e) {
                comunidades = new LinkedHashMap<>();
                UsuarioRepository.dadosPersistidos.comunidades = comunidades;
                System.err.println("Erro ao carregar dados, iniciando com mapa vazio: " + e.getMessage());
            }
        } else if (comunidades == null) {
            comunidades = new LinkedHashMap<>();
            UsuarioRepository.dadosPersistidos.comunidades = comunidades;
        }
    }
}
