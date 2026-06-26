# RELATORIO - JACKUT - USER STORIES 5 A 9

## 1. Visao geral

Este relatorio descreve a evolucao do projeto Jackut apos a implementacao das User Stories 5 a 9. O sistema passou a suportar, alem das funcionalidades basicas de conta, perfil, amizade e recados, a criacao e participacao em comunidades, o envio de mensagens para comunidades, novos tipos de relacionamento e a remocao completa de conta.

Funcionalidades cobertas:

- US5: criacao de comunidades
- US6: adicao de usuarios a comunidades
- US7: envio de mensagens a comunidades
- US8: novos relacionamentos entre usuarios
- US9: remocao de conta e limpeza de informacoes

---

## 2. Estrutura da solucao

### 2.1 Organizacao em camadas

O projeto foi mantido com uma arquitetura simples e separada por responsabilidades:

- `Facade`: interface publica usada pelos testes de aceitacao
- `service`: regras de negocio e orquestracao
- `repository`: persistencia em memoria e em arquivo
- `model`: entidades de dominio
- `exception`: excecoes de negocio especificas

### 2.2 Persistencia

A persistencia continua baseada em serializacao Java no arquivo `jackut.dat`.

O formato passou a salvar:

- usuarios
- comunidades

Os dados sao carregados automaticamente na inicializacao e salvos ao encerrar o sistema.

---

## 3. User Story 5 - Criacao de comunidades

### 3.1 Requisito implementado

O usuario autenticado pode criar comunidades informando:

- nome
- descricao

### 3.2 Regras atendidas

- o nome da comunidade e a chave primaria
- nao pode haver duas comunidades com o mesmo nome
- duas comunidades podem compartilhar a mesma descricao
- o criador e o dono da comunidade
- o dono entra automaticamente como membro

### 3.3 Classes envolvidas

- `Comunidade`
- `ComunidadeRepository`
- `ComunidadeService`
- `Facade`

---

## 4. User Story 6 - Adicao de comunidades

### 4.1 Requisito implementado

O usuario pode se adicionar a uma comunidade existente.

### 4.2 Regras atendidas

- usuario nao pode entrar duas vezes na mesma comunidade
- comunidades inexistentes geram erro
- a lista de comunidades de cada usuario preserva a ordem de entrada

### 4.3 Classes envolvidas

- `Comunidade`
- `Usuario`
- `ComunidadeService`

---

## 5. User Story 7 - Mensagens a comunidades

### 5.1 Requisito implementado

Um usuario pode enviar uma mensagem para uma comunidade. Todos os membros recebem a mensagem.

### 5.2 Regras atendidas

- a mensagem e entregue a todos os membros da comunidade
- cada usuario le suas mensagens em ordem FIFO
- recados e mensagens continuam sendo conceitos distintos
- a leitura de mensagens vazia gera `NaoHaMensagensException`

### 5.3 Classes envolvidas

- `Usuario`
- `ComunidadeService`
- `RecadoService`
- `Facade`

---

## 6. User Story 8 - Novos relacionamentos

### 6.1 Requisito implementado

O sistema passou a suportar tres novos tipos de relacao:

- fan / idolo
- paquera
- inimigo

### 6.2 Regras atendidas

- um usuario pode ter varios fas
- um usuario pode ter varios idolos
- um usuario pode ter varios paqueras
- um usuario pode ter varios inimigos
- a relacao de paquera e privada para quem a adicionou
- quando a paquera se torna mutua, o sistema envia um recado automatico para ambos
- um inimigo nao pode adicionar o outro como amigo, idolo, paquera ou destinatario de recado

### 6.3 Classes envolvidas

- `Usuario`
- `AmizadeService`
- `RecadoService`
- `Facade`

---

## 7. User Story 9 - Remocao de conta

### 7.1 Requisito implementado

O usuario pode remover sua conta do sistema e todas as informacoes associadas a ele devem desaparecer.

### 7.2 Regras atendidas

- o usuario e removido do repositiorio
- suas relacoes sao removidas dos outros usuarios
- seus recados enviados sao removidos das caixas dos destinatarios
- suas comunidades sao removidas quando ele e dono
- sua participacao em comunidades tambem e removida
- sua sessao e removida, sem afetar as sessoes de outros usuarios

### 7.3 Classes envolvidas

- `UsuarioService`
- `SessaoService`
- `UsuarioRepository`
- `ComunidadeRepository`
- `Usuario`
- `Comunidade`

---

## 8. Tratamento de excecoes

O projeto utiliza excecoes especificas para manter o contrato dos testes de aceitacao. Entre as principais:

- `ComunidadeJaExisteException`
- `ComunidadeNaoExisteException`
- `NaoHaMensagensException`
- `UsuarioJaAdicionadoComoIdoloException`
- `UsuarioJaAdicionadoComoPaqueraException`
- `UsuarioJaAdicionadoComoInimigoException`
- `UsuarioNaoPodeSerIdoloDeSiMesmoException`
- `UsuarioNaoPodeSerPaqueraDeSiMesmoException`
- `UsuarioNaoPodeSerInimigoDeSiMesmoException`
- `FuncaoInvalidaException`

Essas excecoes permitem que cada regra de negocio seja testada com mensagens precisas.

---

## 9. Decisoes de design

### 9.1 Estruturas de dados

- `LinkedHashSet` para colecoes com ordem de insercao previsivel
- `LinkedHashMap` para persistir objetos mantendo ordem deterministica
- `Queue` para recados e mensagens em FIFO

### 9.2 Separacao de responsabilidades

- `Usuario` armazena o estado do usuario
- `Comunidade` armazena nome, descricao, dono e membros
- `Service` implementa regras de negocio
- `Repository` cuida da persistencia

### 9.3 Persistencia unificada

Usuarios e comunidades sao salvos juntos em um unico arquivo, evitando perda de estado entre execucoes e simplificando a recuperacao da aplicacao.

---

## 10. Conclusao

Com as User Stories 5 a 9, o Jackut evoluiu de uma rede simples de usuarios para uma plataforma com:

- comunidades
- participacao em comunidades
- mensagens para grupos
- relacionamentos sociais mais ricos
- exclusao completa e consistente de contas

O sistema ficou mais aderente ao contrato dos testes e mais organizado em termos de dominio, persistencia e tratamento de excecoes.

