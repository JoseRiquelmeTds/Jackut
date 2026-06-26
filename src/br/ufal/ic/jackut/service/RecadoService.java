package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.AutoEnvioRecadoException;
import br.ufal.ic.jackut.exception.FuncaoInvalidaException;
import br.ufal.ic.jackut.exception.NaoHaRecadosException;
import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.UsuarioRepository;

public class RecadoService {
    private final UsuarioRepository usuarioRepository;
    private final SessaoService sessaoService;

    public RecadoService(UsuarioRepository usuarioRepository, SessaoService sessaoService) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoService = sessaoService;
    }

    public void enviarRecado(String idSessao, String loginDestinatario, String textoRecado)
            throws UsuarioNaoCadastradoException, AutoEnvioRecadoException, FuncaoInvalidaException {
        String loginRemetente = sessaoService.obterLoginPorSessao(idSessao);

        Usuario destinatario = usuarioRepository.buscarPorLogin(loginDestinatario);
        if (destinatario == null) {
            throw new UsuarioNaoCadastradoException();
        }

        if (loginRemetente.equals(loginDestinatario)) {
            throw new AutoEnvioRecadoException();
        }

        if (destinatario.ehInimigoDe(loginRemetente)) {
            throw new FuncaoInvalidaException(destinatario.getNome() + " \u00e9 seu inimigo.");
        }

        destinatario.receberRecado(loginRemetente, textoRecado);
    }

    public String lerRecado(String idSessao) throws NaoHaRecadosException, UsuarioNaoCadastradoException {
        String loginUsuario = sessaoService.obterLoginPorSessao(idSessao);

        Usuario usuario = usuarioRepository.buscarPorLogin(loginUsuario);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }

        return usuario.lerProximoRecado();
    }
}
