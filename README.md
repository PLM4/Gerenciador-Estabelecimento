# 🏪 Gerenciador de Estabelecimento

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen)
![Java](https://img.shields.io/badge/Java-21-red)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-blue)

Aplicação backend completa para gerenciamento de estabelecimentos com API REST, autenticação JWT, e gerenciamento de clientes, produtos, pedidos e pagamentos.

---

## 📚 Documentação

**3 arquivos essenciais:**
1. **README.md** (este) - Quick Start e visão geral
2. **DOCUMENTATION.md** - Endpoints, entidades, testes e arquitetura
3. **QUICK_REFERENCE.md** - Cheat sheet e referência rápida

---

## 🚀 Quick Start (5 minutos)

### **1. Pré-requisitos**
```bash
Java 21
PostgreSQL (ou Docker)
Maven (incluído - mvnw)
```

### **2. Crie arquivo `.env` na raiz**
```
DB_URL=jdbc:postgresql://localhost:5432/gerenciador_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=sua_chave_secreta_super_segura_12345678
SERVER_PORT=8081
```

### **3. Banco de Dados (Docker)**
```bash
docker run --name postgres-db \
  -e POSTGRES_DB=gerenciador_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:latest
```

### **4. Executar**
```bash
# Windows
mvnw spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

### **5. Acessar API**
- **Swagger UI:** http://localhost:8081/swagger-ui.html
- **API Base:** http://localhost:8081

---

## 📊 Stack Tecnológico

| Componente | Versão |
|-----------|--------|
| Spring Boot | 3.4.2 |
| Java | 21 |
| PostgreSQL | Latest |
| Spring Security | Latest |
| JWT (java-jwt) | 4.5.0 |
| JUnit 5 | 5 |
| Mockito | 5.21.0 |

---

## 🔐 Autenticação

Endpoints públicos (sem tokens):
- `POST /auth/register` - Registrar
- `POST /auth/login` - Login

Todos os demais endpoints requerem token JWT válido no header: `Authorization: Bearer {token}`

---

## 📡 Recursos

- ✅ 39+ endpoints documentados
- ✅ 8 entidades (Cliente, Produto, Categoria, Fornecedor, Pedido, Pagamento, Usuário, ItemPedido)
- ✅ Autenticação JWT com criptografia BCrypt
- ✅ PostgreSQL com Hibernate
- ✅ Swagger/OpenAPI automático
- ✅ 7+ testes unitários com JUnit 5 e Mockito
- ✅ Tratamento de erros customizado

---

## 🧪 Testes

```bash
# Executar testes
mvnw test

# Com cobertura (jacoco)
mvnw clean test jacoco:report
```

---

## 📚 Consulte a Documentação

### **[DOCUMENTATION.md](./DOCUMENTATION.md)** - Guia Completo
- 🔌 Todos os 39+ endpoints com exemplos cURL
- 📊 Todas as 8 entidades explicadas
- 📋 14+ DTOs (Request/Response)
- 🔧 Serviços e Repositories
- 🛡️ Segurança e JWT (completo)
- 🧪 Como testar cada endpoint
- 🏗️ Arquitetura e padrões
- ⚠️ Tratamento de erros
- 📞 Troubleshooting

### **[QUICK_REFERENCE.md](./QUICK_REFERENCE.md)** - Referência Rápida
- ⚡ Comandos e endpoints em tabelas
- 🔑 Enums (Status, TipoPagamento, UserRole)
- 💾 Estrutura do banco de dados
- 📊 Erros comuns e soluções
- 🛠️ Comandos úteis

---

## 🎯 Fluxo Completo - Exemplo

```bash
# 1️⃣ Registrar usuário
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123","role":"ADMIN"}'

# 2️⃣ Login e obter token
RESPONSE=$(curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}')

TOKEN=$(echo $RESPONSE | jq -r '.token')

# 3️⃣ Criar categoria
curl -X POST http://localhost:8081/categorias \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"nome":"Eletrônicos"}'

# 4️⃣ Listar categorias
curl http://localhost:8081/categorias \
  -H "Authorization: Bearer $TOKEN"
```

---

## 📁 Estrutura do Projeto

```
Gerenciador-Estabelecimento/
├── src/
│   ├── main/java/com/gereciador/estabelecimento/
│   │   ├── config/              # Security, JWT, OpenAPI
│   │   ├── controllers/         # REST Controllers (7)
│   │   │   ├── dto/            # DTOs Request/Response
│   │   │   └── advice/         # Exception Handlers
│   │   ├── services/           # Serviços (8)
│   │   ├── repositories/       # Data Access (7)
│   │   ├── entities/           # JPA Entities (8)
│   │   ├── enums/              # Status, TipoPagamento, UserRole
│   │   ├── exceptions/         # Exceções Customizadas (9)
│   │   └── util/               # TokenUtil
│   ├── test/java/              # Testes (7+ testes)
│   └── resources/              # application.properties
├── pom.xml                      # Dependências Maven
├── compose.yaml                 # Docker Compose
├── README.md                    # Este arquivo (Quick Start)
├── DOCUMENTATION.md             # Documentação Completa
└── QUICK_REFERENCE.md           # Referência Rápida
```

---

## 📊 Resumo de Endpoints

| Recurso | GET | POST | PATCH | DELETE |
|---------|-----|------|-------|--------|
| Autenticação | - | register, login | - | - |
| Clientes | ✅ | ✅ | ✅ | ✅ |
| Produtos | ✅ | ✅ | ✅ | ✅ |
| Categorias | ✅ | ✅ | ✅ | ✅ |
| Fornecedores | ✅ | ✅ | ✅ | ✅ |
| Pedidos | ✅ | ✅ | ✅ | ✅ |
| Pagamentos | ✅ | ✅ | ✅ | ✅ |

---

## 🔍 Recursos Especiais

- **Categoria por nome:** `GET /categorias/filter?nome=Eletrônicos`
- **Finalizar pagamento:** `PATCH /pagamentos/finalizar/{id}`
- **Health Check:** `GET /actuator/health`
- **Metrics:** `GET /actuator/metrics`

---

## 🛠️ Setup Variáveis de Ambiente

Crie `.env` na raiz ou configure no SO:

```bash
# Base de Dados
DB_URL=jdbc:postgresql://localhost:5432/gerenciador_db
DB_USERNAME=postgres
DB_PASSWORD=postgres

# JWT Token Secret
JWT_SECRET=sua_chave_secreta_muito_segura_123456

# Server
SERVER_PORT=8081
```

---

## 🚨 Troubleshooting Rápido

| Erro | Solução |
|------|---------|
| `Connection refused` | Iniciar Docker: `docker run ...` |
| `Port 8081 in use` | Mudar SERVER_PORT |
| `401 Unauthorized` | Incluir token: `Authorization: Bearer {token}` |
| `404 Not Found` | Verificar ID do recurso |

Para mais: Veja [DOCUMENTATION.md](./DOCUMENTATION.md#-tratamento-de-erros)

---

## 🎓 Próximos Passos

1. **Consulte [DOCUMENTATION.md](./DOCUMENTATION.md)** para:
   - Todos os endpoints em detalhede
   - Como testar cada um
   - Padrões de design
   - Arquitetura interna

2. **Consulte [QUICK_REFERENCE.md](./QUICK_REFERENCE.md)** para:
   - Referência rápida de comandos
   - Tabelas de endpoints
   - Estrutura do BD

---

## 📞 Informações

- **Versão:** 1.0
- **Data:** 21/03/2025

---

![Esquema Conceitual](aps-diagramas/esquema-conceitual.png)
