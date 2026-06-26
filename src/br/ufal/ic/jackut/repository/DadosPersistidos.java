package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.model.Comunidade;
import br.ufal.ic.jackut.model.Usuario;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class DadosPersistidos implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Usuario> usuarios = new HashMap<>();
    Map<String, Comunidade> comunidades = new HashMap<>();
}
