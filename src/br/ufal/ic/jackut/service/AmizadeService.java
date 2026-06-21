package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.*;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.UsuarioRepository;
import java.util.Set;

public class AmizadeService {
    private final UsuarioRepository usuarioRepository;
    private final SessaoService sessaoService;

    public AmizadeService(UsuarioRepository usuarioRepository, SessaoService sessaoService) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoService = sessaoService;
    }

    public void adicionarAmigo(String idSessao, String amigo) throws UsuarioNaoCadastradoException, AutoAdicaoAmigoException, AmigoJaAdicionadoException, AmigoEsperandoAceitacaoException {
        String loginRemetente = sessaoService.obterLoginPorSessao(idSessao);

        if (amigo == null || amigo.trim().isEmpty() || !usuarioRepository.existe(amigo)) {
            throw new UsuarioNaoCadastradoException();
        }

        if (loginRemetente.equals(amigo)) {
            throw new AutoAdicaoAmigoException();
        }

        Usuario remetente = usuarioRepository.buscarPorLogin(loginRemetente);
        Usuario destinatario = usuarioRepository.buscarPorLogin(amigo);

        if (remetente.ehAmigoDe(amigo)) {
            throw new AmigoJaAdicionadoException();
        }

        if (remetente.temConviteEnviadoPara(amigo)) {
            throw new AmigoEsperandoAceitacaoException();
        }

        if (destinatario.temConviteEnviadoPara(loginRemetente)) {
            destinatario.removerConviteDe(loginRemetente);
            remetente.adicionarAmigo(amigo);
            destinatario.adicionarAmigo(loginRemetente);
        } else {
            remetente.enviarConvitePara(amigo);
        }
    }

    public boolean ehAmigo(String login, String amigo) {
        Usuario usuario = usuarioRepository.buscarPorLogin(login);
        if (usuario == null) {
            return false;
        }
        return usuario.ehAmigoDe(amigo);
    }

    public String getAmigos(String login) {
        Usuario usuario = usuarioRepository.buscarPorLogin(login);
        if (usuario == null) {
            return "{}";
        }

        Set<String> listaAmigos = usuario.getAmigos();
        if (listaAmigos.isEmpty()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder("{");
        for (String nomeAmigo : listaAmigos) {
            sb.append(nomeAmigo).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");

        return sb.toString();
    }
}
