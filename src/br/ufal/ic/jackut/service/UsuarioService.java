package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.*;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.UsuarioRepository;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final SessaoService sessaoService;

    public UsuarioService(UsuarioRepository usuarioRepository, SessaoService sessaoService) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoService = sessaoService;
    }

    public void criarUsuario(String login, String senha, String nome) throws ContaJaExisteException, LoginInvalidoException, SenhaInvalidaException {
        if (usuarioRepository.existe(login)) {
            throw new ContaJaExisteException();
        }
        Usuario novoUsuario = new Usuario(login, senha, nome);
        usuarioRepository.salvar(novoUsuario);
    }

    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
        Usuario usuario = usuarioRepository.buscarPorLogin(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        return usuario.getAtributoPerfil(atributo);
    }

    public void editarPerfil(String idSessao, String atributo, String valor) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
        String loginUsuario = sessaoService.obterLoginPorSessao(idSessao);

        if (atributo == null || atributo.trim().isEmpty() || valor == null || valor.trim().isEmpty()) {
            throw new AtributoNaoPreenchidoException();
        }

        Usuario usuario = usuarioRepository.buscarPorLogin(loginUsuario);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        usuario.alterarPerfil(atributo, valor);
    }
}
