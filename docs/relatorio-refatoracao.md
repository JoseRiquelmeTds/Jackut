# Relatório de Refatoração e Ajustes de Design - Jackut

Este documento detalha **exatamente** todas as alterações realizadas no projeto Jackut para solucionar os problemas de design de software e arquitetura apontados no feedback da avaliação.

---

## 1. Contexto e Objetivos

O projeto passou por uma refatoração profunda para sanar as seguintes penalidades aplicadas no feedback:
* **Segunda Facade e God Class (-2,0 pontos)**: A classe `SistemaJackut` concentrava lógica de negócio, sessões, persistência e formatação de dados.
* **Exceções Inadequadas (-0,5 pontos)**: Uso genérico de `throws Exception`, exceções customizadas sem implementação adequada e presença de `printStackTrace`.
* **Encapsulamento e Modularidade (-0,5 pontos)**: Exposição direta das coleções internas (`amigos` e `convitesEnviados`) da classe `Usuario`.

---

## 2. Detalhamento de Todas as Alterações

### A. Divisão da God Class `SistemaJackut` (SRP e Desacoplamento)
A classe `SistemaJackut` foi **completamente removida**. Suas responsabilidades foram divididas em novas classes no pacote `br.ufal.ic.jackut.repository` (persistência) e `br.ufal.ic.jackut.service` (lógica de aplicação):

1. **[UsuarioRepository](file:///Ubuntu/home/riquelme/codigos/Jackut/src/br/ufal/ic/jackut/repository/UsuarioRepository.java) [NOVO]**:
   * Responsável exclusivo por carregar e salvar a base de dados de usuários no arquivo local `jackut.dat`.
   * Encapsula o mapa de usuários cadastrados em memória (`Map<String, Usuario>`).
   * Substituiu o uso de `e.printStackTrace()` nas falhas de E/S por impressões informativas limpas em `System.err.println()`.
2. **[SessaoService](file:///Ubuntu/home/riquelme/codigos/Jackut/src/br/ufal/ic/jackut/service/SessaoService.java) [NOVO]**:
   * Gerencia o mapa de sessões ativas (`Map<String, String> sessoesAtivas`).
   * Fornece validação de login/senha e geração de IDs de sessão (`UUID`).
3. **[UsuarioService](file:///Ubuntu/home/riquelme/codigos/Jackut/src/br/ufal/ic/jackut/service/UsuarioService.java) [NOVO]**:
   * Centraliza a criação de novos usuários e a leitura/escrita de atributos no perfil dinâmico de cada usuário.
4. **[AmizadeService](file:///Ubuntu/home/riquelme/codigos/Jackut/src/br/ufal/ic/jackut/service/AmizadeService.java) [NOVO]**:
   * Concentra as regras para adicionar amigos, validar convites recíprocos e verificar se dois usuários são amigos.
   * Contém a lógica de formatação que gera a representação textual da lista de amigos no padrão `{amigo1,amigo2}`.
5. **[RecadoService](file:///Ubuntu/home/riquelme/codigos/Jackut/src/br/ufal/ic/jackut/service/RecadoService.java) [NOVO]**:
   * Contém as validações e entrega de recados de texto no mural dos destinatários, bem como a leitura do próximo recado da fila.

---

### B. Refatoração da Fachada `Facade`
A classe **[Facade](file:///Ubuntu/home/riquelme/codigos/Jackut/src/br/ufal/ic/jackut/Facade.java)** foi totalmente reescrita para deixar de ser um mero redirecionador para uma segunda fachada (`SistemaJackut`):
* Agora, atua como o ponto único de entrada do subsistema, instanciando os serviços (`UsuarioService`, `SessaoService`, etc.) e delegando as chamadas diretamente a eles.
* Todas as assinaturas de métodos foram alteradas para remover `throws Exception`. Agora declaram **explicitamente** as exceções customizadas lançadas (ex: `throws ContaJaExisteException, LoginInvalidoException...`).

---

### C. Melhoria no Encapsulamento de `Usuario`
A classe **[Usuario](file:///Ubuntu/home/riquelme/codigos/Jackut/src/br/ufal/ic/jackut/model/Usuario.java)** foi alterada para não expor suas coleções internas diretamente:
* **Retorno Imutável**: Os métodos `getAmigos()` e `getConvitesEnviados()` passaram a retornar uma view imutável das coleções usando `Collections.unmodifiableSet(amigos)` e `Collections.unmodifiableSet(convitesEnviados)`.
* **Métodos de Domínio Encapsulados**: Adicionou-se métodos para modificação controlada do estado interno do objeto `Usuario`, impedindo manipulações externas diretas:
  * `boolean temConviteEnviadoPara(String loginAmigo)`
  * `void enviarConvitePara(String loginAmigo)`
  * `void removerConviteDe(String loginAmigo)`
  * `void adicionarAmigo(String loginAmigo)`
  * `boolean ehAmigoDe(String loginAmigo)`

---

### D. Correção das Exceções Customizadas
Para corrigir a penalidade de "exceções sem implementação adequada":
1. **[JackutException](file:///Ubuntu/home/riquelme/codigos/Jackut/src/br/ufal/ic/jackut/exception/JackutException.java) [NOVO]**:
   * Criada como a classe base para todas as exceções de negócio do sistema (estendendo `Exception`). Define `serialVersionUID = 1L` e implementa os 4 construtores padrão da linguagem Java.
2. **Atualização das Exceções Customizadas**:
   * Todas as 14 exceções existentes no pacote `br.ufal.ic.jackut.exception` foram reescritas para herdar de `JackutException`.
   * Cada uma delas agora possui o identificador de versão de serialização `private static final long serialVersionUID = 1L;` e os 4 construtores padrão que suportam mensagens customizadas e causas de exceção encadeadas.

---

## 3. Resumo de Arquivos Modificados e Criados

```
Jackut/
├── docs/
│   └── relatorio-refatoracao.md            [NOVO] Este documento explicativo
└── src/
    └── br/ufal/ic/jackut/
        ├── Facade.java                     [MODIFICADO] Fachada delegando para serviços e sem throws Exception genérico
        ├── controller/
        │   └── SistemaJackut.java          [DELETADO] God class removida do sistema
        ├── repository/
        │   └── UsuarioRepository.java      [NOVO] Classe de persistência e encapsulamento de dados
        ├── service/
        │   ├── SessaoService.java          [NOVO] Gerenciador do ciclo de vida das sessões
        │   ├── UsuarioService.java         [NOVO] Lógica de registro e atualização de perfis
        │   ├── AmizadeService.java         [NOVO] Lógica e formatação de amizades
        │   └── RecadoService.java          [NOVO] Envio e leitura de recados no mural
        ├── model/
        │   └── Usuario.java                [MODIFICADO] Encapsulamento completo com Collections.unmodifiableSet
        └── exception/
            ├── JackutException.java        [NOVO] Exceção base do sistema
            ├── AmigoEsperandoAceitacao...  [MODIFICADO] Herda de JackutException, com construtores padrão e serialVersionUID
            ├── AmigoJaAdicionadoException  [MODIFICADO] Herda de JackutException, com construtores padrão e serialVersionUID
            ├── AtributoNaoPreenchidoEx...  [MODIFICADO] Herda de JackutException, com construtores padrão e serialVersionUID
            ├── AutoAdicaoAmigoException    [MODIFICADO] Herda de JackutException, com construtores padrão e serialVersionUID
            ├── AutoEnvioRecadoException    [MODIFICADO] Herda de JackutException, com construtores padrão e serialVersionUID
            ├── ContaJaExisteException      [MODIFICADO] Herda de JackutException, com construtores padrão e serialVersionUID
            ├── LoginInvalidoException      [MODIFICADO] Herda de JackutException, com construtores padrão e serialVersionUID
            ├── LoginOuSenhaInvalidoEx...   [MODIFICADO] Herda de JackutException, com construtores padrão e serialVersionUID
            ├── NaoHaRecadosException       [MODIFICADO] Herda de JackutException, com construtores padrão e serialVersionUID
            ├── PerfilNaoCriadoException    [MODIFICADO] Herda de JackutException, com construtores padrão e serialVersionUID
            ├── SenhaInvalidaException      [MODIFICADO] Herda de JackutException, com construtores padrão e serialVersionUID
            ├── SessaoInvalidaException     [MODIFICADO] Herda de JackutException, com construtores padrão e serialVersionUID
            ├── UsuarioNaoCadastradoException [MODIFICADO] Herda de JackutException, com construtores padrão e serialVersionUID
            └── UsuarioNaoEncontradoException [MODIFICADO] Herda de JackutException, com construtores padrão e serialVersionUID
```

---

## 4. Validação dos Testes de Aceitação

A conformidade do sistema foi testada com a suite EasyAccept do projeto. Para isso, o sistema foi compilado em UTF-8 e a JVM foi configurada com `-Dfile.encoding=ISO-8859-1` para processamento correto dos acentos dos scripts de teste:

```
Test file src/tests/us1_1.txt: 17 tests OK
Test file src/tests/us1_2.txt: 7 tests OK
Test file src/tests/us2_1.txt: 36 tests OK
Test file src/tests/us2_2.txt: 13 tests OK
Test file src/tests/us3_1.txt: 46 tests OK
Test file src/tests/us3_2.txt: 10 tests OK
Test file src/tests/us4_1.txt: 42 tests OK
Test file src/tests/us4_2.txt: 13 tests OK
```

Todos os **184 testes de aceitação** passaram com **100% de sucesso**.
