# Arquitetura do Sistema - Bootcamp Arquiteto(a) de Software

## 📋 Visão Geral

Este documento apresenta a arquitetura do sistema desenvolvido como **Desafio Final** do Bootcamp de Arquiteto(a) de Software, demonstrando a aplicação prática dos conceitos trabalhados nos módulos:

1. **Fundamentos de Arquitetura de Software**
2. **Requisitos Arquiteturais e Modelagem Arquitetural**
3. **Design Patterns, Estilos e Padrões Arquiteturais**
4. **Principais Arquiteturas de Software da Atualidade**

## 🎯 Objetivos de Ensino Aplicados

### 1. Fundamentos de Arquitetura de Software
- **Separação de Responsabilidades**: Implementação clara do padrão MVC
- **Modularidade**: Organização em pacotes bem definidos
- **Escalabilidade**: Estrutura preparada para crescimento
- **Manutenibilidade**: Código organizado e documentado

### 2. Requisitos Arquiteturais e Modelagem Arquitetural
- **Requisitos Funcionais**: CRUD completo + funcionalidades adicionais
- **Requisitos Não-Funcionais**: Performance, segurança, usabilidade
- **Modelagem**: Diagramas UML e documentação arquitetural
- **Validação**: Testes unitários e integração

### 3. Design Patterns, Estilos e Padrões Arquiteturais
- **Padrão MVC**: Model-View-Controller implementado
- **Repository Pattern**: Abstração de acesso a dados
- **Service Layer Pattern**: Lógica de negócio centralizada
- **DTO Pattern**: Transferência de dados estruturada

### 4. Principais Arquiteturas de Software da Atualidade
- **API RESTful**: Endpoints REST bem definidos
- **Arquitetura em Camadas**: Separação clara de responsabilidades
- **Microserviços Ready**: Estrutura preparada para evolução
- **Cloud Native**: Configuração para containerização

## 🏗️ Arquitetura MVC Implementada

### **Model (Modelo)**
Representa as entidades de domínio e a estrutura de dados.

**Localização**: `src/main/java/com/br/bootcamp/orders/model/`

**Componentes**:
- **Entidades JPA**: `Cliente.java`, `Produto.java`, `Pedido.java`, `ItemPedido.java`
- **DTOs**: `model/dto/` com objetos de transferência de dados
- **Enums Centralizados**: `model/enums/` com `StatusPedido.java`, `CategoriaProduto.java`, `TipoPagamento.java`

**Características**:
- Anotadas com `@Entity` para mapeamento JPA
- Implementam `Serializable` para cache e transferência
- Definem relacionamentos (`@OneToMany`, `@ManyToOne`)
- Utilizam Lombok para redução de boilerplate

### **View (Visão)**
Representa a apresentação dos dados através de APIs REST.

**Localização**: `src/main/java/com/br/bootcamp/orders/controller/`

**Componentes**:
- **Controllers REST**: `ClienteController.java`, `ProdutoController.java`, `PedidoController.java`
- **Configurações**: `controller/config/` com configurações OpenAPI e ModelMapper

**Características**:
- Anotados com `@RestController`
- Mapeiam URLs com `@RequestMapping`
- Retornam `ResponseEntity` para controle de status HTTP
- Documentação OpenAPI completa
- Habilitam CORS para integração

### **Controller (Controlador)**
Representa a lógica de negócios e coordenação entre camadas.

**Localização**: `src/main/java/com/br/bootcamp/orders/service/`

**Componentes**:
- **Interfaces**: `service/contracts/` com contratos dos serviços
- **Implementações**: `ClienteServiceImpl.java`, `ProdutoServiceImpl.java`, `PedidoServiceImpl.java`
- **Utilitários**: `service/util/` com classes especializadas
- **Exceções**: `service/exception/` com tratamento de erros

**Características**:
- Anotados com `@Service`
- Contêm validações de negócio
- Implementam regras complexas
- Coordenam operações entre entidades

## 📁 Estrutura de Pacotes Detalhada

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

## 🎨 Padrões de Design Implementados

### **1. Repository Pattern**
- **Objetivo**: Abstrair acesso a dados
- **Implementação**: Interfaces que estendem `JpaRepository`
- **Benefícios**: Facilita testes unitários, permite troca de implementação
- **Localização**: `repository/`

### **2. Service Layer Pattern**
- **Objetivo**: Centralizar lógica de negócio
- **Implementação**: Services com anotação `@Service`
- **Benefícios**: Facilita reutilização, melhora testabilidade
- **Localização**: `service/`

### **3. DTO Pattern**
- **Objetivo**: Separar dados de entrada/saída das entidades
- **Implementação**: DTOs com anotações OpenAPI
- **Benefícios**: Controle de serialização, validação de entrada
- **Localização**: `model/dto/`

### **4. Interface Segregation Pattern**
- **Objetivo**: Interfaces específicas e coesas
- **Implementação**: Interfaces no pacote `contracts`
- **Benefícios**: Facilita testes e inversão de dependência
- **Localização**: `service/contracts/`

### **5. Constructor Injection Pattern**
- **Objetivo**: Injeção de dependências via construtor
- **Implementação**: Uso do Lombok `@RequiredArgsConstructor`
- **Benefícios**: Dependências explícitas, facilita testes

### **6. Utility Classes Pattern**
- **Objetivo**: Separação de responsabilidades específicas
- **Implementação**: `PedidoValidator` e `PedidoCalculator`
- **Benefícios**: Código mais limpo e testável
- **Localização**: `service/util/`

## 🔧 Tecnologias e Frameworks

### **Backend**
- **Java 21**: Linguagem principal (conforme solicitado)
- **Spring Boot 3.2.0**: Framework principal
- **Spring Data JPA**: Persistência de dados
- **Spring Web**: APIs REST
- **SpringDoc OpenAPI**: Documentação da API

### **Banco de Dados**
- **PostgreSQL 15**: Banco de dados principal (diferencial)
- **Flyway**: Versionamento de banco
- **Docker Compose**: Infraestrutura

### **Ferramentas**
- **Lombok**: Redução de boilerplate
- **Maven**: Gerenciamento de dependências
- **ModelMapper**: Conversão entre DTOs e entidades

## 📊 Funcionalidades Implementadas

### **Operações CRUD Básicas (Conforme Enunciado)**

#### Clientes
- ✅ **Create**: Criar novo cliente
- ✅ **Read**: Buscar cliente por ID
- ✅ **Update**: Atualizar cliente existente
- ✅ **Delete**: Deletar cliente
- ✅ **Find All**: Listar todos os clientes
- ✅ **Find By Name**: Buscar por nome
- ✅ **Contagem**: Contar total de clientes

#### Produtos
- ✅ **Create**: Criar novo produto
- ✅ **Read**: Buscar produto por ID
- ✅ **Update**: Atualizar produto existente
- ✅ **Delete**: Deletar produto
- ✅ **Find All**: Listar todos os produtos
- ✅ **Find By Name**: Buscar por nome
- ✅ **Contagem**: Contar total de produtos

#### Pedidos
- ✅ **Create**: Criar novo pedido
- ✅ **Read**: Buscar pedido por ID
- ✅ **Update**: Atualizar pedido existente
- ✅ **Delete**: Deletar pedido
- ✅ **Find All**: Listar todos os pedidos
- ✅ **Find By Client**: Buscar por cliente
- ✅ **Find By Status**: Buscar por status
- ✅ **Contagem**: Contar total de pedidos

### **Funcionalidades Adicionais (Diferencial)**
- ✅ Busca por email (clientes)
- ✅ Busca por faixa de preço (produtos)
- ✅ Busca por período (pedidos)
- ✅ Atualização de estoque (produtos)
- ✅ Atualização de status (pedidos)
- ✅ Cálculo automático de valores
- ✅ Validações de negócio robustas

## 🔐 Validações de Negócio

### **Clientes**
- Email único no sistema
- Nome e email obrigatórios
- Formato de email válido

### **Produtos**
- Nome, preço e descrição obrigatórios
- Preço deve ser positivo
- Quantidade em estoque não negativa
- Categoria usando enum centralizado

### **Pedidos**
- Cliente deve existir
- Produtos devem existir e ter estoque suficiente
- Quantidades devem ser positivas
- Cálculo automático de valores
- Controle de status com enum
- Validações de cancelamento e alteração

## 📈 Benefícios da Arquitetura Implementada

### **1. Manutenibilidade**
- Código organizado em camadas bem definidas
- Responsabilidades claramente separadas
- Documentação completa

### **2. Testabilidade**
- Interfaces bem definidas
- Injeção de dependências
- Classes utilitárias isoladas

### **3. Escalabilidade**
- Estrutura preparada para crescimento
- Padrões de design escaláveis
- Configuração para containerização

### **4. Flexibilidade**
- Fácil troca de implementações
- Configurações externalizadas
- Suporte a diferentes bancos de dados

## 🎯 Conclusão

Esta arquitetura demonstra a aplicação prática dos conceitos de arquitetura de software, implementando uma solução robusta que atende aos requisitos do enunciado do bootcamp, com foco na qualidade, manutenibilidade e escalabilidade do código. 