# Exemplos de Uso da API - Sistema de Tratamento de Erros

Este documento demonstra como o sistema de tratamento de erros funciona na prática seguindo as **melhores práticas do Spring Boot** - com tratamento **100% centralizado** no GlobalExceptionHandler.

## 🔐 **Fluxo de Autenticação JWT**

### **1. Registro (POST /api/auth/register)**
- Cria uma nova conta no sistema
- Senha é criptografada automaticamente
- UserType é definido como CUSTOMER por padrão
- Retorna dados do usuário criado (sem token)

### **2. Login (POST /api/auth/login)**
- Valida credenciais (login + password)
- Gera token JWT com tempo de expiração
- Retorna token para uso em requisições subsequentes

### **3. Uso do Token**
- Incluir header: `Authorization: Bearer {token}`
- Token é validado a cada requisição protegida
- Endpoints protegidos requerem autenticação válida

### **4. Segurança**
- Senhas são criptografadas com BCrypt
- Tokens JWT têm tempo de expiração configurável
- Credenciais inválidas retornam 401 Unauthorized

---

## 🏗️ **Arquitetura do Tratamento de Erros**

```
Controller (limpo) → Service (lança exceptions) → GlobalExceptionHandler (trata tudo) → ErrorResponse
```

**Características:**
- ✅ **Controller**: Apenas entrada/saída HTTP
- ✅ **Service**: Apenas lógica de negócio + exceções específicas  
- ✅ **GlobalExceptionHandler**: Tratamento 100% centralizado
- ✅ **ErrorResponse**: Formato padronizado para todas as respostas de erro

## 🔐 **Exemplos de Endpoints de Autenticação**

## AUTH 1. Registro de Usuário com Sucesso

### Request:
```http
POST /api/auth/register
Content-Type: application/json

{
    "name": "João Silva",
    "email": "joao.silva@email.com",
    "login": "joao.silva",
    "password": "senha123456",
    "address": "Rua das Flores, 123",
    "userType": "CUSTOMER"
}
```

### Response (201 Created):
```json
{
    "id": 1,
    "name": "João Silva",
    "email": "joao.silva@email.com",
    "login": "joao.silva",
    "lastUpdated": "2025-08-07T14:30:00",
    "address": "Rua das Flores, 123"
}
```

## AUTH 2. Erro no Registro - Dados Inválidos (Bean Validation)

### Request:
```http
POST /api/auth/register
Content-Type: application/json

{
    "name": "",
    "email": "email-invalido",
    "login": "ab",
    "password": "123",
    "address": "",
    "userType": "CUSTOMER"
}
```

### Response (400 Bad Request):
```json
{
    "message": "Dados inválidos fornecidos",
    "status": 400,
    "error": "Bad Request",
    "timestamp": "2025-08-07 14:30:00",
    "path": "/api/auth/register",
    "details": [
        "Nome é obrigatório",
        "Email deve ter formato válido",
        "Login deve ter entre 3 e 50 caracteres",
        "Senha deve ter pelo menos 6 caracteres",
        "Endereço é obrigatório"
    ]
}
```

## AUTH 3. Erro no Registro - Email/Login já existe

### Request:
```http
POST /api/auth/register
Content-Type: application/json

{
    "name": "Maria Santos",
    "email": "joao.silva@email.com",
    "login": "maria.santos",
    "password": "senha123456",
    "address": "Rua das Palmeiras, 456",
    "userType": "CUSTOMER"
}
```

### Response (409 Conflict):
```json
{
    "message": "Usuário com email 'joao.silva@email.com' já existe",
    "status": 409,
    "error": "Conflict",
    "timestamp": "2025-08-07 14:30:00",
    "path": "/api/auth/register",
    "details": null
}
```

## AUTH 4. Login com Sucesso

### Request:
```http
POST /api/auth/login
Content-Type: application/json

{
    "login": "joao.silva",
    "password": "senha123456"
}
```

### Response (200 OK):
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2FvLnNpbHZhIiwiaWF0IjoxNjU2Nzc5MjAwLCJleHAiOjE2NTY4NjU2MDB9.ABC123XYZ789",
    "type": "Bearer",
    "username": "joao.silva"
}
```

## AUTH 5. Erro no Login - Credenciais Inválidas

### Request:
```http
POST /api/auth/login
Content-Type: application/json

{
    "login": "joao.silva",
    "password": "senhaerrada"
}
```

### Response (401 Unauthorized):
```json
{
    "token": "",
    "type": "",
    "username": "Invalid credentials"
}
```

## AUTH 6. Erro no Login - Dados de Login Inválidos (Campos Vazios)

### Request:
```http
POST /api/auth/login
Content-Type: application/json

{
    "login": "",
    "password": ""
}
```

### Response (400 Bad Request):
```json
{
    "message": "Dados inválidos fornecidos",
    "status": 400,
    "error": "Bad Request",
    "timestamp": "2025-08-07 14:30:00",
    "path": "/api/auth/login",
    "details": [
        "Login é obrigatório",
        "Senha é obrigatória"
    ]
}
```

## AUTH 7. Usando Token JWT em Requisições Protegidas

### Request (Exemplo de busca de usuário com autenticação):
```http
GET /api/user/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2FvLnNpbHZhIiwiaWF0IjoxNjU2Nzc5MjAwLCJleHAiOjE2NTY4NjU2MDB9.ABC123XYZ789
```

### Response (200 OK):
```json
{
    "id": 1,
    "name": "João Silva",
    "email": "joao.silva@email.com",
    "login": "joao.silva",
    "lastUpdated": "2025-08-07T14:30:00",
    "address": "Rua das Flores, 123"
}
```

## AUTH 9. Erro de Acesso sem Token

### Request:
```http
GET /api/user/1
```

### Response (401 Unauthorized):
```json
{
    "message": "Token de acesso é obrigatório",
    "status": 401,
    "error": "Unauthorized",
    "timestamp": "2025-08-07 14:30:00",
    "path": "/api/user/1",
    "details": null
}
```

---

## 👤 **Exemplos de Endpoints de Usuário (CRUD)**

## 1. Criação de Usuário com Sucesso

### Request:
```http
POST /api/user
Content-Type: application/json

{
    "name": "João Silva",
    "email": "joao@email.com",
    "login": "joao123",
    "password": "minhasenha123",
    "address": "Rua A, 123",
    "userType": "CUSTOMER"
}
```

### Response (201 Created):
```json
{
    "id": 1,
    "name": "João Silva",
    "email": "joao@email.com",
    "login": "joao123",
    "lastUpdated": "2025-08-03T12:30:00",
    "address": "Rua A, 123"
}
```

## 2. Erro de Validação - Dados Inválidos (Bean Validation)

### Request:
```http
POST /api/user
Content-Type: application/json

{
    "name": "",
    "email": "email-invalido", 
    "login": "ab",
    "password": "123",
    "address": "Endereço muito longo que excede o limite de 255 caracteres permitidos para este campo, causando uma falha na validação do sistema que está configurado para aceitar no máximo 255 caracteres... Endereço muito longo que excede o limite de 255 caracteres permitidos",
    "userType": "CUSTOMER"
}
```

### Response (400 Bad Request):
```json
{
    "message": "Dados inválidos fornecidos",
    "status": 400,
    "error": "Bad Request", 
    "timestamp": "2025-08-03 12:30:00",
    "path": "/api/user",
    "details": [
        "Nome é obrigatório",
        "Email deve ter formato válido",
        "Login deve ter entre 3 e 50 caracteres", 
        "Senha deve ter pelo menos 6 caracteres",
        "Endereço deve ter no máximo 255 caracteres"
    ]
}
```

## 2.1. Erro de Validação - Service Layer

### Request:
```http
POST /api/user
Content-Type: application/json

{
    "name": "João Silva",
    "email": "joao@email.com",
    "login": "joao123",
    "password": "senha123",
    "address": "Rua A, 123"
}
```

### Response (400 Bad Request) - ID null no Service:
```json
{
    "message": "ID do usuário não pode ser nulo",
    "status": 400,
    "error": "Bad Request",
    "timestamp": "2025-08-03 12:30:00", 
    "path": "/api/user",
    "details": null
}
```

## 3. Erro de Conflito - Email já existe (UserAlreadyExistsException)

### Request:
```http
POST /api/user
Content-Type: application/json

{
    "name": "Maria Silva",
    "email": "joao@email.com",
    "login": "maria123", 
    "password": "senha123",
    "address": "Rua B, 456",
    "userType": "CUSTOMER"
}
```

### Response (409 Conflict):
```json
{
    "message": "Usuário com email 'joao@email.com' já existe",
    "status": 409,
    "error": "Conflict",
    "timestamp": "2025-08-03 12:30:00",
    "path": "/api/user",
    "details": null
}
```

## 3.1. Erro de Conflito - Constraint do Banco (DataIntegrityViolationException)

### Request:
```http
POST /api/user
Content-Type: application/json

{
    "name": "Pedro Santos",
    "email": "maria@email.com", 
    "login": "joao123",
    "password": "senha123",
    "address": "Rua C, 789",
    "userType": "CUSTOMER"
}
```

### Response (409 Conflict):
```json
{
  "message": "Usuário com login 'joao123' já existe",
  "status": 409,
  "error": "Conflict",
  "timestamp": "2025-08-03 17:33:29",
  "path": "/api/user",
  "details": null
}
```

## 4. Busca de Usuário Inexistente

### Request:
```http
GET /api/user/999
```

### Response (404 Not Found):
```json
{
    "message": "Usuário com ID 999 não encontrado",
    "status": 404,
    "error": "Not Found",
    "timestamp": "2025-08-03 12:30:00",
    "path": "/api/user/999",
    "details": null
}
```

## 5. Atualização Parcial com Sucesso

### Request:
```http
PATCH /api/user/1
Content-Type: application/json

{
    "name": "João Santos",
    "address": "Rua Nova, 789"
}
```

### Response (200 OK):
```json
{
    "id": 1,
    "name": "João Santos",
    "email": "joao@email.com",
    "login": "joao123",
    "lastUpdated": "2025-08-03T12:35:00",
    "address": "Rua Nova, 789"
}
```

## 6. Erro na Atualização - Login já existe

### Request:
```http
PATCH /api/user/1
Content-Type: application/json

{
    "login": "maria123"
}
```

### Response (409 Conflict):
```json
{
    "message": "Usuário com login 'maria123' já existe",
    "status": 409,
    "error": "Conflict",
    "timestamp": "2025-08-03 12:30:00",
    "path": "/api/user/1",
    "details": null
}
```

## 7. Remoção com Sucesso

### Request:
```http
DELETE /api/user/1
```

### Response (204 No Content):
```
(Sem corpo de resposta)
```

## 8. Erro na Remoção - Usuário não existe

### Request:
```http
DELETE /api/user/999
```

### Response (404 Not Found):
```json
{
    "message": "Usuário com ID 999 não encontrado",
    "status": 404,
    "error": "Not Found",
    "timestamp": "2025-08-03 12:30:00",
    "path": "/api/user/999",
    "details": null
}
```

## 9. Erro Interno do Servidor (DataAccessException)

### Request:
```http
GET /api/user
```

### Response (500 Internal Server Error):
```json
{
    "message": "Erro de acesso aos dados",
    "status": 500,
    "error": "Internal Server Error",
    "timestamp": "2025-08-03 12:30:00",
    "path": "/api/user",
    "details": null
}
```

## 9.1. Erro Genérico (Exception)

### Response (500 Internal Server Error):
```json
{
    "message": "Erro interno do servidor",
    "status": 500,
    "error": "Internal Server Error",
    "timestamp": "2025-08-03 12:30:00",
    "path": "/api/user",
    "details": null
}
```

## Códigos de Status HTTP Utilizados

- **200 OK**: Operação realizada com sucesso (login, busca, atualização)
- **201 Created**: Recurso criado com sucesso (registro de usuário)
- **204 No Content**: Recurso removido com sucesso
- **400 Bad Request**: Dados inválidos fornecidos (validação DTO ou Service)
- **401 Unauthorized**: Não autenticado - token ausente ou inválido
- **404 Not Found**: Recurso não encontrado (UserNotFoundException)
- **409 Conflict**: Conflito de dados - duplicação (UserAlreadyExistsException ou DataIntegrityViolationException)
- **500 Internal Server Error**: Erro interno do servidor (DataAccessException ou Exception genérica)

## Campos da Resposta de Erro (ErrorResponse)

- **message**: Mensagem principal do erro
- **status**: Código de status HTTP numérico
- **error**: Descrição textual do status HTTP
- **timestamp**: Data e hora do erro no formato yyyy-MM-dd HH:mm:ss
- **path**: Caminho da requisição que gerou o erro
- **details**: Lista opcional com detalhes específicos (usado principalmente para erros de validação)

## 🔄 **Fluxo de Exceções na Prática**

1. **Cliente** envia request para Controller
2. **Controller** chama Service (sem tratamento de erro)
3. **Service** executa lógica e pode lançar exceção específica
4. **Repository** pode lançar DataAccessException
5. **Spring Framework** propaga exceção automaticamente
6. **GlobalExceptionHandler** captura e formata
7. **Cliente** recebe ErrorResponse padronizada

## 💡 **Tipos de Exceções e Suas Origens**

### **Exceções do Framework (Spring/Bean Validation):**
- `MethodArgumentNotValidException` → Bean Validation (@Valid, @NotBlank, etc.)
- `DataIntegrityViolationException` → Constraints do banco de dados
- `DataAccessException` → Problemas de acesso ao banco
- `IllegalArgumentException` → Argumentos inválidos

### **Exceções Personalizadas (Service Layer):**
- `UserNotFoundException` → Usuário não encontrado
- `UserAlreadyExistsException` → Email/login duplicado
- `InvalidUserDataException` → Dados inválidos na lógica de negócio

### **Tratamento no GlobalExceptionHandler:**
Todas as exceções acima são capturadas e formatadas em `ErrorResponse` padronizada com:
- Mensagem específica e clara
- Status HTTP apropriado
- Timestamp da ocorrência  
- Path da requisição
- Details opcionais (principalmente para validação)

## Endpoints de Autenticação

### POST /api/auth/register
- **Descrição**: Registra um novo usuário no sistema
- **Possíveis Erros**:
    - 400 (dados inválidos)
    - 409 (email/login já existe)
    - 500 (erro interno)

### POST /api/auth/login
- **Descrição**: Realiza autenticação e retorna token JWT
- **Possíveis Erros**:
    - 401 (credenciais inválidas)
    - 500 (erro interno)

## Endpoints do CRUD de Usuários

### GET /api/user
- **Descrição**: Lista todos os usuários
- **Possíveis Erros**: 500 (erro interno)

### GET /api/user/{id}
- **Descrição**: Busca usuário por ID
- **Possíveis Erros**:
    - 400 (ID inválido)
    - 404 (usuário não encontrado)
    - 500 (erro interno)

### POST /api/user
- **Descrição**: Cria novo usuário
- **Possíveis Erros**:
    - 400 (dados inválidos)
    - 409 (email/login já existe)
    - 500 (erro interno)

### PATCH /api/user/{id}
- **Descrição**: Atualiza usuário parcialmente
- **Possíveis Erros**:
    - 400 (dados inválidos)
    - 404 (usuário não encontrado)
    - 409 (email/login já existe)
    - 500 (erro interno)

### DELETE /api/user/{id}
- **Descrição**: Remove usuário
- **Possíveis Erros**:
    - 400 (ID inválido)
    - 404 (usuário não encontrado)
    - 500 (erro interno)

---
