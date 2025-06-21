# Bootcamp: Arquiteto(a) de Software - Desafio Final

## 📋 Descrição do Projeto

Este projeto foi desenvolvido como parte do **Desafio Final** do Bootcamp de Arquiteto(a) de Software, com o objetivo de exercitar os conceitos trabalhados nos módulos:

1. **Fundamentos de Arquitetura de Software**
2. **Requisitos Arquiteturais e Modelagem Arquitetural**
3. **Design Patterns, Estilos e Padrões Arquiteturais**
4. **Principais Arquiteturas de Software da Atualidade**

### 🎯 Objetivo do Exercício

O objetivo é aplicar os conhecimentos de arquitetura de software, focando na implementação de uma **API RESTful**, seguindo o padrão **MVC (Model-View-Controller)**. A ideia é explorar práticas de design e construção de APIs, documentação de arquitetura e organização de código.

## 🏗️ Arquitetura Implementada

### Padrão MVC (Model-View-Controller)

Este projeto implementa o padrão arquitetural MVC conforme solicitado no enunciado:

- **Model**: Entidades JPA com relacionamentos e persistência
- **View**: Controllers REST que expõem endpoints da API
- **Controller**: Services com lógica de negócio e Repositories para acesso a dados

### Estrutura de Pastas e Componentes

```
src/main/java/com/br/bootcamp/orders/
├── OrdersApplication.java              # Classe principal da aplicação
├── model/                             # Camada de entidades (Model)
│   ├── Cliente.java                   # Entidade Cliente
│   ├── Produto.java                   # Entidade Produto
│   ├── Pedido.java                    # Entidade Pedido
│   ├── ItemPedido.java                # Entidade ItemPedido
│   ├── dto/                           # Data Transfer Objects
│   │   ├── ClienteDTO.java            # DTO para Cliente
│   │   ├── ProdutoDTO.java            # DTO para Produto
│   │   ├── PedidoDTO.java             # DTO para Pedido
│   │   └── ErrorResponseDTO.java      # DTO para respostas de erro
│   └── enums/                         # Enums centralizados
│       ├── StatusPedido.java          # Status dos pedidos
│       ├── CategoriaProduto.java      # Categorias de produtos
│       └── TipoPagamento.java         # Tipos de pagamento
├── repository/                        # Camada de acesso a dados
│   ├── ClienteRepository.java         # Repository Cliente
│   ├── ProdutoRepository.java         # Repository Produto
│   └── PedidoRepository.java          # Repository Pedido
├── service/                           # Camada de serviços (Controller)
│   ├── contracts/                     # Interfaces dos serviços
│   │   ├── IClienteService.java       # Interface Cliente
│   │   ├── IProdutoService.java       # Interface Produto
│   │   └── IPedidoService.java        # Interface Pedido
│   ├── util/                          # Classes utilitárias
│   │   ├── PedidoValidator.java       # Validações de pedido
│   │   ├── PedidoCalculator.java      # Cálculos de pedido
│   │   └── StringToStatusPedidoConverter.java # Conversor de strings
│   ├── exception/                     # Tratamento de exceções
│   │   ├── BusinessException.java     # Exceção de negócio
│   │   ├── ResourceNotFoundException.java # Recurso não encontrado
│   │   └── GlobalExceptionHandler.java # Handler global
│   ├── ClienteServiceImpl.java        # Implementação Cliente
│   ├── ProdutoServiceImpl.java        # Implementação Produto
│   └── PedidoServiceImpl.java         # Implementação Pedido
└── controller/                        # Camada de apresentação (View)
    ├── config/                        # Configurações
    │   ├── OpenApiConfig.java         # Configuração OpenAPI
    │   └── ModelMapperConfig.java     # Configuração ModelMapper
    ├── ClienteController.java         # Controller Cliente
    ├── ProdutoController.java         # Controller Produto
    └── PedidoController.java          # Controller Pedido
```

### Explicação dos Componentes MVC

#### **Model (Modelo)**
- **Responsabilidade**: Representa as entidades de domínio e a estrutura de dados
- **Localização**: `src/main/java/com/br/bootcamp/orders/model/`
- **Componentes**: Entidades JPA (`Cliente`, `Produto`, `Pedido`, `ItemPedido`), DTOs e Enums
- **Características**: Anotadas com `@Entity`, implementam `Serializable`, definem relacionamentos

#### **View (Visão)**
- **Responsabilidade**: Apresentação dos dados através de APIs REST
- **Localização**: `src/main/java/com/br/bootcamp/orders/controller/`
- **Componentes**: Controllers REST (`ClienteController`, `ProdutoController`, `PedidoController`)
- **Características**: Anotados com `@RestController`, mapeiam URLs, retornam `ResponseEntity`

#### **Controller (Controlador)**
- **Responsabilidade**: Lógica de negócios e coordenação entre camadas
- **Localização**: `src/main/java/com/br/bootcamp/orders/service/`
- **Componentes**: Services (`ClienteServiceImpl`, `ProdutoServiceImpl`, `PedidoServiceImpl`)
- **Características**: Anotados com `@Service`, contêm validações de negócio, implementam regras complexas

## 🚀 Tecnologias Utilizadas

### Plataforma e Linguagem
- **Java 21** com **Spring Boot 3.2.0** (conforme solicitado no enunciado)

### Dependências Principais
- **Spring Web**: Criação da API REST
- **Spring Data JPA**: Persistência de dados
- **PostgreSQL**: Banco de dados (diferencial mencionado no enunciado)
- **Flyway**: Migrações de banco
- **OpenAPI 3**: Documentação da API
- **Docker Compose**: Infraestrutura

## 📚 Funcionalidades Implementadas

### Operações CRUD Básicas (Conforme Enunciado)

#### 🔹 Clientes
- ✅ **Create**: Criar novo cliente (`POST /api/clientes`)
- ✅ **Read**: Buscar cliente por ID (`GET /api/clientes/{id}`)
- ✅ **Update**: Atualizar cliente existente (`PUT /api/clientes/{id}`)
- ✅ **Delete**: Deletar cliente (`DELETE /api/clientes/{id}`)
- ✅ **Find All**: Listar todos os clientes (`GET /api/clientes`)
- ✅ **Find By Name**: Buscar por nome (`GET /api/clientes/nome/{nome}`)
- ✅ **Contagem**: Contar total de clientes (`GET /api/clientes/contar`)

#### 🔹 Produtos
- ✅ **Create**: Criar novo produto (`POST /api/produtos`)
- ✅ **Read**: Buscar produto por ID (`GET /api/produtos/{id}`)
- ✅ **Update**: Atualizar produto existente (`PUT /api/produtos/{id}`)
- ✅ **Delete**: Deletar produto (`DELETE /api/produtos/{id}`)
- ✅ **Find All**: Listar todos os produtos (`GET /api/produtos`)
- ✅ **Find By Name**: Buscar por nome (`GET /api/produtos/nome/{nome}`)
- ✅ **Contagem**: Contar total de produtos (`GET /api/produtos/contar`)

#### 🔹 Pedidos
- ✅ **Create**: Criar novo pedido (`POST /api/pedidos`)
- ✅ **Read**: Buscar pedido por ID (`GET /api/pedidos/{id}`)
- ✅ **Update**: Atualizar pedido existente (`PUT /api/pedidos/{id}`)
- ✅ **Delete**: Deletar pedido (`DELETE /api/pedidos/{id}`)
- ✅ **Find All**: Listar todos os pedidos (`GET /api/pedidos`)
- ✅ **Find By Client**: Buscar por cliente (`GET /api/pedidos/cliente/{id}`)
- ✅ **Find By Status**: Buscar por status (`GET /api/pedidos/status/{status}`)
- ✅ **Contagem**: Contar total de pedidos (`GET /api/pedidos/contar`)

### Funcionalidades Adicionais (Diferencial)
- ✅ Busca por email (clientes)
- ✅ Busca por faixa de preço (produtos)
- ✅ Busca por período (pedidos)
- ✅ Atualização de estoque (produtos)
- ✅ Atualização de status (pedidos)
- ✅ Cálculo automático de valores
- ✅ Validações de negócio robustas

## 🔧 Configuração e Execução

### Pré-requisitos
- Docker e Docker Compose

### 🐳 Execução com Docker (Recomendado)

1. **Clone o repositório**
```bash
git clone <url-do-repositorio>
cd orders
```

2. **Execute todos os serviços (aplicação + banco)**
```bash
docker-compose up -d
```

3. **Acesse a aplicação**
- **Swagger UI**: http://localhost:8085/swagger-ui/index.html
- **API Base URL**: http://localhost:8085/api

### 📋 Comandos úteis do Docker

```bash
# Verificar status dos serviços
docker-compose ps

# Ver logs da aplicação
docker-compose logs -f orders-app

# Ver logs do banco de dados
docker-compose logs -f postgres

# Parar todos os serviços
docker-compose down

# Parar e remover volumes (dados do banco)
docker-compose down -v

# Rebuild da aplicação
docker-compose build orders-app
docker-compose up -d
```

## 📖 Documentação da API

### Endpoints Principais

#### Clientes
```
GET    /api/clientes              # Listar todos (Find All)
GET    /api/clientes/{id}         # Buscar por ID (Find By ID)
GET    /api/clientes/nome/{nome}  # Buscar por nome (Find By Name)
GET    /api/clientes/contar       # Contar total (Contagem)
POST   /api/clientes              # Criar cliente (Create)
PUT    /api/clientes/{id}         # Atualizar cliente (Update)
DELETE /api/clientes/{id}         # Deletar cliente (Delete)
```

#### Produtos
```
GET    /api/produtos              # Listar todos (Find All)
GET    /api/produtos/{id}         # Buscar por ID (Find By ID)
GET    /api/produtos/nome/{nome}  # Buscar por nome (Find By Name)
GET    /api/produtos/contar       # Contar total (Contagem)
POST   /api/produtos              # Criar produto (Create)
PUT    /api/produtos/{id}         # Atualizar produto (Update)
DELETE /api/produtos/{id}         # Deletar produto (Delete)
```

#### Pedidos
```
GET    /api/pedidos               # Listar todos (Find All)
GET    /api/pedidos/{id}          # Buscar por ID (Find By ID)
GET    /api/pedidos/cliente/{id}  # Buscar por cliente
GET    /api/pedidos/status/{status} # Buscar por status
GET    /api/pedidos/contar        # Contar total (Contagem)
POST   /api/pedidos               # Criar pedido (Create)
PUT    /api/pedidos/{id}          # Atualizar pedido (Update)
DELETE /api/pedidos/{id}          # Deletar pedido (Delete)
```

## 📊 Entregáveis do Desafio

### 1. ✅ Desenho Arquitetural
- **Arquivo**: `DESENHO_ARQUITETURAL.md`
- **Descrição**: Documentação detalhada da arquitetura MVC implementada
- **Diagramas**: Estrutura de componentes e relacionamentos

### 2. ✅ Estrutura de Pastas e Explicação
- **Arquivo**: `README.md` (seção acima)
- **Descrição**: Estrutura organizada seguindo padrão MVC
- **Explicação**: Papel de cada componente (Controller, Model, Service)

### 3. ✅ Código Funcionando
- **Repositório**: Código completo e funcional
- **Testes**: Testes unitários implementados
- **Documentação**: OpenAPI/Swagger completa

### 4. ✅ Persistência Funcionando
- **Banco**: PostgreSQL com Docker
- **Migrações**: Flyway para versionamento
- **Relacionamentos**: JPA com mapeamentos corretos

## 🎯 Conclusão

Este projeto demonstra a aplicação prática dos conceitos de arquitetura de software, implementando uma API RESTful robusta seguindo o padrão MVC, com persistência de dados, validações de negócio e documentação completa, atendendo a todos os requisitos solicitados no enunciado do bootcamp. 