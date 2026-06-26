package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.ComunidadeJaExisteException;
import br.ufal.ic.jackut.exception.ComunidadeNaoExisteException;
import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.model.Comunidade;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.ComunidadeRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;

import java.util.Set;

public class ComunidadeService {
    private final ComunidadeRepository comunidadeRepository;
    private final UsuarioRepository usuarioRepository;
    private final SessaoService sessaoService;

    public ComunidadeService(ComunidadeRepository comunidadeRepository, UsuarioRepository usuarioRepository, SessaoService sessaoService) {
        this.comunidadeRepository = comunidadeRepository;
        this.usuarioRepository = usuarioRepository;
        this.sessaoService = sessaoService;
    }

    public void criarComunidade(String idSessao, String nome, String descricao)
            throws UsuarioNaoCadastradoException, ComunidadeJaExisteException {
        String login = sessaoService.obterLoginPorSessao(idSessao);
        Usuario dono = usuarioRepository.buscarPorLogin(login);
        if (dono == null) {
            throw new UsuarioNaoCadastradoException();
        }
        if (nome == null || nome.trim().isEmpty() || comunidadeRepository.existe(nome)) {
            throw new ComunidadeJaExisteException();
        }
        comunidadeRepository.salvar(new Comunidade(nome, descricao, login));
    }

    public String getDescricaoComunidade(String nome) throws ComunidadeNaoExisteException {
        Comunidade comunidade = comunidadeRepository.buscarPorNome(nome);
        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }
        return comunidade.getDescricao();
    }

    public String getDonoComunidade(String nome) throws ComunidadeNaoExisteException {
        Comunidade comunidade = comunidadeRepository.buscarPorNome(nome);
        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }
        return comunidade.getDono();
    }

    public String getMembrosComunidade(String nome) throws ComunidadeNaoExisteException {
        Comunidade comunidade = comunidadeRepository.buscarPorNome(nome);
        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }
        Set<String> membros = comunidade.getMembros();
        StringBuilder sb = new StringBuilder("{");
        for (String membro : membros) {
            sb.append(membro).append(",");
        }
        if (!membros.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }
}
