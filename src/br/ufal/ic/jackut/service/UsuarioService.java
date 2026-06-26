package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.*;
import br.ufal.ic.jackut.model.Comunidade;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.ComunidadeRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final SessaoService sessaoService;
    private final ComunidadeRepository comunidadeRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, SessaoService sessaoService, ComunidadeRepository comunidadeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoService = sessaoService;
        this.comunidadeRepository = comunidadeRepository;
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

    public void removerUsuario(String idSessao) throws UsuarioNaoCadastradoException {
        String loginUsuario = sessaoService.obterLoginPorSessao(idSessao);
        Usuario usuario = usuarioRepository.buscarPorLogin(loginUsuario);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }

        for (Usuario outro : usuarioRepository.todos()) {
            if (!outro.getLogin().equals(loginUsuario)) {
                outro.removerAmigo(loginUsuario);
                outro.removerConviteEnviadoPara(loginUsuario);
                outro.removerFa(loginUsuario);
                outro.removerIdolo(loginUsuario);
                outro.removerPaquera(loginUsuario);
                outro.removerInimigo(loginUsuario);
                outro.removerRecadosDe(loginUsuario);
            }
        }

        for (Comunidade comunidade : comunidadeRepository.todas().values()) {
            comunidade.removerMembro(loginUsuario);
            usuario.removerComunidade(comunidade.getNome());
        }

        List<String> comunidadesParaRemover = new ArrayList<>();
        for (Comunidade comunidade : comunidadeRepository.todas().values()) {
            if (comunidade.getDono().equals(loginUsuario)) {
                comunidadesParaRemover.add(comunidade.getNome());
                for (String membro : comunidade.getMembros()) {
                    Usuario usuarioMembro = usuarioRepository.buscarPorLogin(membro);
                    if (usuarioMembro != null) {
                        usuarioMembro.removerComunidade(comunidade.getNome());
                    }
                }
            }
        }
        for (String nomeComunidade : comunidadesParaRemover) {
            comunidadeRepository.remover(nomeComunidade);
        }

        usuarioRepository.remover(loginUsuario);
        sessaoService.limparSessoes();
    }
}
