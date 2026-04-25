# Mapeamento Das Entregas

## Entrega 1 - pagina com condicao

- Status: atendido
- Onde aparece: `src/main/resources/templates/ofertas.html`
- Como foi implementado: a vitrine alterna o destaque principal com `th:if` e `th:unless`, mudando o bloco visivel conforme o status da promocao.

## Entrega 1 - pagina com repeticao

- Status: atendido
- Onde aparece: `src/main/resources/templates/ofertas.html` e `src/main/resources/templates/catalogo.html`
- Como foi implementado: as listas usam `th:each` para repetir a estrutura dos cards e simular dados que futuramente viriam de banco.

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
