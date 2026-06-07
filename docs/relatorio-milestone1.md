# RELATÓRIO - MILESTONE 1 - JACKUT

## Rede de Relacionamentos Jackut

---

## 1. INTRODUÇÃO

Este relatório descreve o design e implementação do primeiro milestone do projeto Jackut, um sistema de rede de relacionamentos desenvolvido em Java. O projeto implementa as User Stories 1 a 4, fornecendo funcionalidades de:

- **US1**: Criação de contas de usuários
- **US2**: Criação e edição de perfis de usuários
- **US3**: Adição de amigos com sistema de convites
- **US4**: Envio e leitura de recados entre usuários

---

## 2. ARQUITETURA E DESIGN

### 2.1 Padrões de Projeto Utilizados

#### **2.1.1 Padrão Facade**

O projeto implementa o **padrão Facade** como seu principal padrão arquitetural. A classe `Facade` (no pacote `br.ufal.ic.jackut`) atua como uma interface única simplificada para toda a complexidade do sistema de negócio, encapsulando a classe `SistemaJackut`.

**Benefícios da utilização:**
- Simplifica o acesso à lógica de negócio
- Fornece uma interface consistente para os testes de aceitação (EasyAccept)
- Desacopla a interface pública da implementação interna
- Facilita futuras alterações na implementação sem afetar os testes

```java
public class Facade {
    private SistemaJackut sistema = new SistemaJackut();
    // Delegação de métodos...
}
```

#### **2.1.2 Padrão Model-View-Controller (MVC)**

Embora o projeto não possua uma visão (View) implementada, foi adotada a separação entre:

- **Model** (`br.ufal.ic.jackut.model`): Classe `Usuario` que representa a entidade de negócio
- **Controller** (`br.ufal.ic.jackut.controller`): Classe `SistemaJackut` que orquestra a lógica de negócio
- **Facade** (`br.ufal.ic.jackut`): Atua como intermediária para acessar o controlador

Esta separação garante responsabilidades bem definidas e facilita testes e manutenção.

#### **2.1.3 Padrão de Persistência com Serialização**

O sistema utiliza **serialização Java** para persistência de dados. O objeto `Map<String, Usuario>` é serializado em arquivo (`jackut.dat`) no método `encerrarSistema()`.

**Características:**
- A classe `Usuario` implementa `Serializable`
- Usa `ObjectOutputStream` e `ObjectInputStream` para salvar/carregar dados
- Persistência automática ao encerrar o sistema
- Carregamento automático ao inicializar

### 2.2 Estrutura de Pacotes

```
br.ufal.ic.jackut/
??? Facade.java                          # Interface pública (Facade pattern)
??? Main.java                            # Ponto de entrada (testes EasyAccept)
??? controller/
?   ??? SistemaJackut.java               # Lógica de negócio (Controller)
??? model/
?   ??? Usuario.java                     # Entidade de domínio (Model)
??? exception/
    ??? AmigoEsperandoAceitacaoException.java
    ??? AmigoJaAdicionadoException.java
    ??? AtributoNaoPreenchidoException.java
    ??? AutoAdicaoAmigoException.java
    ??? AutoEnvioRecadoException.java
    ??? ContaJaExisteException.java
    ??? LoginInvalidoException.java
    ??? LoginOuSenhaInvalidoException.java
    ??? NaoHaRecadosException.java
    ??? PerfilNaoCriadoException.java
    ??? SenhaInvalidaException.java
    ??? SessaoInvalidaException.java
    ??? UsuarioNaoCadastradoException.java
    ??? UsuarioNaoEncontradoException.java
```

### 2.3 Responsabilidades das Classes Principais

#### **Facade**
- Ponto de entrada para os testes de aceitação
- Delega todos os métodos para `SistemaJackut`
- Mantém encapsulamento da complexidade interna

#### **SistemaJackut (Controller)**
- Gerencia o conjunto de usuários (HashMap)
- Gerencia as sessões ativas (HashMap)
- Implementa a lógica de negócio de todas as User Stories
- Orquestra as operações entre objetos `Usuario`
- Responsável por persistência de dados

**Principais responsabilidades:**
- Criar usuários e validar dados de entrada
- Gerenciar sessões ativas com IDs únicos (UUID)
- Adicionar amigos com sistema de convites bidirecional
- Enviar recados para usuários
- Persistir/carregar dados do arquivo

#### **Usuario (Model)**
- Representa um usuário do sistema
- Armazena dados pessoais (login, senha, nome)
- Mantém perfil dinâmico (Map de atributos)
- Gerencia lista de amigos e convites enviados
- Gerencia fila de recados (LinkedList)

**Principais responsabilidades:**
- Validar login e senha na construção
- Fornecer acesso a atributos do perfil
- Receber e ler recados em ordem FIFO

---

## 3. DIAGRAMA DE CLASSES

```
???????????????????????????????????????????????????????????????????
?                      br.ufal.ic.jackut                          ?
???????????????????????????????????????????????????????????????????
?                          <<Facade>>                             ?
?                        + Facade()                               ?
?                      - sistema: SistemaJackut                   ?
???????????????????????????????????????????????????????????????????
? + zerarSistema(): void                                          ?
? + criarUsuario(login, senha, nome): void                        ?
? + abrirSessao(login, senha): String                             ?
? + getAtributoUsuario(login, atributo): String                   ?
? + editarPerfil(idSessao, atributo, valor): void                 ?
? + adicionarAmigo(id, amigo): void                               ?
? + ehAmigo(login, amigo): boolean                                ?
? + getAmigos(login): String                                      ?
? + enviarRecado(id, destinatario, recado): void                  ?
? + lerRecado(id): String                                         ?
? + encerrarSistema(): void                                       ?
???????????????????????????????????????????????????????????????????
                              ?
                              ? utiliza
                              ?
???????????????????????????????????????????????????????????????????
?             br.ufal.ic.jackut.controller                        ?
???????????????????????????????????????????????????????????????????
?                      <<Controller>>                             ?
?                    + SistemaJackut()                            ?
?    - usuarios: Map<String, Usuario>                             ?
?    - sessoesAtivas: Map<String, String>                         ?
?    - ARQUIVO_DADOS: String = "jackut.dat"                       ?
???????????????????????????????????????????????????????????????????
? + criarUsuario(login, senha, nome): void                        ?
? + abrirSessao(login, senha): String                             ?
? + getAtributoUsuario(login, atributo): String                   ?
? + zerarSistema(): void                                          ?
? + encerrarSistema(): void                                       ?
? + editarPerfil(idSessao, atributo, valor): void                 ?
? + adicionarAmigo(idSessao, amigo): void                         ?
? + ehAmigo(login, amigo): boolean                                ?
? + getAmigos(login): String                                      ?
? + enviarRecado(idSessao, destinatario, recado): void            ?
? + lerRecado(idSessao): String                                   ?
? - salvarDados(): void                                           ?
? - carregarDados(): void                                         ?
???????????????????????????????????????????????????????????????????
                              ?
                              ? gerencia
                              ? (1..*)
                              ?
???????????????????????????????????????????????????????????????????
?                br.ufal.ic.jackut.model                          ?
???????????????????????????????????????????????????????????????????
?                       <<Entity>>                                ?
?                      + Usuario()                                ?
?    - login: String                                              ?
?    - senha: String                                              ?
?    - nome: String                                               ?
?    - perfil: Map<String, String>                                ?
?    - amigos: Set<String>                                        ?
?    - convitesEnviados: Set<String>                              ?
?    - muralDeRecados: Queue<String>                              ?
???????????????????????????????????????????????????????????????????
? + getAtributoPerfil(atributo): String                           ?
? + alterarPerfil(atributo, valor): void                          ?
? + getAmigos(): Set<String>                                      ?
? + getConvitesEnviados(): Set<String>                            ?
? + receberRecado(recado): void                                   ?
? + lerProximoRecado(): String                                    ?
? + getLogin(): String                                            ?
? + getSenha(): String                                            ?
? + getNome(): String                                             ?
???????????????????????????????????????????????????????????????????

???????????????????????????????????????????????????????????????????
?              br.ufal.ic.jackut.exception                        ?
???????????????????????????????????????????????????????????????????
? ? AmigoEsperandoAceitacaoException                              ?
? ? AmigoJaAdicionadoException                                    ?
? ? AtributoNaoPreenchidoException                                ?
? ? AutoAdicaoAmigoException                                      ?
? ? AutoEnvioRecadoException                                      ?
? ? ContaJaExisteException                                        ?
? ? LoginInvalidoException                                        ?
? ? LoginOuSenhaInvalidoException                                 ?
? ? NaoHaRecadosException                                         ?
? ? PerfilNaoCriadoException                                      ?
? ? SenhaInvalidaException                                        ?
? ? SessaoInvalidaException                                       ?
? ? UsuarioNaoCadastradoException                                 ?
? ? UsuarioNaoEncontradoException                                 ?
???????????????????????????????????????????????????????????????????
```

---

## 4. DECISÕES DE DESIGN

### 4.1 Estrutura de Dados

#### **HashMap para Usuários**
```java
private Map<String, Usuario> usuarios;
```

**Justificativa:**
- Acesso O(1) por login do usuário
- Facilita busca rápida de usuários
- Adequado para o tamanho esperado de dados

#### **HashMap para Sessões Ativas**
```java
private Map<String, String> sessoesAtivas; // <idSessao, loginUsuario>
```

**Justificativa:**
- Rastreia sessões abertas
- UUID garante unicidade e segurança
- Permite múltiplas sessões simultâneas por usuário
- Facilita validação rápida de sessões

#### **Set<String> para Amigos**
```java
private Set<String> amigos;
private Set<String> convitesEnviados;
```

**Justificativa:**
- Amigos não podem ser duplicados
- Busca O(1) para verificar se é amigo
- LinkedHashSet mantém ordem de inserção

#### **Queue<String> para Recados**
```java
private java.util.Queue<String> muralDeRecados = new java.util.LinkedList<>();
```

**Justificativa:**
- Implementa padrão FIFO (First In, First Out)
- Recados são entregues na ordem de envio
- LinkedList oferece performance O(1) para operações

### 4.2 Perfil Dinâmico do Usuário

O perfil é implementado como um `HashMap<String, String>` que permite adicionar atributos dinamicamente:

```java
public void alterarPerfil(String atributo, String valor) {
    if ("nome".equalsIgnoreCase(atributo)) {
        this.nome = valor;
    } else {
        perfil.put(atributo, valor);
    }
}
```

**Justificativa:**
- Atende à US2 que permite modificar "qualquer atributo"
- Não requer mudanças na classe para adicionar novos atributos
- Nome é tratado como atributo especial (sempre existe)

### 4.3 Sistema de Convites Bidirecional

O sistema de amizade funciona com:
1. **Envio de convite**: Usuário A adiciona B ? B fica em `convitesEnviados` de A
2. **Aceitação**: Quando B adiciona A, o sistema detecta e confirma a amizade bilateralmente

```java
if (destinatario.getConvitesEnviados().contains(loginRemetente)) {
    // Amizade confirmada mutuamente
    destinatario.getConvitesEnviados().remove(loginRemetente);
    remetente.getAmigos().add(amigo);
    destinatario.getAmigos().add(loginRemetente);
} else {
    // Apenas envia convite
    remetente.getConvitesEnviados().add(amigo);
}
```

**Justificativa:**
- Implementa a regra "relacionamento só é efetivado quando o outro adicionar de volta"
- Evita necessidade de classe separada para Convite
- Simples e elegante

### 4.4 Validação de Segurança

#### **Sessões Validadas**
Operações que requerem autenticação validam a sessão:
```java
if (!sessoesAtivas.containsKey(idSessao)) {
    throw new UsuarioNaoCadastradoException();
}
```

#### **Login vs Senha**
Na autenticação, não se distingue qual dos dois é inválido por questão de segurança:
```java
if (usuario == null || !usuario.getSenha().equals(senha)) {
    throw new LoginOuSenhaInvalidoException();
}
```

**Justificativa:**
- Impede ataques de força bruta que descobrem logins válidos

### 4.5 Persistência

Dados são salvos em arquivo serializado:
- **Acionamento**: Ao chamar `encerrarSistema()`
- **Carregamento**: No construtor de `SistemaJackut`
- **Arquivo**: `jackut.dat` (relativo ao diretório de trabalho)

**Justificativa:**
- Simples de implementar com a API de serialização Java
- Adequado para um protótipo/MVP
- Testes podem limpar dados com `zerarSistema()`

---

## 5. FLUXOS DE NEGÓCIO

### 5.1 Criação de Usuário (US1)

```
criarUsuario(login, senha, nome)
    ??? Validar login (não vazio)
    ??? Validar senha (não vazia)
    ??? Verificar se login já existe
    ??? Criar novo Usuario(login, senha, nome)
    ??? Armazenar em HashMap<usuarios>
```

**Validações:**
- Login vazio ? LoginInvalidoException
- Senha vazia ? SenhaInvalidaException
- Login duplicado ? ContaJaExisteException

### 5.2 Adição de Amigos (US3)

```
adicionarAmigo(idSessao, amigo)
    ??? Validar sessão
    ??? Validar amigo existe
    ??? Validar não é auto-adição
    ??? Verificar se já é amigo
    ??? Verificar se já existe convite pendente
    ??? Buscar usuário destinatário
    ??? Se destinatário enviou convite para remetente:
    ?   ??? Confirmar amizade mutuamente
    ??? Senão:
        ??? Registrar convite em remetente.convitesEnviados
```

**Validações:**
- Sessão inválida ? UsuarioNaoCadastradoException
- Amigo não existe ? UsuarioNaoCadastradoException
- Auto-adição ? AutoAdicaoAmigoException
- Já é amigo ? AmigoJaAdicionadoException
- Convite pendente ? AmigoEsperandoAceitacaoException

### 5.3 Envio de Recados (US4)

```
enviarRecado(idSessao, destinatario, recado)
    ??? Validar sessão
    ??? Validar destinatário existe
    ??? Validar não é auto-envio
    ??? Adicionar recado à fila do destinatário
        usuario.receberRecado(recado)
```

**Validações:**
- Sessão inválida ? UsuarioNaoCadastradoException
- Destinatário não existe ? UsuarioNaoCadastradoException
- Auto-envio ? AutoEnvioRecadoException

### 5.4 Leitura de Recados (US4)

```
lerRecado(idSessao)
    ??? Validar sessão
    ??? Buscar usuário
    ??? Remover e retornar primeiro recado da fila
    ??? Se fila vazia: NaoHaRecadosException
```

---

## 6. TRATAMENTO DE EXCEÇÕES

O projeto utiliza exceções específicas para cada erro de negócio, permitindo testes precisos:

| Exceção | Mensagem | Quando Lançada |
|---------|----------|----------------|
| `ContaJaExisteException` | "Conta com esse nome já existe." | Login duplicado |
| `LoginInvalidoException` | "Login inválido." | Login vazio na criação |
| `SenhaInvalidaException` | "Senha inválida." | Senha vazia na criação |
| `LoginOuSenhaInvalidoException` | "Login ou senha inválidos." | Falha na autenticação |
| `UsuarioNaoCadastradoException` | "Usuário não cadastrado." | Sessão inválida, usuário não existe |
| `AtributoNaoPreenchidoException` | "Atributo não preenchido." | Atributo não existe ou vazio |
| `AutoAdicaoAmigoException` | "Usuário não pode adicionar a si mesmo como amigo." | Adição de auto-amigo |
| `AmigoJaAdicionadoException` | "Usuário já está adicionado como amigo." | Amigo já existe |
| `AmigoEsperandoAceitacaoException` | "Usuário já está adicionado como amigo, esperando aceitação do convite." | Convite pendente |
| `AutoEnvioRecadoException` | "Usuário não pode enviar recado para si mesmo." | Envio para si mesmo |
| `NaoHaRecadosException` | "Não há recados." | Leitura com fila vazia |

---

## 7. CONFORMIDADE COM REQUISITOS

### User Story 1 - Criação de Conta ?
- ? Criar usuário com login, senha e nome
- ? Validar login e senha
- ? Abrir sessão com autenticação
- ? Impedir contas duplicadas
- ? Recuperar atributos de usuários

### User Story 2 - Criação/Edição de Perfil ?
- ? Editar atributos de perfil
- ? Adicionar novos atributos dinamicamente
- ? Recuperar valores de atributos
- ? Tratamento de atributos não preenchidos

### User Story 3 - Adição de Amigos ?
- ? Adicionar amigos com envio de convite
- ? Confirmar amizade mutuamente
- ? Verificar se são amigos
- ? Listar amigos
- ? Validações de segurança (auto-adição, duplicatas, convites pendentes)

### User Story 4 - Envio de Recados ?
- ? Enviar recados a qualquer usuário
- ? Ler recados em ordem FIFO
- ? Validar que não é auto-envio
- ? Tratamento de fila vazia

---

## 8. QUALIDADE E BOAS PRÁTICAS

### 8.1 Separação de Responsabilidades
- **Facade**: Interface pública
- **SistemaJackut**: Orquestração e lógica de negócio
- **Usuario**: Entidade de domínio
- **Exceptions**: Erros de negócio

### 8.2 Encapsulamento
- Dados privados em todas as classes
- Getters para acesso controlado
- Operações através de métodos públicos bem definidos

### 8.3 Persistência
- Serialização automática ao encerrar
- Carregamento automático na inicialização
- Limpeza completa com `zerarSistema()`

### 8.4 Documentação
- Javadoc em classes e métodos principais
- Comentários explicativos em lógica complexa
- Nomes descritivos para variáveis e métodos

---

## 9. CONCLUSÃO

O design do Jackut (Milestone 1) segue princípios sólidos de engenharia de software:

1. **Arquitetura limpa** com separação clara de camadas (Facade-Controller-Model)
2. **Padrões de projeto** apropriados (Facade, estruturas de dados eficientes)
3. **Validação rigorosa** de regras de negócio
4. **Tratamento de erros** específico e controlado
5. **Persistência** simples e funcional
6. **Extensibilidade** para futuras User Stories

O sistema está pronto para os testes de aceitação e pode ser estendido facilmente para suportar as User Stories 5-8 nos próximos milestones, mantendo a arquitetura atual.

---

## 10. REFERÊNCIAS

- Documento do projeto Jackut (requisitos e user stories)
- Testes de aceitação: `src/tests/us1_*.txt`, `us2_*.txt`, `us3_*.txt`, `us4_*.txt`
- Framework de testes: EasyAccept

---

**Data de Elaboração:** Junho de 2026  
**Versão:** 1.0  
**Status:** Pronto para Milestone 1

