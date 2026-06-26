package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.LoginOuSenhaInvalidoException;
import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.UsuarioRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessaoService {
    private final UsuarioRepository usuarioRepository;
    private final Map<String, String> sessoesAtivas;

    public SessaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessoesAtivas = new HashMap<>();
    }

    public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException {
        Usuario usuario = usuarioRepository.buscarPorLogin(login);
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            throw new LoginOuSenhaInvalidoException();
        }

        String idSessao = UUID.randomUUID().toString();
        sessoesAtivas.put(idSessao, login);
        return idSessao;
    }

    public String obterLoginPorSessao(String idSessao) throws UsuarioNaoCadastradoException {
        if (idSessao == null || idSessao.trim().isEmpty() || !sessoesAtivas.containsKey(idSessao)) {
            throw new UsuarioNaoCadastradoException();
        }
        return sessoesAtivas.get(idSessao);
    }

    public void limparSessoes() {
        sessoesAtivas.clear();
    }

    public void removerSessaoDoUsuario(String login) {
        String sessaoParaRemover = null;
        for (Map.Entry<String, String> entry : sessoesAtivas.entrySet()) {
            if (entry.getValue().equals(login)) {
                sessaoParaRemover = entry.getKey();
                break;
            }
        }
        if (sessaoParaRemover != null) {
            sessoesAtivas.remove(sessaoParaRemover);
        }
    }
}
