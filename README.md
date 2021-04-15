# Serviço de Pedidos

Serviço de Pedidos criado com base em Java 11 e Spring Boot 2.4.4

## Instalação

Clone o projeto para o repositório desejado. Se for a primeira vez executando o projeto, execute os seguintes comandos na raiz do mesmo:

```bash
mvn clean install -Dskiptests

docker build -t order-service:v1 .

docker run -p 8080:8080 order-service:v1
```

A partir dos comandos anteriores o projeto deverá estar rodando em  **http://localhost:8080**.

Uma vez executados, para subir a aplicação novamente basta executar o último comando. O segundo e o terceiro comandos só serão executados novamente se houverem alterações no projeto.

## Endpoints


> CONSULTAR CEP -> http://localhost:8080/api/consultacep/{cep}

**Exemplo**: http://localhost:8080/api/consultacep/12238745

> INSERIR PEDIDO -> http://localhost:8080/api/pedido

```
Payload: {

  "pedido":"123456",
  "itens": [
  {
    "descricao": "Item A",
    "precoUnitario": 10,
    "qtd": 1
  },
  {
    "descricao": "Item B",
    "precoUnitario": 5,
    "qtd": 2
  }
  ]
}
```

> ATUALIZAR PEDIDO -> http://localhost:8080/api/pedido/{pedido}

**Exemplo**: http://localhost:8080/api/pedido/123456
```
Payload: Payload: {

  "pedido":"123456",
  "itens": [
  {
    "descricao": "Item A",
    "precoUnitario": 10,
    "qtd": 1
  },
  {
    "descricao": "Item C",
    "precoUnitario": 3,
    "qtd": 15
  }
  ]
}
```
> BUSCAR PEDIDO -> http://localhost:8080/api/pedido/{pedido}

**Exemplo**: http://localhost:8080/api/pedido/123456

> DELETAR PEDIDO -> http://localhost:8080/api/pedido/{pedido}

**Exemplo**: http://localhost:8080/api/pedido/123456

> VERIFICAR STATUS DO PEDIDO -> http://localhost:8080/api/status
```
Payload: {
  "status":"APROVADO",
  "itensAprovados": 3,
  "valorAprovado": 20,
  "pedido":"123456"
}
```

## Testes
Para executar os testes, abra um terminal na raiz do projeto e execute:

```bash
mvn test
```
