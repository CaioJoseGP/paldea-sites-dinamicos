# Premissa da Aplicacao Paldea

## Visao geral

O projeto Paldea era uma aplicacao Spring Boot voltada para uma loja de plantas de jardim. A proposta combinava uma API REST simples com paginas estaticas para apresentar catalogo, promocao e operacoes de cadastro.

## Dominio e dados

- Entidade principal: `Plant`
- Campos usados: `id`, `nome`, `descricao`, `preco` e `categoria`
- Persistencia: repositorio em memoria, sem banco de dados
- Dados iniciais: lista mockada de plantas ornamentais, flores, aromaticas, ervas, suculentas e palmeiras

## Logica da aplicacao

- `GET /api/plantas`: listava todas as plantas cadastradas
- `GET /api/plantas/{id}`: buscava uma planta por identificador
- `POST /api/plantas`: criava um novo registro
- `PUT /api/plantas/{id}`: atualizava um registro existente
- `DELETE /api/plantas/{id}`: removia um registro existente
- `GET /api/promocao`: retornava um objeto fixo de promocao com estado ativo, mensagem e percentual de desconto

## Logica exibida no frontend

- `index.html`: consumia `/api/promocao` e demonstrava exibicao condicional com `if`
- `catalogo.html`: consumia `/api/plantas` e renderizava a lista de plantas com iteracao via `forEach`
- `crud.html`: oferecia interface para criar, buscar, atualizar, excluir e listar plantas pela API
- `style.css`: centralizava o estilo visual das paginas

## Premissa tecnica

- Backend em Spring Boot com `spring-boot-starter-webmvc`
- Teste basico de subida de contexto com `@SpringBootTest`
- Aplicacao sem autenticacao, sem banco e sem persistencia duravel
- Objetivo principal: demonstrar conceitos de CRUD, consumo de API no frontend e logica condicional/listagem em JavaScript
