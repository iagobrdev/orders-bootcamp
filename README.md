# API REST - Sistema de Pedidos

## 📋 Descrição

API REST desenvolvida em Java com Spring Boot para gerenciamento de pedidos, seguindo o padrão arquitetural MVC. O sistema permite gerenciar clientes, produtos e pedidos com operações CRUD completas, validações de negócio e persistência em banco de dados PostgreSQL.

## 🏗️ Arquitetura

### Padrão MVC (Model-View-Controller)
- **Model**: Entidades JPA com relacionamentos
- **View**: Controllers REST com documentação OpenAPI
- **Controller**: Services com lógica de negócio e Repositories para persistência

### Estrutura de Pacotes
```
src/main/java/com/br/bootcamp/orders/
├── OrdersApplication.java              # Classe principal da aplicação
├── model/                             # Camada de entidades
│   ├── Cliente.java                   # Entidade Cliente
│   ├── Produto.java                   # Entidade Produto
│   ├── Pedido.java                    # Entidade Pedido
│   ├── ItemPedido.java                # Entidade ItemPedido
│   └── enums/                         # Enums centralizados
│       ├── StatusPedido.java          # Status dos pedidos
│       ├── CategoriaProduto.java      # Categorias de produtos
│       └── TipoPagamento.java         # Tipos de pagamento
├── repository/                        # Camada de acesso a dados
│   ├── ClienteRepository.java         # Repository Cliente
│   ├── ProdutoRepository.java         # Repository Produto
│   └── PedidoRepository.java          # Repository Pedido
├── service/                           # Camada de serviços
│   ├── contracts/                     # Interfaces dos serviços
│   │   ├── IClienteService.java       # Interface Cliente
│   │   ├── IProdutoService.java       # Interface Produto
│   │   └── IPedidoService.java        # Interface Pedido
│   ├── util/                          # Classes utilitárias
│   │   ├── PedidoValidator.java       # Validações de pedido
│   │   └── PedidoCalculator.java      # Cálculos de pedido
│   ├── ClienteServiceImpl.java        # Implementação Cliente
│   ├── ProdutoServiceImpl.java        # Implementação Produto
│   └── PedidoServiceImpl.java         # Implementação Pedido
└── controller/                        # Camada de apresentação
    ├── config/                        # Configurações
    │   └── OpenApiConfig.java         # Configuração OpenAPI
    ├── ClienteController.java         # Controller Cliente
    ├── ProdutoController.java         # Controller Produto
    └── PedidoController.java          # Controller Pedido
```

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL** (via Docker)
- **Flyway** (Migrações de banco)
- **Lombok** (Redução de boilerplate)
- **OpenAPI 3** (Documentação da API)
- **Docker Compose** (Infraestrutura)

## 📦 Pré-requisitos

- Java 21 ou superior
- Docker e Docker Compose
- Maven 3.6+

## 🔧 Configuração e Execução

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd orders
```

### 2. Inicie o banco de dados
```bash
docker-compose up -d
```

### 3. Execute a aplicação
```bash
./mvnw spring-boot:run
```

### 4. Acesse a documentação
- **Swagger UI**: http://localhost:8085/swagger-ui.html
- **API Base URL**: http://localhost:8085/api

## 📚 Funcionalidades Implementadas

### 🔹 Clientes
- ✅ Listar todos os clientes
- ✅ Buscar cliente por ID
- ✅ Buscar cliente por nome (case-insensitive)
- ✅ Buscar cliente por email (único)
- ✅ Criar novo cliente
- ✅ Atualizar cliente existente
- ✅ Deletar cliente
- ✅ Contar total de clientes

### 🔹 Produtos
- ✅ Listar todos os produtos
- ✅ Buscar produto por ID
- ✅ Buscar produtos por nome (case-insensitive)
- ✅ Buscar produtos por faixa de preço
- ✅ Criar novo produto
- ✅ Atualizar produto existente
- ✅ Deletar produto
- ✅ Contar total de produtos
- ✅ Atualizar estoque do produto

### 🔹 Pedidos
- ✅ Listar todos os pedidos
- ✅ Buscar pedido por ID
- ✅ Buscar pedidos por cliente
- ✅ Buscar pedidos por período
- ✅ Buscar pedidos por status
- ✅ Criar novo pedido
- ✅ Atualizar pedido existente
- ✅ Atualizar status do pedido
- ✅ Deletar pedido
- ✅ Contar total de pedidos
- ✅ Calcular valor total do pedido

## 🔐 Validações de Negócio

### Clientes
- Email único no sistema
- Nome e email obrigatórios
- Formato de email válido

### Produtos
- Nome, preço e descrição obrigatórios
- Preço deve ser positivo
- Quantidade em estoque não negativa
- Categoria usando enum centralizado

### Pedidos
- Cliente deve existir
- Produtos devem existir e ter estoque suficiente
- Quantidades devem ser positivas
- Cálculo automático de valores
- Controle de status com enum
- Validações de cancelamento e alteração

## 🗄️ Banco de Dados

### Migrações Flyway
- **V1**: Criação das tabelas principais
- **V2**: Atualização de enums e campo de pagamento

### Estrutura
- **clientes**: Dados dos clientes
- **produtos**: Catálogo de produtos
- **pedidos**: Pedidos dos clientes
- **itens_pedido**: Itens de cada pedido

## 📖 Documentação da API

### Endpoints Principais

#### Clientes
```
GET    /api/clientes              # Listar todos
GET    /api/clientes/{id}         # Buscar por ID
GET    /api/clientes/nome/{nome}  # Buscar por nome
GET    /api/clientes/email/{email} # Buscar por email
POST   /api/clientes              # Criar cliente
PUT    /api/clientes/{id}         # Atualizar cliente
DELETE /api/clientes/{id}         # Deletar cliente
GET    /api/clientes/contar       # Contar clientes
```

#### Produtos
```
GET    /api/produtos              # Listar todos
GET    /api/produtos/{id}         # Buscar por ID
GET    /api/produtos/nome/{nome}  # Buscar por nome
GET    /api/produtos/preco        # Buscar por faixa de preço
POST   /api/produtos              # Criar produto
PUT    /api/produtos/{id}         # Atualizar produto
DELETE /api/produtos/{id}         # Deletar produto
GET    /api/produtos/contar       # Contar produtos
PUT    /api/produtos/{id}/estoque # Atualizar estoque
```

#### Pedidos
```
GET    /api/pedidos               # Listar todos
GET    /api/pedidos/{id}          # Buscar por ID
GET    /api/pedidos/cliente/{id}  # Buscar por cliente
GET    /api/pedidos/periodo       # Buscar por período
GET    /api/pedidos/status/{status} # Buscar por status
POST   /api/pedidos               # Criar pedido
PUT    /api/pedidos/{id}          # Atualizar pedido
PUT    /api/pedidos/{id}/status   # Atualizar status
DELETE /api/pedidos/{id}          # Deletar pedido
GET    /api/pedidos/contar        # Contar pedidos
```

## 🧪 Testes

### Executar testes
```bash
./mvnw test
```

### Cobertura de testes
```bash
./mvnw jacoco:report
```

## 🔧 Configurações

### application.properties
```properties
# Banco de dados
spring.datasource.url=jdbc:postgresql://localhost:5432/orders_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

# Flyway
spring.flyway.enabled=true

# OpenAPI
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

### Docker Compose
```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: orders_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

## 📊 Monitoramento

### Logs
- Logs estruturados com SLF4J
- Níveis de log configuráveis
- Rastreamento de operações

### Métricas
- Contadores de entidades
- Tempo de resposta das operações
- Status de saúde da aplicação

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👥 Autores

- **Bootcamp Architecture Software** - *Iago Bertoletti Ribeiro*

## 🙏 Agradecimentos

- Spring Boot Team
- PostgreSQL Community
- Flyway Team
- OpenAPI Community

## 🎯 Padrões de Design Implementados

### **1. Repository Pattern**
- **Objetivo**: Abstrair acesso a dados
- **Implementação**: Interfaces que estendem `JpaRepository`
- **Benefícios**: Facilita testes unitários, permite troca de implementação

### **2. Service Layer Pattern**
- **Objetivo**: Centralizar lógica de negócio
- **Implementação**: Services com anotação `@Service`
- **Benefícios**: Facilita reutilização, melhora testabilidade

### **3. DTO Pattern**
- **Objetivo**: Separar dados de entrada/saída das entidades
- **Implementação**: DTOs com anotações OpenAPI para documentação
- **Benefícios**: Controle de serialização, validação de entrada, documentação clara

### **4. ModelMapper Pattern**
- **Objetivo**: Conversão automática entre DTOs e entidades
- **Implementação**: Configuração customizada no nível de serviço
- **Benefícios**: Reduz código boilerplate, mapeamento automático

### **5. Interface Segregation Pattern**
- **Objetivo**: Interfaces específicas e coesas
- **Implementação**: Interfaces no pacote `contracts`
- **Benefícios**: Facilita testes e inversão de dependência

### **6. Constructor Injection Pattern**
- **Objetivo**: Injeção de dependências via construtor
- **Implementação**: Uso do Lombok `@RequiredArgsConstructor`
- **Benefícios**: Dependências explícitas, facilita testes

### **7. Impl Pattern**
- **Objetivo**: Separação entre contrato e implementação
- **Implementação**: Classes com sufixo `Impl`
- **Benefícios**: Clareza na separação de responsabilidades

### **8. Contracts Pattern**
- **Objetivo**: Contratos bem definidos
- **Implementação**: Interfaces em pacote separado
- **Benefícios**: Facilita manutenção e evolução

### **9. Utility Classes Pattern**
- **Objetivo**: Separação de responsabilidades específicas
- **Implementação**: `PedidoValidator` e `PedidoCalculator`
- **Benefícios**: Código mais limpo e testável 