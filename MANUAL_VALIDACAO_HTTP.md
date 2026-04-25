# Manual De Validacao HTTP

## Entrega 2 - manual para capturar os headers

Este manual mostra como comprovar os codigos HTTP retornados pela aplicacao Paldea. O documento foi organizado para facilitar a inclusao dos prints finais no arquivo entregue.

## Preparacao

1. Abra um terminal na raiz do projeto.
2. Inicie a aplicacao com `.\mvnw.cmd spring-boot:run`.
3. Acesse `http://localhost:8080/login`.

## Validacao pelo navegador

### Entrega 2 - uso de 200 OK

1. Abra o navegador em `http://localhost:8080/catalogo`.
2. Pressione `F12` para abrir o DevTools.
3. Entre na aba `Network` ou `Rede`.
4. Recarregue a pagina.
5. Clique na requisicao `catalogo`.
6. Confira em `Headers` ou `Cabecalhos` que o `Status Code` retornado e `200 OK`.

Print sugerido:
- lista da aba `Network` mostrando a requisicao `catalogo`
- painel de `Headers` destacando `200 OK`

### Entrega 2 - uso de 400 Bad Request

1. Acesse `http://localhost:8080/login`.
2. Abra o DevTools na aba `Network`.
3. Envie o formulario de login com e-mail correto e senha vazia, ou com credenciais invalidas.
4. Clique na requisicao `login`.
5. Confira em `Headers` que o `Status Code` retornado e `400 Bad Request`.

Print sugerido:
- formulario enviado com dado invalido
- cabecalhos da requisicao `login` mostrando `400 Bad Request`

### Entrega 2 - uso de 404 Not Found

1. Acesse `http://localhost:8080/plantas`.
2. Abra o DevTools na aba `Network`.
3. Use o modo `Buscar`.
4. Informe um ID inexistente, como `999`.
5. Envie a busca.
6. Clique na requisicao `buscar`.
7. Confira em `Headers` que o `Status Code` retornado e `404 Not Found`.

Print sugerido:
- formulario de busca com `999`
- cabecalhos da requisicao `buscar` mostrando `404 Not Found`

## Validacao pelo Postman

### Entrega 2 - uso de 200 OK

- Metodo: `GET`
- URL: `http://localhost:8080/catalogo`
- Resultado esperado: `200 OK`

### Entrega 2 - uso de 400 Bad Request

- Metodo: `POST`
- URL: `http://localhost:8080/login`
- Body: `x-www-form-urlencoded`
- Campos:
  - `email = equipe@paldea.com`
  - `senha =`
- Resultado esperado: `400 Bad Request`

### Entrega 2 - uso de 404 Not Found

- Metodo: `GET`
- URL: `http://localhost:8080/plantas/buscar?id=999`
- Resultado esperado: `404 Not Found`

## Fechamento

Para a versao final da entrega, adicione os prints reais nos pontos sugeridos acima. Assim o documento fica completo e pronto para comprovacao visual dos cabecalhos HTTP.
