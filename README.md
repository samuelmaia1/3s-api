# 3S API

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring_Boot-4.0.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 4.0.1" />
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven" />
  <img src="https://img.shields.io/badge/PostgreSQL-Database-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/OpenAPI-Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="OpenAPI" />
</p>

API REST desenvolvida com **Spring Boot 4**, focada na operação interna da **3S Letreiros**. O projeto centraliza autenticação, gestão de usuários, produtos, clientes, pedidos, contratos e cláusulas, com documentação OpenAPI e geração de PDF para contratos.

## Sumário

- [Visão geral](#visão-geral)
- [Principais funcionalidades](#principais-funcionalidades)
- [Stack e arquitetura](#stack-e-arquitetura)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do ambiente](#configuração-do-ambiente)
- [Como executar](#como-executar)
- [Documentação da API](#documentação-da-api)
- [Autenticação](#autenticação)
- [Exemplos de endpoints](#exemplos-de-endpoints)
- [Build, testes e Docker](#build-testes-e-docker)

## Visão geral

A API foi organizada em camadas de **domain**, **infra** e **presentation**, separando regras de negócio, integrações e exposição HTTP. A aplicação utiliza autenticação baseada em **JWT + refresh token via cookie HTTP-only**, persistência com **Spring Data JPA** e documentação interativa com **Swagger UI**.

## Principais funcionalidades

- Cadastro, autenticação e atualização de usuários.
- Gestão de produtos com consulta pública por ID.
- Cadastro e consulta de clientes.
- Criação e acompanhamento de pedidos.
- Atualização de status do pedido ao longo do fluxo operacional.
- Geração de contratos em PDF e assinatura de contrato.
- Cadastro de cláusulas contratuais.
- Resumo gerencial no dashboard.

## Stack e arquitetura

### Tecnologias

- **Java 21**
- **Spring Boot 4.0.1**
- **Spring Web MVC**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL**
- **Springdoc OpenAPI / Swagger UI**
- **Thymeleaf + OpenHTMLToPDF** para contratos em PDF
- **Maven** para build e gerenciamento de dependências

### Arquitetura

```text
api/src/main/java/com/_s/api
├── domain        # regras de negócio, entidades e serviços
├── infra         # segurança, persistência, mapeadores e configuração
└── presentation  # controllers, DTOs, responses e handlers HTTP
```

## Estrutura do projeto

```text
.
├── README.md
└── api
    ├── Dockerfile
    ├── mvnw
    ├── pom.xml
    └── src
        ├── main
        │   ├── java/com/_s/api
        │   └── resources
        └── test
```

## Pré-requisitos

Antes de iniciar, tenha instalado:

- **JDK 21**
- **Maven 3.9+** ou utilize o wrapper `./mvnw`
- **PostgreSQL** acessível pela aplicação
- Variáveis de ambiente opcionais para customizar segurança e CORS

## Configuração do ambiente

A aplicação lê propriedades de `api/src/main/resources/application.properties` e aceita sobrescrita por variáveis de ambiente.

### Variáveis suportadas

| Variável | Descrição | Valor padrão |
| --- | --- | --- |
| `JWT_SECRET` | Segredo usado na assinatura do token JWT | valor local definido no projeto |
| `ISSUER` | Issuer do token JWT | `Meiflow-api-dev` |
| `ALLOWED_ORIGINS` | Origens liberadas para CORS HTTP | `http://localhost:3000` |
| `ALLOWED_SOCKET_ORIGINS` | Origens liberadas para WebSocket | `*` |

> **Importante:** atualmente o projeto possui configuração de datasource diretamente em `application.properties`. Para ambientes reais, prefira mover credenciais para variáveis de ambiente ou secret manager.

## Como executar

### 1. Entrar no diretório da aplicação

```bash
cd api
```

### 2. Executar em modo de desenvolvimento

```bash
./mvnw spring-boot:run
```

A aplicação sobe, por padrão, na porta **8080**.

### 3. Gerar build do projeto

```bash
./mvnw clean package
```

## Documentação da API

Com a aplicação em execução, acesse:

- **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

## Autenticação

### Endpoints públicos

Os seguintes endpoints estão liberados sem autenticação:

- `POST /api/users/create`
- `POST /api/auth/login`
- `POST /api/auth/refresh`
- `GET /api/products/{id}`
- `GET /api/health`
- Rotas do Swagger e WebSocket configuradas pela aplicação

### Fluxo de autenticação

1. Envie credenciais para `POST /api/auth/login`.
2. A API retorna o `accessToken` no corpo e também define cookies HTTP-only.
3. Use o token Bearer nas rotas protegidas.
4. Quando necessário, renove a sessão com `POST /api/auth/refresh` usando o cookie `refresh_token`.
5. Finalize a sessão com `POST /api/auth/logout`.

### Header de exemplo

```http
Authorization: Bearer <seu-jwt>
```

## Exemplos de endpoints

Abaixo estão exemplos práticos para acelerar testes locais via `curl`.

### 1. Criar usuário

```bash
curl -X POST http://localhost:8080/api/users/create \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Samuel",
    "lastName": "Maia",
    "email": "samuel@example.com",
    "cpf": "12345678909",
    "password": "Senha@123",
    "socialName": "3S Letreiros",
    "instagram": "@3sletreiros",
    "logo": "https://cdn.exemplo.com/logo.png",
    "address": {
      "cep": "01001-000",
      "street": "Praça da Sé",
      "neighborhood": "Sé",
      "city": "São Paulo",
      "number": "100"
    }
  }'
```

### 2. Fazer login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "samuel@example.com",
    "password": "Senha@123"
  }'
```

### 3. Criar produto autenticado

```bash
curl -X POST http://localhost:8080/api/users/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-jwt>" \
  -d '{
    "name": "Letreiro Neon",
    "description": "Letreiro personalizado para fachada",
    "price": 799.90,
    "stock": 15,
    "imageUri": "https://cdn.exemplo.com/produtos/neon.png"
  }'
```

### 4. Criar cliente

```bash
curl -X POST http://localhost:8080/api/costumers/create \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-jwt>" \
  -d '{
    "name": "Maria",
    "lastName": "Oliveira",
    "email": "maria@example.com",
    "cpf": "98765432100",
    "address": {
      "cep": "20040-020",
      "street": "Rua da Assembleia",
      "neighborhood": "Centro",
      "city": "Rio de Janeiro",
      "number": "200"
    }
  }'
```

### 5. Criar pedido

```bash
curl -X POST http://localhost:8080/api/users/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-jwt>" \
  -d '{
    "costumerId": "<costumer-id>",
    "items": [
      {
        "productId": "<product-id>",
        "quantity": 2
      }
    ],
    "deliveryAddress": {
      "cep": "30130-110",
      "street": "Av. Afonso Pena",
      "neighborhood": "Centro",
      "city": "Belo Horizonte",
      "number": "1500"
    },
    "deliveryDate": "2026-03-25T10:00:00",
    "returnDate": "2026-03-30T18:00:00"
  }'
```

### 6. Atualizar status do pedido

```bash
curl -X PUT http://localhost:8080/api/orders/<order-id>/status \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-jwt>" \
  -d '{
    "action": "PAGAMENTO_APROVADO"
  }'
```

### 7. Gerar contrato em PDF

```bash
curl -X POST http://localhost:8080/api/contracts/generate \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-jwt>" \
  --output contrato.pdf \
  -d '{
    "costumerId": "<costumer-id>",
    "orderId": "<order-id>",
    "clausesIds": ["<clause-id-1>", "<clause-id-2>"]
  }'
```

### 8. Obter resumo do dashboard

```bash
curl -X GET http://localhost:8080/api/dashboard/summary \
  -H "Authorization: Bearer <seu-jwt>"
```

## Build, testes e Docker

### Executar testes

```bash
./mvnw test
```

### Subir com Docker

```bash
docker build -t 3s-api .
docker run --rm -p 8080:8080 3s-api
```

> O `Dockerfile` está dentro da pasta `api`, então execute os comandos acima a partir desse diretório.

---

Se quiser evoluir este repositório, uma boa próxima etapa é adicionar **arquivo `.env.example`**, **pipeline de CI** e **instruções de banco local via Docker Compose** para facilitar onboarding do time.
