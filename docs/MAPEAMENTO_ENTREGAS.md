# Mapeamento Das Entregas

## Entrega 1 - pagina com condicao

- Status: atendido
- Onde aparece: `src/main/resources/templates/catalogo.html`
- Como foi implementado: o catalogo alterna o destaque promocional com `th:if` e `th:unless`, mudando o bloco visivel conforme o status da campanha.

## Entrega 1 - pagina com repeticao

- Status: atendido
- Onde aparece: `src/main/resources/templates/catalogo.html`
- Como foi implementado: a lista usa `th:each` para repetir a estrutura dos cards e simular dados que futuramente viriam de banco.

## Entrega 1 - CRUD visual

- Status: atendido
- Onde aparece: `src/main/resources/templates/plantas.html`
- Como foi implementado: a tela de gestao concentra inserir, atualizar, buscar e apagar em um formulario unico com envio HTML para os endpoints server side.

## Entrega 2 - servidor web real com Spring MVC

- Status: atendido
- Onde aparece: controladores em `src/main/java/com/jardim/paldea/controller`
- Como foi implementado: o projeto roda com Spring Boot e processa as paginas no servidor usando controllers e Thymeleaf.

## Entrega 2 - codigos HTTP pela regra de negocio

- Status: atendido
- Onde aparece: `src/main/java/com/jardim/paldea/controller/LoginController.java`, `src/main/java/com/jardim/paldea/controller/PlantController.java` e `src/test/java/com/jardim/paldea/PaldeaApplicationTests.java`
- Como foi implementado: operacoes validas retornam `200`, erros de entrada retornam `400` e registros inexistentes retornam `404`.

## Entrega 2 - manual para capturar headers

- Status: parcialmente atendido
- Onde aparece: `MANUAL_VALIDACAO_HTTP.md`
- Como foi implementado: o passo a passo do DevTools e do Postman foi documentado.
- O que ainda falta para fechar com excelencia: inserir as capturas de tela reais no documento final.

## Entrega 3 - requisicoes GET e POST por formularios

- Status: atendido
- Onde aparece: `src/main/resources/templates/catalogo.html`, `src/main/resources/templates/plantas.html`, `src/main/java/com/jardim/paldea/controller/ShowcaseController.java` e `src/main/java/com/jardim/paldea/controller/PlantController.java`
- Como foi implementado: buscas e filtros usam `GET`, deixando parametros visiveis na URL (`/catalogo?promocaoStatus=...` e `/plantas/buscar?id=...`). Login e operacoes de cadastro usam `POST`, enviando dados pelo corpo da requisicao (`/login`, `/plantas/inserir`, `/plantas/atualizar` e `/plantas/apagar`).

## Entrega 3 - persistencia com SESSION

- Status: atendido
- Onde aparece: `src/main/java/com/jardim/paldea/controller/LoginController.java`, `src/main/java/com/jardim/paldea/controller/ShowcaseController.java`, `src/main/java/com/jardim/paldea/controller/PlantController.java`, `src/main/resources/templates/catalogo.html` e `src/main/resources/templates/plantas.html`
- Como foi implementado: apos login valido, o nome do usuario fica salvo na `HttpSession` com a chave `usuario`. Ao navegar entre paginas, os controllers leem essa sessao e enviam o usuario para as views, mantendo o estado de login sem obrigar novo acesso.

## Entrega 3 - persistencia com COOKIE

- Status: documentado para implementacao futura
- Onde aparece: orientacao registrada neste mapeamento
- Como implementar de forma direta: ler um cookie com `@CookieValue` e gravar uma preferencia simples no navegador com `HttpServletResponse#addCookie(...)`, por exemplo `idioma=pt-BR` ou `tema=escuro`.
- Observacao de escopo: cookie nao foi implementado nesta rodada para manter o codigo simples, conforme decisao do projeto.
