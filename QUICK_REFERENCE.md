# 📋 Quick Reference - Guia Rápido de Consulta

## 🚀 Iniciar em 6 Passos

```bash
# 1. Abrir Terminal
cd seu_projeto

# 2. Banco de Dados
docker run --name postgres-db \
  -e POSTGRES_DB=gerenciador_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:latest

# 3. Variáveis de Ambiente (.env)
DB_URL=jdbc:postgresql://localhost:5432/gerenciador_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=seu_segredo_aqui
SERVER_PORT=8081

# 4. Compilar
mvnw clean install

# 5. Executar
mvnw spring-boot:run

# 6. Acessar
http://localhost:8081/swagger-ui.html
```

---

## 🔑 Autenticação (Sempre Primeiro!)

```bash
# 1. Registrar
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "seu_user",
    "password": "sua_senha",
    "role": "ADMIN"
  }'

# 2. Login (copie o token!)
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "seu_user",
    "password": "sua_senha"
  }'

# 3. Use em todas as requisições:
curl -X GET http://localhost:8081/clientes \
  -H "Authorization: Bearer seu_token_aqui"
```

---

## 📡 Cheat Sheet de Endpoints

### **POST (Criar)**
```bash
# Cliente
post /clientes {"nome": "...", "cpf": "...", "contatos": [...]}

# Produto
post /produtos {"nome": "...", "preco": 0, "quantidade": 0, ...}

# Categoria
post /categorias {"nome": "..."}

# Fornecedor
post /fornecedores {"nome": "...", "cnpj": "...", "contatos": [...]}

# Pedido
post /pedidos {"data": "...", "statusPedido": "INICIALIZADO", ...}

# Pagamento
post /pagamentos {"pedido_id": 1, "cliente_id": 1, "valor": 0, ...}
```

### **GET (Ler)**
```bash
# Listar todos
get /clientes
get /produtos
get /categorias
get /fornecedores
get /pedidos
get /pagamentos

# Obter por ID
get /clientes/1
get /produtos/1
get /categorias/1
get /fornecedores/1
get /pedidos/1
get /pagamentos/1

# Buscar específico
get /categorias/filter?nome=Eletrônicos
```

### **PATCH (Atualizar)**
```bash
# Atualizar
patch /clientes/1 {"nome": "novo_nome"}
patch /produtos/1 {"preco": 4000}
patch /categorias/1 {"nome": "novo_nome"}
patch /fornecedores/1 {"nome": "novo_nome"}
patch /pedidos/1 {"statusPedido": "FINALIZADO"}
patch /pagamentos/1 {"valor": 2000}

# Finalizar pagamento (especial)
patch /pagamentos/finalizar/1 {"tipoPagamento": "PIX"}
```

### **DELETE (Remover)**
```bash
# Deletar
delete /clientes/1
delete /produtos/1
delete /categorias/1
delete /fornecedores/1
delete /pedidos/1
delete /pagamentos/1
```

---

## 📊 Status Codes

| Código | Significado | Exemplo |
|--------|------------|---------|
| **200** | OK - Sucesso | GET, POST, PATCH |
| **204** | No Content - Sucesso sem corpo | DELETE |
| **400** | Bad Request - Dado inválido | Pagamento já finalizado |
| **401** | Unauthorized - Token inválido | Token expirado |
| **403** | Forbidden - Acesso negado | Token não enviado |
| **404** | Not Found - Recurso não existe | ID inválido |
| **500** | Server Error - Erro interno | Erro não tratado |

---

## 🏷️ Enums (Tipos)

### **Status** (Pedido/Pagamento)
```
INICIALIZADO   → Recém criado
FINALIZADO     → Concluído com sucesso
CANCELADO      → Cancelado
ERRO           → Erro durante processo
```

### **TipoPagamento**
```
PIX            → Pagamento via PIX
CARTAO         → Cartão de crédito/débito
DINHEIRO       → Dinheiro
```

### **UserRole** (Permissões)
```
ADMIN          → Acesso total
USER           → Acesso limitado
```

---

## 🧪 Testes Rápido

```bash
# Todos os testes
mvnw test

# Teste específico
mvnw test -Dtest=ClienteControllerTest

# Com coverage
mvnw clean test jacoco:report

# Ver relatório (após coverage)
start target/site/jacoco/index.html
```

---

## 📋 Fluxo Típico de Negócio

```
1️⃣  Registrar/Login
    ↓
2️⃣  Criar Categorias
    ↓
3️⃣  Criar Fornecedores
    ↓
4️⃣  Criar Produtos (com categorias + fornecedores)
    ↓
5️⃣  Criar Clientes
    ↓
6️⃣  Criar Pedidos (com itens/produtos)
    ↓
7️⃣  Criar Pagamento (associado ao pedido)
    ↓
8️⃣  Finalizar Pagamento (mudar status)
```

---

## 🔍 Principais Headers

| Header | Valor | Obrigatório |
|--------|-------|-----------|
| `Content-Type` | `application/json` | Para POST/PATCH |
| `Authorization` | `Bearer {token}` | Exceto /auth/** |
| `Accept` | `application/json` | Recomendado |

---

## ⚠️ Erros Comuns

| Erro | Causa | Solução |
|------|-------|--------|
| `401 Unauthorized` | Sem token | Fazer login primeiro |
| `403 Forbidden` | Token inválido | Verificar JWT_SECRET |
| `404 Not Found` | ID não existe | Verificar ID correto |
| `Connection refused` | DB desligado | Iniciar Docker |
| `Port 8081 in use` | Porta ocupada | Mudar SERVER_PORT |
| `JDBC Connection Error` | Credenciais erradas | Verificar .env |

---

## 📁 Arquivos de Documentação

| Arquivo | Conteúdo | Quando Consultar |
|---------|----------|----------------|
| **README_DOCUMENTACAO.md** | Visão Geral | Começar aqui |
| **DOCUMENTATION.md** | Endpoints Completos | Consultar endpoint específico |
| **TESTING_GUIDE.md** | Como Testar | Testar a aplicação |
| **ARCHITECTURE.md** | Detalhes Técnicos | Entender código |
| **QUICK_REFERENCE.md** | Este arquivo | Referência rápida |

---

## 💾 Estrutura BD (Tabelas)

```
tb_users
├── id (PK)
├── username (unique)
├── password (bcrypt)
└── role (ADMIN, USER)

tb_clientes
├── id (PK)
├── nome
├── cpf (unique)
└── tb_cliente_contatos (lista de contatos)

tb_categorias
├── id (PK)
└── nome (unique)

tb_fornecedores
├── id (PK)
├── nome
├── cnpj (unique)
└── tb_fornecedores_contatos (lista)

tb_produtos
├── id (PK)
├── nome
├── preco
├── quantidade
├── validate (data)
├── tb_produto_categoria (M:N)
└── tb_produto_fornecedor (M:N)

tb_pedidos
├── id (PK)
├── data
├── statusPedido
└── tb_itens_pedido (1:N)

tb_itens_pedido
├── id (PK)
├── pedido_id (FK)
├── produto_id (FK)
└── quantidade

tb_pagamentos
├── id (PK)
├── pedido_id (FK)
├── cliente_id (FK)
├── valor
├── data
├── tipoPagamento
└── statusPagamento
```

---

## 🛠️ Comandos Úteis

```bash
# Limpar e recompilar
mvnw clean install

# Apenas compilar
mvnw compile

# Apenas testes
mvnw test -q

# Testes com detalhes
mvnw test -X

# Kickar todas as portas
# Windows
netstat -ano | findstr :8081

# Linux/Mac
lsof -i :8081

# Matar processo
# Windows
taskkill /PID <PID> /F

# Linux/Mac
kill -9 <PID>

# Ver logs no Linux/Mac
tail -f logs/app.log

# Git commands (se usar)
git status
git add .
git commit -m "mensagem"
git push
```

---

## 📊 Exemplo Completo em JSON

```json
{
  "categoria": {
    "id": 1,
    "nome": "Eletrônicos"
  },
  "fornecedor": {
    "id": 1,
    "nome": "Tech Supplies",
    "cnpj": "12.345.678/0001-90",
    "contatos": ["vendas@tech.com"]
  },
  "produto": {
    "id": 1,
    "nome": "Notebook Dell",
    "preco": 3500.00,
    "quantidade": 10,
    "validate": "2025-12-31",
    "categorias": [{ "id": 1, "nome": "Eletrônicos" }],
    "fornecedores": [{ "id": 1, "nome": "Tech Supplies" }]
  },
  "cliente": {
    "id": 1,
    "nome": "João Silva",
    "cpf": "123.456.789-00",
    "contatos": ["joao@email.com"]
  },
  "pedido": {
    "id": 1,
    "data": "2025-03-21",
    "statusPedido": "INICIALIZADO",
    "itensPedido": [
      {
        "id": 1,
        "produto": { "id": 1, "nome": "Notebook Dell" },
        "quantidade": 2
      }
    ]
  },
  "pagamento": {
    "id": 1,
    "pedido": { "id": 1 },
    "cliente": { "id": 1 },
    "valor": 3500.00,
    "data": "2025-03-21",
    "tipoPagamento": "PIX",
    "statusPagamento": "FINALIZADO"
  }
}
```

---

## 🔗 Links Úteis

| Recurso | Link |
|---------|------|
| Swagger UI | http://localhost:8081/swagger-ui.html |
| OpenAPI JSON | http://localhost:8081/v3/api-docs |
| Health Check | http://localhost:8081/actuator/health |
| Metrics | http://localhost:8081/actuator/metrics |

---

## ✅ Checklist de Desenvolvimento

- [ ] Banco dados rodando
- [ ] Variáveis de ambiente criadas
- [ ] Aplicação compilada sem erros
- [ ] Aplicação iniciada com sucesso
- [ ] Token JWT obtido com sucesso
- [ ] Endpoints testados no Postman/cURL
- [ ] Todos os testes passando
- [ ] Swagger UI acessível
- [ ] Documentação consultada

---

## 🎯 Próximos Passos

1. **Aprender Endpoints:** Leia [DOCUMENTATION.md](./DOCUMENTATION.md)
2. **Executar Testes:** `mvnw test`
3. **Usar Swagger:** http://localhost:8081/swagger-ui.html

---

**Última atualização:** 21/03/2025  
