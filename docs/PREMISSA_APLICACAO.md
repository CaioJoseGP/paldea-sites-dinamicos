# Premissa da Aplicacao Paldea

## Visao geral

O projeto Paldea representa a presenca digital de uma loja de plantas ornamentais, com foco em vitrine comercial, catalogo visual e operacao administrativa. A proposta desta fase e unir uma interface mais profissional com processamento server side em Spring MVC, sem depender de banco de dados real.

## Tema e dominio

- Tema principal: loja de plantas com curadoria para interiores, jardins e presentes
- Entidade central: `Plant`
- Campos usados: `id`, `nome`, `descricao`, `categoria` e `preco`
- Persistencia: repositorio em memoria
- Dados iniciais: colecao mockada com especies de diferentes categorias

## Arquitetura atual

- `LoginController`: recebe o acesso da equipe e redireciona para a vitrine
- `ShowcaseController`: entrega o catalogo com comportamento condicional de campanha
- `PlantController`: concentra as operacoes de cadastro, busca, atualizacao e exclusao
- `PlantService`: aplica regras simples de negocio e validacao
- `PlantRepository`: mantem os registros em memoria
- `templates/*.html`: views renderizadas no servidor com Thymeleaf

## Logica da aplicacao

- `GET /login`: abre a tela inicial de acesso
- `POST /login`: valida as credenciais da equipe
- `GET /catalogo`: exibe a colecao com repeticao de itens e comportamento condicional para a campanha
- `GET /plantas`: abre a central administrativa
- `POST /plantas/inserir`: cria um novo cadastro
- `POST /plantas/atualizar`: altera um cadastro existente
- `POST /plantas/apagar`: remove um cadastro existente
- `GET /plantas/buscar`: localiza uma planta por ID

## Premissa academica atendida

- Mockup inicial evoluido para uma aplicacao Spring MVC funcional
- Pagina condicional implementada com `th:if`
- Pagina de listagem implementada com `th:each`
- CRUD visual com processamento server side
- Tratamento de `200 OK`, `400 Bad Request` e `404 Not Found`

## Premissa tecnica

- Spring Boot com `spring-boot-starter-webmvc`
- Templates server side com `spring-boot-starter-thymeleaf`
- Sem JavaScript para envio das requisicoes HTTP principais
- Sem banco de dados, autenticacao externa ou persistencia duravel
