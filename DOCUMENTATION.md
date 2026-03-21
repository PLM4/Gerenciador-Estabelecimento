# 📚 Documentação Completa - Gerenciador de Estabelecimento

## 📋 Índice

1. [Visão Geral](#visão-geral)
2. [Arquitetura](#arquitetura)
3. [Endpoints da API](#endpoints-da-api)
4. [Entidades e Modelo de Dados](#entidades-e-modelo-de-dados)
5. [DTOs (Data Transfer Objects)](#dtos-data-transfer-objects)
6. [Enumerações](#enumerações)
7. [Serviços](#serviços)
8. [Repositórios](#repositórios)
9. [Configuração de Segurança](#configuração-de-segurança)
10. [Testes Unitários](#testes-unitários)
11. [Como Executar](#como-executar)
12. [Dependências](#dependências)
13. [Estrutura de Diretórios](#estrutura-de-diretórios)

---

## 🎯 Visão Geral

**Gerenciador de Estabelecimento** é uma aplicação backend desenvolvida em **Spring Boot 3.4.2** que gerencia operações empresariais como:

- 👥 Gestão de Clientes
- 📦 Catálogo de Produtos
- 🏭 Fornecedores
- 📂 Categorias
- 📝 Pedidos
- 💳 Pagamentos
- 🔐 Autenticação de Usuários

**Características principais:**
- API REST com Spring Web
- Autenticação JWT
- Banco de dados PostgreSQL
- Persistência com JPA/Hibernate
- Documentação automática com OpenAPI (Springdoc)
- Testes automatizados com JUnit 5 e Mockito
- Open API 3.0 para integração externa

---

## 🏗️ Arquitetura

A aplicação segue o padrão arquitetural **MVC (Model-View-Controller)** com camadas de:

```
┌─────────────────────────────────────┐
│      Controllers (REST API)         │
└─────────────────────────────────────┘
            ↕
┌─────────────────────────────────────┐
│      Services (Lógica de Negócio)   │
└─────────────────────────────────────┘
            ↕
┌─────────────────────────────────────┐
│      Repositories (Persistência)    │
└─────────────────────────────────────┘
            ↕
┌─────────────────────────────────────┐
│    PostgreSQL Database              │
└─────────────────────────────────────┘
```

### Componentes estruturais:

- **Controllers**: Endpoints REST que recebem requisições
- **Services**: Lógica de negócio centralizada
- **Repositories**: Acesso e manipulação de dados
- **Entities**: Modelos de dados mapeados para o banco
- **DTOs**: Objetos de transferência de dados entre cliente e servidor
- **Config**: Configuração de segurança, codificação de senhas e documentação
- **Enums**: Tipos enumerados para Status e Tipo de Pagamento
- **Exceptions**: Tratamento de erros customizado
- **Util**: Utilitários como geração de tokens JWT

---

## 🔌 Endpoints da API

### 📊 BASE URL
```
http://localhost:8081/
```

---

### 🔐 **AUTENTICAÇÃO**

#### **1. Login**
```http
POST /auth/login
Content-Type: application/json

{
  "username": "string",
  "password": "string"
}
```
**Resposta (200):**
```json
{
  "token": "eyJhbGc..."
}
```
**Status:** 200 OK

---

#### **2. Registro de Novo Usuário**
```http
POST /auth/register
Content-Type: application/json

{
  "username": "string",
  "password": "string",
  "role": "USER"
}
```
**Resposta (200):**
```json
{
  "id": 1,
  "username": "string",
  "role": "USER"
}
```
**Status:** 200 OK

---

### 👥 **CLIENTES**

#### **1. Criar Cliente**
```http
POST /clientes
Content-Type: application/json
Authorization: Bearer {token}

{
  "nome": "João Silva",
  "cpf": "123.456.789-00",
  "contatos": ["email@example.com", "1234567890"]
}
```
**Resposta (200):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "cpf": "123.456.789-00",
  "contatos": ["email@example.com", "1234567890"],
  "pagamentos": []
}
```

---

#### **2. Obter Cliente por ID**
```http
GET /clientes/{id}
Authorization: Bearer {token}
```
**Resposta (200):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "cpf": "123.456.789-00",
  "contatos": ["email@example.com"],
  "pagamentos": []
}
```

---

#### **3. Listar Todos os Clientes**
```http
GET /clientes
Authorization: Bearer {token}
```
**Resposta (200):**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "cpf": "123.456.789-00",
    "contatos": ["email@example.com"],
    "pagamentos": []
  }
]
```

---

#### **4. Atualizar Cliente**
```http
PATCH /clientes/{id}
Content-Type: application/json
Authorization: Bearer {token}

{
  "nome": "João Santos",
  "cpf": "123.456.789-00",
  "contatos": ["novo@example.com"]
}
```
**Resposta (200):** Cliente atualizado

---

#### **5. Deletar Cliente**
```http
DELETE /clientes/{id}
Authorization: Bearer {token}
```
**Resposta:** 204 No Content

---

### 📦 **PRODUTOS**

#### **1. Criar Produto**
```http
POST /produtos
Content-Type: application/json
Authorization: Bearer {token}

{
  "nome": "Notebook",
  "preco": 3500.00,
  "quantidade": 10,
  "validate": "2025-12-31",
  "categorias": [1, 2],
  "fornecedores": [1]
}
```
**Resposta (200):**
```json
{
  "id": 1,
  "nome": "Notebook",
  "preco": 3500.00,
  "quantidade": 10,
  "validate": "2025-12-31",
  "categorias": [{...}],
  "fornecedores": [{...}]
}
```

---

#### **2. Obter Produto por ID**
```http
GET /produtos/{id}
Authorization: Bearer {token}
```

---

#### **3. Listar Todos os Produtos**
```http
GET /produtos
Authorization: Bearer {token}
```

---

#### **4. Atualizar Produto**
```http
PATCH /produtos/{id}
Content-Type: application/json
Authorization: Bearer {token}

{
  "nome": "Notebook Pro",
  "preco": 4000.00,
  "quantidade": 8
}
```

---

#### **5. Deletar Produto**
```http
DELETE /produtos/{id}
Authorization: Bearer {token}
```

---

### 📂 **CATEGORIAS**

#### **1. Criar Categoria**
```http
POST /categorias
Content-Type: application/json
Authorization: Bearer {token}

{
  "nome": "Eletrônicos"
}
```

---

#### **2. Obter Categoria por ID**
```http
GET /categorias/{id}
Authorization: Bearer {token}
```

---

#### **3. Listar Todas as Categorias**
```http
GET /categorias
Authorization: Bearer {token}
```

---

#### **4. Buscar Categoria por Nome**
```http
GET /categorias/filter?nome=Eletrônicos
Authorization: Bearer {token}
```

---

#### **5. Atualizar Categoria**
```http
PATCH /categorias/{id}
Content-Type: application/json
Authorization: Bearer {token}

{
  "nome": "Eletrônicos e Informática"
}
```

---

#### **6. Deletar Categoria**
```http
DELETE /categorias/{id}
Authorization: Bearer {token}
```

---

### 🏭 **FORNECEDORES**

#### **1. Criar Fornecedor**
```http
POST /fornecedores
Content-Type: application/json
Authorization: Bearer {token}

{
  "nome": "Tech Supplies",
  "cnpj": "12.345.678/0001-90",
  "contatos": ["vendas@techsupplies.com"]
}
```

---

#### **2. Obter Fornecedor por ID**
```http
GET /fornecedores/{id}
Authorization: Bearer {token}
```

---

#### **3. Listar Todos os Fornecedores**
```http
GET /fornecedores
Authorization: Bearer {token}
```

---

#### **4. Atualizar Fornecedor**
```http
PATCH /fornecedores/{id}
Content-Type: application/json
Authorization: Bearer {token}

{
  "nome": "Tech Supplies Distribuidor",
  "contatos": ["novo@techsupplies.com"]
}
```

---

#### **5. Deletar Fornecedor**
```http
DELETE /fornecedores/{id}
Authorization: Bearer {token}
```

---

### 📝 **PEDIDOS**

#### **1. Criar Pedido**
```http
POST /pedidos
Content-Type: application/json
Authorization: Bearer {token}

{
  "data": "2025-03-21",
  "statusPedido": "INICIALIZADO",
  "itensPedido": [
    {
      "produto_id": 1,
      "quantidade": 2
    }
  ]
}
```

---

#### **2. Obter Pedido por ID**
```http
GET /pedidos/{id}
Authorization: Bearer {token}
```

---

#### **3. Listar Todos os Pedidos**
```http
GET /pedidos
Authorization: Bearer {token}
```

---

#### **4. Atualizar Pedido**
```http
PATCH /pedidos/{id}
Content-Type: application/json
Authorization: Bearer {token}

{
  "statusPedido": "FINALIZADO"
}
```

---

#### **5. Deletar Pedido**
```http
DELETE /pedidos/{id}
Authorization: Bearer {token}
```

---

### 💳 **PAGAMENTOS**

#### **1. Criar Pagamento**
```http
POST /pagamentos
Content-Type: application/json
Authorization: Bearer {token}

{
  "pedido_id": 1,
  "cliente_id": 1,
  "valor": 1500.00,
  "tipoPagamento": "PIX",
  "data": "2025-03-21"
}
```

---

#### **2. Obter Pagamento por ID**
```http
GET /pagamentos/{id}
Authorization: Bearer {token}
```

---

#### **3. Listar Todos os Pagamentos**
```http
GET /pagamentos
Authorization: Bearer {token}
```

---

#### **4. Atualizar Pagamento**
```http
PATCH /pagamentos/{id}
Content-Type: application/json
Authorization: Bearer {token}

{
  "valor": 1600.00,
  "tipoPagamento": "CARTAO"
}
```

---

#### **5. Finalizar Pagamento**
```http
PATCH /pagamentos/finalizar/{id}
Content-Type: application/json
Authorization: Bearer {token}

{
  "tipoPagamento": "PIX"
}
```
**Resposta:** Pagamento finalizado com status=FINALIZADO

---

#### **6. Deletar Pagamento**
```http
DELETE /pagamentos/{id}
Authorization: Bearer {token}
```

---

## 📊 Entidades e Modelo de Dados

### **1. Usuário (User)**
```java
- id: Long (PK)
- username: String (Único, obrigatório)
- password: String (Criptografada)
- role: UserRole (ADMIN, USER)
```
**Tabela:** `tb_users`

---

### **2. Cliente**
```java
- id: Long (PK)
- nome: String (Obrigatório, máx 60 caracteres)
- cpf: String (Único)
- contatos: List<String> (Coleção de contatos)
- pagamentos: List<Pagamento> (Relacionamento 1:N)
```
**Tabela:** `tb_clientes`
**Tabela Relacionada:** `tb_cliente_contatos`

---

### **3. Produto**
```java
- id: Long (PK)
- nome: String (Obrigatório, máx 60 caracteres)
- preco: BigDecimal (Obrigatório)
- quantidade: Integer (Obrigatório)
- validate: LocalDate (Data de validade)
- categorias: List<Categoria> (Relacionamento M:N)
- fornecedores: List<Fornecedor> (Relacionamento M:N)
```
**Tabela:** `tb_produtos`
**Tabelas de Junção:** `tb_produto_categoria`, `tb_produto_fornecedor`

---

### **4. Categoria**
```java
- id: Long (PK)
- nome: String (Único, obrigatório)
- produtos: List<Produto> (Relacionamento M:N)
```
**Tabela:** `tb_categorias`

---

### **5. Fornecedor**
```java
- id: Long (PK)
- nome: String (Obrigatório, máx 60 caracteres)
- cnpj: String (Único, obrigatório)
- contatos: List<String> (Coleção de contatos)
- produtos: List<Produto> (Relacionamento M:N)
```
**Tabela:** `tb_fornecedores`
**Tabela Relacionada:** `tb_fornecedores_contatos`

---

### **6. Pedido**
```java
- id: Long (PK)
- data: LocalDate (Data do pedido, padrão = hoje)
- statusPedido: Status (INICIALIZADO, CANCELADO, FINALIZADO, ERRO)
- itensPedido: List<ItemPedido> (Relacionamento 1:N, cascade)
- pagamento: Pagamento (Relacionamento 1:1)
```
**Tabela:** `tb_pedidos`
**Status padrão:** INICIALIZADO

---

### **7. ItemPedido**
```java
- id: Long (PK)
- pedido: Pedido (FK)
- produto: Produto (FK)
- quantidade: Integer
```
**Tabela:** `tb_itens_pedido`

---

### **8. Pagamento**
```java
- id: Long (PK)
- pedido: Pedido (FK, cascade)
- cliente: Cliente (FK, cascade)
- valor: BigDecimal (Calculado automaticamente)
- data: LocalDate (Data do pagamento, padrão = hoje)
- tipoPagamento: TipoPagamento (PIX, CARTAO, DINHEIRO)
- statusPagamento: Status (INICIALIZADO, FINALIZADO, CANCELADO, ERRO)
```
**Tabela:** `tb_pagamentos`
**Cálculo automático:** o valor é calculado automaticamente a partir dos itens do pedido

---

## 📋 DTOs (Data Transfer Objects)

### **ClienteRequestDTO** (Entrada)
```json
{
  "nome": "string",
  "cpf": "string",
  "contatos": ["string"]
}
```

### **ClienteResponseDTO** (Saída)
```json
{
  "id": "number",
  "nome": "string",
  "cpf": "string",
  "contatos": ["string"],
  "pagamentos": []
}
```

---

### **ProdutoRequestDTO** (Entrada)
```json
{
  "nome": "string",
  "preco": "number",
  "quantidade": "number",
  "validate": "date",
  "categorias": ["number"],
  "fornecedores": ["number"]
}
```

### **ProdutoResponseDTO** (Saída)
```json
{
  "id": "number",
  "nome": "string",
  "preco": "number",
  "quantidade": "number",
  "validate": "date",
  "categorias": [],
  "fornecedores": []
}
```

---

### **CategoriaRequestDTO** (Entrada)
```json
{
  "nome": "string"
}
```

### **CategoriaResponseDTO** (Saída)
```json
{
  "id": "number",
  "nome": "string",
  "produtos": []
}
```

---

### **FornecedorRequestDTO** (Entrada)
```json
{
  "nome": "string",
  "cnpj": "string",
  "contatos": ["string"]
}
```

### **FornecedorResponseDTO** (Saída)
```json
{
  "id": "number",
  "nome": "string",
  "cnpj": "string",
  "contatos": ["string"],
  "produtos": []
}
```

---

### **PedidoRequestDTO** (Entrada)
```json
{
  "data": "date",
  "statusPedido": "string",
  "itensPedido": []
}
```

### **PedidoResponseDTO** (Saída)
```json
{
  "id": "number",
  "data": "date",
  "statusPedido": "string",
  "itensPedido": [],
  "pagamento": null
}
```

---

### **PagamentoRequestDTO** (Entrada)
```json
{
  "pedido_id": "number",
  "cliente_id": "number",
  "valor": "number",
  "tipoPagamento": "string",
  "data": "date"
}
```

### **PagamentoResponseDTO** (Saída)
```json
{
  "id": "number",
  "pedido": {},
  "cliente": {},
  "valor": "number",
  "data": "date",
  "tipoPagamento": "string",
  "statusPagamento": "string"
}
```

---

### **LoginRequestDTO** (Entrada)
```json
{
  "username": "string",
  "password": "string"
}
```

### **LoginResponseDTO** (Saída)
```json
{
  "token": "string"
}
```

---

### **UserRequestDTO** (Entrada)
```json
{
  "username": "string",
  "password": "string",
  "role": "USER"
}
```

### **UserResponseDTO** (Saída)
```json
{
  "id": "number",
  "username": "string",
  "role": "string"
}
```

---

## 🏷️ Enumerações

### **Status**
```java
public enum Status {
    INICIALIZADO,    // Estado inicial
    CANCELADO,       // Operação cancelada
    FINALIZADO,      // Operação concluída
    ERRO             // Erro na operação
}
```

---

### **TipoPagamento**
```java
public enum TipoPagamento {
    PIX,             // Pagamento via PIX
    CARTAO,          // Pagamento via Cartão de Crédito/Débito
    DINHEIRO         // Pagamento em Dinheiro
}
```

---

### **UserRole**
```java
public enum UserRole {
    ADMIN,           // Administrador - acesso total
    USER             // Usuário comum - acesso limitado
}
```

---

## 🔧 Serviços

Os serviços contêm toda a lógica de negócio e operações que manipulam os dados.

### **BaseService (Superclasse)**
Fornece operações CRUD genéricas para todos os serviços:
- `save(DTO)` - Salva um novo objeto
- `getById(Long)` - Obtém por ID
- `update(Long, DTO)` - Atualiza um objeto
- `delete(Long)` - Deleta um objeto
- `getAll()` - Lista todos

---

### **ClienteService**
Estende `BaseService<ClienteRequestDTO, ClienteResponseDTO>`
- Operações CRUD para Cliente
- Mapeamento entre entidade e DTOs

---

### **ProdutoService**
Estende `BaseService<ProdutoRequestDTO, ProdutoResponseDTO>`
- Operações CRUD para Produto
- Relacionamento com Categorias e Fornecedores

---

### **CategoriaService**
Estende `BaseService<CategoriaRequestDTO, CategoriaResponseDTO>`
- Operações CRUD para Categoria
- Método especial: `findCategoriaByNome(String)`

---

### **FornecedorService**
Estende `BaseService<FornecedorRequestDTO, FornecedorResponseDTO>`
- Operações CRUD para Fornecedor

---

### **PedidoService**
Estende `BaseService<PedidoRequestDTO, PedidoResponseDTO>`
- Operações CRUD para Pedido
- Gerenciamento de itens do pedido

---

### **PagamentoService**
Estende `BaseService<PagamentoRequestDTO, PagamentoResponseDTO>`
- Operações CRUD para Pagamento
- Método especial: `finalizarPedido(Long, TipoPagamento)`
- Validação de pagamentos já finalizados (lança `PagamentoFinalizadoException`)

---

### **UserService**
- Gerenciamento de usuários
- Autenticação
- Geração de tokens JWT

---

## 💾 Repositórios

Os repositórios estendem `JpaRepository` e fornecem acesso aos dados persistidos.

```
ClienteRepository extends JpaRepository<Cliente, Long>
ProdutoRepository extends JpaRepository<Produto, Long>
CategoriaRepository extends JpaRepository<Categoria, Long>
FornecedorRepository extends JpaRepository<Fornecedor, Long>
PedidoRepository extends JpaRepository<Pedido, Long>
PagamentoRepository extends JpaRepository<Pagamento, Long>
UserRepository extends JpaRepository<User, Long>
```

**Métodos customizados:**
- `CategoriaRepository.findByNome(String)` - Busca categoria por nome

---

## 🔐 Configuração de Segurança

### **PasswordEncoderConfig**
- Define o encoder de senha: `BCryptPasswordEncoder`
- Senhas são criptografadas com bcrypt

### **SecurityConfig**
- Configuração do Spring Security
- Definição de rotas públicas e protegidas
- Endpoint `/auth/**` é público (login e registro)
- Demais endpoints requerem autenticação JWT

### **SecurityFilter**
- Filtro customizado que valida tokens JWT
- Intercepta requisições e valida Authorization Header
- Extrai o token JWT e valida assinatura
- Se válido, autoriza; se inválido, retorna 403

### **TokenUtil**
- Geração de tokens JWT utilizando a biblioteca `java-jwt`
- Incluem expiration time
- Assinados com `JWT_SECRET` (variável de ambiente)

### **Fluxo de Autenticação:**
1. Usuário faz login com username/password
2. Spring Security autentica as credenciais
3. Se correto, `TokenUtil` gera um token JWT
4. Token é retornado ao cliente
5. Cliente inclui token no header `Authorization: Bearer {token}`
6. `SecurityFilter` valida o token em cada requisição
7. Se válido, requisição é processada; se inválido, retorna 403

---

## 🧪 Testes Unitários

Os testes são implementados com **JUnit 5** e **Mockito**.

### **Tipo de Testes:**
- **Unit Tests** - Testam métodos individuais isoladamente
- **Controller Tests** - Testam endpoints usando MockMvc
- **Annotations Customizadas** - `@WebMvcTestWithoutSecurity` para testes sem segurança

### **Teste Exemplo: ClienteControllerTest**

```java
@WebMvcTestWithoutSecurity(controllers = ClienteController.class)
class ClienteControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockitoBean
    private ClienteService clienteService;
```

**Testes implementados:**

1. **shouldCreateClienteSuccessfully()** - Verifica criação de cliente
   - Entrada: ClienteRequestDTO válido
   - Mockito: configura ClienteService para retornar ClienteResponseDTO
   - Resultado: Verifica status 200 e campos da resposta

2. **shouldFindByIdClienteSuccessfully()** - Verifica busca por ID
   - Entrada: ID válido
   - Resultado: Retorna cliente completo

3. **shouldFindAllClienteSuccessfully()** - Verifica listagem
   - Resultado: Retorna lista de clientes

4. **Testes similares para todos os controladores:**
   - ProdutoControllerTest
   - PagamentoControllerTest
   - FornecedorControllerTest
   - CategoriaControllerTest
   - AuthenticationControllerTest
   - PedidoControllerTest

---

## 🚀 Como Executar

### **Pré-requisitos:**
- Java 21 instalado
- PostgreSQL em execução
- Maven instalado (ou usar mvnw fornecido)
- Docker (opcional, para banco de dados)

---

### **1. Configurar Variáveis de Ambiente**
Criar arquivo `.env` ou configurar no SO:

```bash
DB_URL=jdbc:postgresql://localhost:5432/gerenciador_db
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET=sua_chave_secreta_muito_segura
SERVER_PORT=8081
```

---

### **2. Executar com Docker (Opcional)**
Se tiver `compose.yaml`:
```bash
docker compose up
```

---

### **3. Compilar o Projeto**
```bash
# Windows
mvnw clean install

# Linux/Mac
./mvnw clean install
```

---

### **4. Executar a Aplicação**
```bash
# Maven
mvn spring-boot:run

# Ou diretamente executar
java -jar target/gerenciador-estabelecimento-0.0.1-SNAPSHOT.jar
```

---

### **5. Acessar a API**
- **URL Base:** `http://localhost:8081`
- **OpenAPI/Swagger:** `http://localhost:8081/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8081/v3/api-docs`

---

### **6. Executar Testes**
```bash
# Todos os testes
mvn test

# Teste específico
mvn test -Dtest=ClienteControllerTest

# Com cobertura
mvn clean test jacoco:report
```

---

## 📦 Dependências

### **Spring Boot**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
Framework web e REST

---

### **JPA/Hibernate**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```
ORM para persistência de dados

---

### **PostgreSQL**
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```
Driver do banco de dados PostgreSQL

---

### **Spring Security**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
Segurança e autenticação

---

### **Java JWT**
```xml
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>4.5.0</version>
</dependency>
```
Geração e validação de tokens JWT

---

### **SpringDoc OpenAPI**
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.5</version>
</dependency>
```
Documentação automática Swagger/OpenAPI

---

### **Testing**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.21.0</version>
    <scope>test</scope>
</dependency>
```
JUnit 5 e Mockito para testes

---

### **JaCoCo (Coverage)**
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.14</version>
</plugin>
```
Relatório de cobertura de código

---

### **DevTools (Opcional)**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```
Hot reload durante desenvolvimento

---

## 📁 Estrutura de Diretórios

```
Gerenciador-Estabelecimento/
├── src/
│   ├── main/
│   │   ├── java/com/gereciador/estabelecimento/
│   │   │   ├── GerenciadorEstabelecimentoApplication.java (Main)
│   │   │   ├── config/
│   │   │   │   ├── PasswordEncoderConfig.java
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── SecurityFilter.java
│   │   │   │   └── SpringdocConfig.java
│   │   │   ├── controllers/
│   │   │   │   ├── AutheticationController.java
│   │   │   │   ├── ClienteController.java
│   │   │   │   ├── ProdutoController.java
│   │   │   │   ├── CategoriaController.java
│   │   │   │   ├── FornecedorController.java
│   │   │   │   ├── PedidoController.java
│   │   │   │   ├── PagamentoController.java
│   │   │   │   ├── advice/ (Exception Handling)
│   │   │   │   └── dto/
│   │   │   │       ├── request/ (DTOs de entrada)
│   │   │   │       └── response/ (DTOs de saída)
│   │   │   ├── entities/
│   │   │   │   ├── User.java
│   │   │   │   ├── Cliente.java
│   │   │   │   ├── Produto.java
│   │   │   │   ├── Categoria.java
│   │   │   │   ├── Fornecedor.java
│   │   │   │   ├── Pedido.java
│   │   │   │   ├── ItemPedido.java
│   │   │   │   └── Pagamento.java
│   │   │   ├── enums/
│   │   │   │   ├── Status.java
│   │   │   │   ├── TipoPagamento.java
│   │   │   │   └── UserRole.java
│   │   │   ├── exceptions/
│   │   │   │   ├── EstabelecimentoException.java
│   │   │   │   ├── ClienteNotFoundException.java
│   │   │   │   ├── ProdutoNotFoundException.java
│   │   │   │   ├── CategoriaNotFoundException.java
│   │   │   │   ├── FornecedorNotFoundException.java
│   │   │   │   ├── PedidoNotFoundException.java
│   │   │   │   ├── PagamentoNotFoundException.java
│   │   │   │   ├── PagamentoFinalizadoException.java
│   │   │   │   └── UserNotFoundException.java
│   │   │   ├── repositories/
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── ClienteRepository.java
│   │   │   │   ├── ProdutoRepository.java
│   │   │   │   ├── CategoriaRepository.java
│   │   │   │   ├── FornecedorRepository.java
│   │   │   │   ├── PedidoRepository.java
│   │   │   │   └── PagamentoRepository.java
│   │   │   ├── services/
│   │   │   │   ├── BaseService.java (Superclasse)
│   │   │   │   ├── UserService.java
│   │   │   │   ├── ClienteService.java
│   │   │   │   ├── ProdutoService.java
│   │   │   │   ├── CategoriaService.java
│   │   │   │   ├── FornecedorService.java
│   │   │   │   ├── PedidoService.java
│   │   │   │   └── PagamentoService.java
│   │   │   ├── mapper/
│   │   │   │   └── (Conversão entre entidades e DTOs)
│   │   │   └── util/
│   │   │       └── TokenUtil.java (Geração de JWT)
│   │   └── resources/
│   │       └── application.properties (Configuração)
│   └── test/
│       └── java/com/gereciador/estabelecimento/
│           ├── annotations/
│           │   └── WebMvcTestWithoutSecurity.java
│           └── controllers/
│               ├── ClienteControllerTest.java
│               ├── ProdutoControllerTest.java
│               ├── CategoriaControllerTest.java
│               ├── FornecedorControllerTest.java
│               ├── PedidoControllerTest.java
│               ├── PagamentoControllerTest.java
│               └── AutheticationControllerTest.java
├── pom.xml (Configuração Maven)
├── mvnw / mvnw.cmd (Maven Wrapper)
├── compose.yaml (Docker Compose)
└── README.md
```

---

## 📝 Tratamento de Erros

A aplicação implementa um tratamento customizado de exceções via `@ControllerAdvice`:

| Exceção | Status HTTP | Mensagem |
|---------|-----------|----------|
| `ClienteNotFoundException` | 404 | Cliente não encontrado |
| `ProdutoNotFoundException` | 404 | Produto não encontrado |
| `CategoriaNotFoundException` | 404 | Categoria não encontrada |
| `FornecedorNotFoundException` | 404 | Fornecedor não encontrado |
| `PedidoNotFoundException` | 404 | Pedido não encontrado |
| `PagamentoNotFoundException` | 404 | Pagamento não encontrado |
| `UserNotFoundException` | 404 | Usuário não encontrado |
| `PagamentoFinalizadoException` | 400 | Pagamento já foi finalizado |
| `EstadoInvalidoException` | 400 | Estado/Status inválido |
| `EstabelecimentoException` | 500 | Erro genérico da aplicação |

---

## 🔒 Segurança da API

### **Autenticação JWT**
- Tokens têm expiração (configurável)
- Assinados com chave secreta (JWT_SECRET)
- Validados em cada requisição protegida

### **Senhas**
- Criptografadas com BCrypt
- Nunca armazenadas em plain text

### **CORS**
- Configurável no `SecurityConfig`
- Permite requisições de origens específicas

### **HTTPS**
- Recomendado em produção
- Configurável via `application.properties`

---

## 💡 Boas Práticas Utilizadas

1. **Separação de Responsabilidades** - Controllers, Services, Repositories
2. **DTOs** - Não expõe entidades diretamente
3. **Mappers** - Converte entidades ↔ DTOs
4. **Injeção de Dependência** - Utiliza @Autowired e constructor injection
5. **Exceptions Customizadas** - Tratamento de erros específicos
6. **Testes Automatizados** - Cobertura com JUnit e Mockito
7. **Documentação OpenAPI** - Swagger UI automático
8. **Versionamento de API** - URL base única (sem versão explícita)
9. **Paginação** - Para listas (futuro)
10. **Validação** - Via @Validated e custom validators

---

## 🧪 Testes Prático (Passo-a-Passo)

### **1. Setup Inicial**

```bash
# Banco de Dados
docker run --name postgres-db \
  -e POSTGRES_DB=gerenciador_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:latest

# Compilar
mvnw clean install

# Executar
mvnw spring-boot:run
```

### **2. Teste de Autenticação**

```bash
# Registrar
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123","role":"ADMIN"}'

# Login (copie o token!)
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Salve: TOKEN="seu_token_aqui"
```

### **3. Teste de Categorias**

```bash
# Criar
curl -X POST http://localhost:8081/categorias \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"nome":"Eletrônicos"}'

# Listar
curl http://localhost:8081/categorias \
  -H "Authorization: Bearer $TOKEN"

# Buscar por nome
curl "http://localhost:8081/categorias/filter?nome=Eletrônicos" \
  -H "Authorization: Bearer $TOKEN"
```

### **4. Teste de Produtos**

```bash
# Criar
curl -X POST http://localhost:8081/produtos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "nome":"Notebook",
    "preco":3500.00,
    "quantidade":10,
    "validate":"2025-12-31",
    "categorias":[1],
    "fornecedores":[1]
  }'

# Listar
curl http://localhost:8081/produtos \
  -H "Authorization: Bearer $TOKEN"
```

### **5. Teste de Clientes**

```bash
# Criar
curl -X POST http://localhost:8081/clientes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "nome":"João Silva",
    "cpf":"123.456.789-00",
    "contatos":["joao@email.com"]
  }'

# Listar
curl http://localhost:8081/clientes \
  -H "Authorization: Bearer $TOKEN"
```

### **6. Executar Testes Unitários**

```bash
# Todos os testes
mvnw test

# Teste específico
mvnw test -Dtest=ClienteControllerTest

# Com cobertura
mvnw clean test jacoco:report
```

---

## 🏗️ Arquitetura e Padrões

### **Camadas da Aplicação**

```
┌──────────────────────────────────────┐
│   REST Clients (Postman, Frontend)   │
└────────────────┬─────────────────────┘
                 │
┌────────────────▼─────────────────────┐
│ Controller Layer (ClienteController) │
│ - Recebe requisições HTTP            │
│ - Valida DTOs                        │
│ - Chama Services                     │
└────────────────┬─────────────────────┘
                 │
┌────────────────▼─────────────────────┐
│ Service Layer (ClienteService)       │
│ - Lógica de negócio                 │
│ - Validações                         │
│ - Chamar Repositories                │
└────────────────┬─────────────────────┘
                 │
┌────────────────▼─────────────────────┐
│ Repository Layer (ClienteRepository) │
│ - Acesso a dados                     │
│ - Queries customizadas               │
│ - CRUD operations                    │
└────────────────┬─────────────────────┘
                 │
┌────────────────▼─────────────────────┐
│      Entity & Database               │
│ - Cliente.java (@Entity)             │
│ - PostgreSQL                         │
└──────────────────────────────────────┘
```

### **Mappers (Entity ↔ DTO)**

Convertem dados entre camadas:

```java
// Entity → ResponseDTO
ClienteResponseDTO dto = mapper.toResponseDTO(cliente);

// RequestDTO → Entity
Cliente entity = mapper.toEntity(requestDTO);
```

Benefícios:
- ✅ Não expõe estrutura interna
- ✅ Flexibilidade para mudanças
- ✅ Segurança nos dados

### **Fluxo Completo de uma Requisição**

```
1. CLIENT
   └→ POST /clientes
      Headers: Authorization: Bearer {token}
      Body: {nome, cpf, contatos}

2. SECURITY FILTER
   └→ Valida token JWT
   └→ Se inválido: retorna 403

3. CONTROLLER
   └→ Recebe ClienteRequestDTO
   └→ Chama service.save(dto)

4. SERVICE
   └→ Valida dados
   └→ Mapper: DTO → Entity
   └→ Chama repository.save()

5. REPOSITORY
   └→ Executa SQL INSERT
   └→ Retorna Entity com ID

6. MAPPER
   └→ Converte Entity → ResponseDTO

7. CONTROLLER
   └→ Retorna ResponseDTO (JSON)

8. CLIENT
   └→ Recebe status 200 + dados
```

### **Padrões de Design Utilizados**

| Padrão | Uso | Exemplo |
|--------|-----|---------|
| **DTO** | Transferência de dados | ClienteRequestDTO |
| **Mapper** | Conversão | ClienteMapper |
| **Repository** | Acesso a dados | ClienteRepository |
| **Service** | Lógica de negócio | ClienteService |
| **Dependency Injection** | Injeção de deps | @Autowired |
| **Filter** | Interceptação | SecurityFilter |
| **Advice** | Tratamento global | GlobalExceptionHandler |

---

## 🛡️ Configuração de Segurança Detalhada

### **application.properties**

```properties
# Server
server.port=8081
server.servlet.context-path=/

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/gerenciador_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
api.security.token.secret=sua_chave_secreta
api.security.token.expiration=3600000

# OpenAPI
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

### **SecurityConfig**

- Disable CSRF (JWT não precisa)
- Session stateless (cada request autossuficiente)
- Rotas públicas: `/auth/**`
- Rotas protegidas: tudo mais
- JWT Filter validando tokens

### **TokenUtil - JWT**

```java
// Gerar token
String token = tokenUtil.generateToken(user);

// Validar token
String username = tokenUtil.validateToken(token);
```

---

## 💾 DTOs Detalhados

### **ClienteRequestDTO**
```json
{
  "nome": "string",
  "cpf": "string",
  "contatos": ["string"]
}
```

### **ProdutoRequestDTO**
```json
{
  "nome": "string",
  "preco": 0,
  "quantidade": 0,
  "validate": "2025-12-31",
  "categorias": [1, 2],
  "fornecedores": [1]
}
```

(Todos os DTOs listados na seção de DTOs acima)

---

## 📚 Referências Úteis

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Java JWT Library](https://github.com/auth0/java-jwt)
- [SpringDoc OpenAPI](https://springdoc.org/)
- [JUnit 5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
- [PostgreSQL](https://www.postgresql.org/)

---

**Versão:** 1.0  
**Data de Atualização:** 21/03/2025  