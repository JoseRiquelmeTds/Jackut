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

    public void adicionarAmigo(String idSessao, String amigo) throws UsuarioNaoCadastradoException, AutoAdicaoAmigoException, AmigoJaAdicionadoException, AmigoEsperandoAceitacaoException, FuncaoInvalidaException {
        String loginRemetente = sessaoService.obterLoginPorSessao(idSessao);

        if (amigo == null || amigo.trim().isEmpty() || !usuarioRepository.existe(amigo)) {
            throw new UsuarioNaoCadastradoException();
        }

        if (loginRemetente.equals(amigo)) {
            throw new AutoAdicaoAmigoException();
        }

        Usuario remetente = usuarioRepository.buscarPorLogin(loginRemetente);
        Usuario destinatario = usuarioRepository.buscarPorLogin(amigo);

        if (destinatario.ehInimigoDe(loginRemetente)) {
            throw new FuncaoInvalidaException(destinatario.getNome() + " \u00e9 seu inimigo.");
        }

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

    public void adicionarIdolo(String idSessao, String idolo)
            throws UsuarioNaoCadastradoException, UsuarioJaAdicionadoComoIdoloException, UsuarioNaoPodeSerIdoloDeSiMesmoException, FuncaoInvalidaException {
        String login = sessaoService.obterLoginPorSessao(idSessao);
        Usuario usuario = usuarioRepository.buscarPorLogin(login);
        if (usuario == null || idolo == null || idolo.trim().isEmpty() || !usuarioRepository.existe(idolo)) {
            throw new UsuarioNaoCadastradoException();
        }
        if (login.equals(idolo)) {
            throw new UsuarioNaoPodeSerIdoloDeSiMesmoException();
        }
        Usuario alvo = usuarioRepository.buscarPorLogin(idolo);
        if (alvo.ehInimigoDe(login)) {
            throw new FuncaoInvalidaException(alvo.getNome() + " \u00e9 seu inimigo.");
        }
        if (usuario.ehIdoloDe(idolo)) {
            throw new UsuarioJaAdicionadoComoIdoloException();
        }
        usuario.adicionarIdolo(idolo);
        alvo.adicionarFa(login);
    }

    public boolean ehFa(String login, String idolo) {
        Usuario usuario = usuarioRepository.buscarPorLogin(login);
        if (usuario == null) {
            return false;
        }
        return usuario.ehIdoloDe(idolo);
    }

    public String getFas(String login) {
        Usuario usuario = usuarioRepository.buscarPorLogin(login);
        if (usuario == null) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        for (String fa : usuario.getFas()) {
            sb.append(fa).append(",");
        }
        if (!usuario.getFas().isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }

    public void adicionarPaquera(String idSessao, String paquera)
            throws UsuarioNaoCadastradoException, UsuarioJaAdicionadoComoPaqueraException, UsuarioNaoPodeSerPaqueraDeSiMesmoException, FuncaoInvalidaException {
        String login = sessaoService.obterLoginPorSessao(idSessao);
        Usuario usuario = usuarioRepository.buscarPorLogin(login);
        if (usuario == null || paquera == null || paquera.trim().isEmpty() || !usuarioRepository.existe(paquera)) {
            throw new UsuarioNaoCadastradoException();
        }
        if (login.equals(paquera)) {
            throw new UsuarioNaoPodeSerPaqueraDeSiMesmoException();
        }
        Usuario alvo = usuarioRepository.buscarPorLogin(paquera);
        if (alvo.ehInimigoDe(login)) {
            throw new FuncaoInvalidaException(alvo.getNome() + " \u00e9 seu inimigo.");
        }
        if (usuario.ehPaqueraDe(paquera)) {
            throw new UsuarioJaAdicionadoComoPaqueraException();
        }

        boolean mutual = alvo.ehPaqueraDe(login);
        usuario.adicionarPaquera(paquera);

        if (mutual) {
            usuario.receberRecado("jackut", alvo.getNome() + " \u00e9 seu paquera - Recado do Jackut.");
            alvo.receberRecado("jackut", usuario.getNome() + " \u00e9 seu paquera - Recado do Jackut.");
        }
    }

    public boolean ehPaquera(String idSessao, String paquera) throws UsuarioNaoCadastradoException {
        String login = sessaoService.obterLoginPorSessao(idSessao);
        Usuario usuario = usuarioRepository.buscarPorLogin(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        return usuario.ehPaqueraDe(paquera);
    }

    public String getPaqueras(String idSessao) throws UsuarioNaoCadastradoException {
        String login = sessaoService.obterLoginPorSessao(idSessao);
        Usuario usuario = usuarioRepository.buscarPorLogin(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        StringBuilder sb = new StringBuilder("{");
        for (String paquera : usuario.getPaqueras()) {
            sb.append(paquera).append(",");
        }
        if (!usuario.getPaqueras().isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }

    public void adicionarInimigo(String idSessao, String inimigo)
            throws UsuarioNaoCadastradoException, UsuarioJaAdicionadoComoInimigoException, UsuarioNaoPodeSerInimigoDeSiMesmoException {
        String login = sessaoService.obterLoginPorSessao(idSessao);
        Usuario usuario = usuarioRepository.buscarPorLogin(login);
        if (usuario == null || inimigo == null || inimigo.trim().isEmpty() || !usuarioRepository.existe(inimigo)) {
            throw new UsuarioNaoCadastradoException();
        }
        if (login.equals(inimigo)) {
            throw new UsuarioNaoPodeSerInimigoDeSiMesmoException();
        }
        if (usuario.ehInimigoDe(inimigo)) {
            throw new UsuarioJaAdicionadoComoInimigoException();
        }
        Usuario alvo = usuarioRepository.buscarPorLogin(inimigo);
        usuario.adicionarInimigo(inimigo);
        alvo.adicionarInimigo(login);
    }
}
