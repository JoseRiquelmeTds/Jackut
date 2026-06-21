package br.ufal.ic.jackut;

import br.ufal.ic.jackut.exception.*;
import br.ufal.ic.jackut.repository.UsuarioRepository;
import br.ufal.ic.jackut.service.*;

public class Facade {
    private final UsuarioRepository usuarioRepository = new UsuarioRepository();
    private final SessaoService sessaoService = new SessaoService(usuarioRepository);
    private final UsuarioService usuarioService = new UsuarioService(usuarioRepository, sessaoService);
    private final AmizadeService amizadeService = new AmizadeService(usuarioRepository, sessaoService);
    private final RecadoService recadoService = new RecadoService(usuarioRepository, sessaoService);

    public void zerarSistema() {
        usuarioRepository.limpar();
        sessaoService.limparSessoes();
    }

    public void criarUsuario(String login, String senha, String nome)
            throws ContaJaExisteException, LoginInvalidoException, SenhaInvalidaException {
        usuarioService.criarUsuario(login, senha, nome);
    }

    public String abrirSessao(String login, String senha)
            throws LoginOuSenhaInvalidoException {
        return sessaoService.abrirSessao(login, senha);
    }

    public String getAtributoUsuario(String login, String atributo)
            throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
        return usuarioService.getAtributoUsuario(login, atributo);
    }

    public void editarPerfil(String idSessao, String atributo, String valor)
            throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
        usuarioService.editarPerfil(idSessao, atributo, valor);
    }

    public void encerrarSistema() {
        usuarioRepository.salvarDados();
    }

    public void adicionarAmigo(String idSessao, String amigo)
            throws UsuarioNaoCadastradoException, AutoAdicaoAmigoException, AmigoJaAdicionadoException, AmigoEsperandoAceitacaoException {
        amizadeService.adicionarAmigo(idSessao, amigo);
    }

    public boolean ehAmigo(String login, String amigo) {
        return amizadeService.ehAmigo(login, amigo);
    }

    public String getAmigos(String login) {
        return amizadeService.getAmigos(login);
    }

    public void enviarRecado(String idSessao, String destinatario, String recado)
            throws UsuarioNaoCadastradoException, AutoEnvioRecadoException {
        recadoService.enviarRecado(idSessao, destinatario, recado);
    }

    public String lerRecado(String idSessao)
            throws NaoHaRecadosException, UsuarioNaoCadastradoException {
        return recadoService.lerRecado(idSessao);
    }
}