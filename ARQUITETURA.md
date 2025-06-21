# Arquitetura do Sistema de Pedidos

## 📋 Visão Geral

Este documento descreve a arquitetura do sistema de pedidos desenvolvido em Java com Spring Boot, seguindo o padrão MVC (Model-View-Controller) e implementando boas práticas de desenvolvimento.

## 🏗️ Arquitetura MVC

### **Model (Modelo)**
Representa as entidades de domínio e a estrutura de dados.

**Localização**: `src/main/java/com/br/bootcamp/orders/model/`

**Componentes**:
- **Entidades JPA**: `Cliente.java`, `Produto.java`, `Pedido.java`, `ItemPedido.java`
- **Enums Centralizados**: `model/enums/` com `StatusPedido.java`, `CategoriaProduto.java`, `TipoPagamento.java`
- **Serialização**: Todas as entidades implementam `Serializable` com `serialVersionUID`

**Características**:
- Anotadas com `@Entity` para mapeamento JPA
- Utilizam Lombok para redução de boilerplate
- Definem relacionamentos (`@OneToMany`, `@ManyToOne`)
- Implementam `Serializable` para cache e transferência de dados

### **View (Visão)**
Representa a apresentação dos dados através de APIs REST.

**Localização**: `src/main/java/com/br/bootcamp/orders/controller/`

**Componentes**:
- **Controllers REST**: `ClienteController.java`, `ProdutoController.java`, `PedidoController.java`
- **Configurações**: `controller/config/OpenApiConfig.java`
- **Documentação**: Anotações OpenAPI/Swagger completas

**Características**:
- Anotados com `@RestController`
- Mapeiam URLs com `@RequestMapping`
- Retornam `ResponseEntity` para controle de status HTTP
- Implementam tratamento de erros
- Habilitam CORS para integração com frontend
- Documentação OpenAPI completa

### **Controller (Controlador)**
Representa a lógica de negócios e coordenação entre camadas.

**Localização**: `src/main/java/com/br/bootcamp/orders/service/`

**Componentes**:
- **Interfaces**: `service/contracts/` com `IClienteService.java`, `IProdutoService.java`, `IPedidoService.java`
- **Implementações**: `ClienteServiceImpl.java`, `ProdutoServiceImpl.java`, `PedidoServiceImpl.java`
- **Utilitários**: `service/util/` com `PedidoValidator.java`, `PedidoCalculator.java`

**Características**:
- Anotados com `@Service`
- Contêm validações de negócio
- Implementam regras complexas
- Coordenam operações entre entidades
- Separação de responsabilidades com classes utilitárias

## 📁 Estrutura de Pacotes

```
src/main/java/com/br/bootcamp/orders/
├── OrdersApplication.java              # Classe principal da aplicação
├── model/                             # Camada de entidades (Model)
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
├── service/                           # Camada de serviços (Controller)
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
└── controller/                        # Camada de apresentação (View)
    ├── config/                        # Configurações
    │   └── OpenApiConfig.java         # Configuração OpenAPI
    ├── ClienteController.java         # Controller Cliente
    ├── ProdutoController.java         # Controller Produto
    └── PedidoController.java          # Controller Pedido
```

## 🎯 Padrões de Design Implementados

### **1. Repository Pattern**
- **Objetivo**: Abstrair acesso a dados
- **Implementação**: Interfaces que estendem `JpaRepository`
- **Benefícios**: Facilita testes unitários, permite troca de implementação

### **2. Service Layer Pattern**
- **Objetivo**: Centralizar lógica de negócio
- **Implementação**: Services com anotação `@Service`
- **Benefícios**: Facilita reutilização, melhora testabilidade

### **3. Interface Segregation Pattern**
- **Objetivo**: Interfaces específicas e coesas
- **Implementação**: Interfaces no pacote `contracts`
- **Benefícios**: Facilita testes e inversão de dependência

### **4. Constructor Injection Pattern**
- **Objetivo**: Injeção de dependências via construtor
- **Implementação**: Uso do Lombok `@RequiredArgsConstructor`
- **Benefícios**: Dependências explícitas, facilita testes

### **5. Impl Pattern**
- **Objetivo**: Separação entre contrato e implementação
- **Implementação**: Classes com sufixo `Impl`
- **Benefícios**: Clareza na separação de responsabilidades

### **6. Contracts Pattern**
- **Objetivo**: Contratos bem definidos
- **Implementação**: Interfaces em pacote separado
- **Benefícios**: Facilita manutenção e evolução

### **7. Utility Classes Pattern**
- **Objetivo**: Separação de responsabilidades específicas
- **Implementação**: `PedidoValidator` e `PedidoCalculator`
- **Benefícios**: Código mais limpo e testável

## 🔧 Tecnologias e Frameworks

### **Backend**
- **Java 17**: Linguagem principal
- **Spring Boot 3.2.0**: Framework principal
- **Spring Data JPA**: Persistência de dados
- **Spring Web**: APIs REST
- **SpringDoc OpenAPI**: Documentação da API

### **Banco de Dados**
- **PostgreSQL 15**: Banco de dados principal
- **Flyway**: Versionamento de banco
- **Docker Compose**: Infraestrutura

### **Ferramentas**
- **Lombok**: Redução de boilerplate
- **Maven**: Gerenciamento de dependências
- **SLF4J**: Logging estruturado

## 📊 Funcionalidades Implementadas

### **Clientes**
- ✅ CRUD completo
- ✅ Busca por nome (case-insensitive)
- ✅ Busca por email (único)
- ✅ Validação de email único
- ✅ Contagem total

### **Produtos**
- ✅ CRUD completo
- ✅ Busca por nome (case-insensitive)
- ✅ Busca por faixa de preço
- ✅ Controle de estoque
- ✅ Categorização com enums
- ✅ Validações de preço e estoque

### **Pedidos**
- ✅ CRUD completo
- ✅ Busca por cliente
- ✅ Busca por período
- ✅ Busca por status
- ✅ Cálculo automático de valores
- ✅ Controle de status com enums
- ✅ Validações de estoque
- ✅ Classes utilitárias para validação e cálculo

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

## 🗄️ Persistência de Dados

### **Estratégia de Migração**
- **Flyway**: Versionamento automático do banco
- **V1**: Criação das tabelas principais
- **V2**: Atualização de enums e campo de pagamento

### **Relacionamentos**
- **Cliente → Pedido**: One-to-Many
- **Pedido → ItemPedido**: One-to-Many
- **Produto → ItemPedido**: One-to-Many

### **Constraints**
- Chaves primárias auto-increment
- Chaves estrangeiras com CASCADE
- Constraints de unicidade (email)
- Constraints de validação (preços, quantidades)

## 📖 Documentação da API

### **OpenAPI/Swagger**
- Documentação automática
- Exemplos de requisição/resposta
- Descrições detalhadas
- Códigos de status HTTP

### **Endpoints Principais**
- **Clientes**: 8 endpoints
- **Produtos**: 9 endpoints
- **Pedidos**: 10 endpoints

## 🧪 Testabilidade

### **Estratégias**
- Injeção de dependências via construtor
- Interfaces para todos os serviços
- Separação de responsabilidades
- Classes utilitárias isoladas

### **Cobertura**
- Testes unitários para services
- Testes de integração para controllers
- Testes de repository com H2

## 📈 Monitoramento e Logs

### **Logging**
- SLF4J para logging estruturado
- Níveis de log configuráveis
- Rastreamento de operações

### **Métricas**
- Contadores de entidades
- Tempo de resposta das operações
- Status de saúde da aplicação

## 🔄 Fluxo de Dados

### **Criação de Pedido**
1. **Controller**: Recebe requisição HTTP
2. **Validator**: Valida dados de entrada
3. **Calculator**: Calcula valores e prepara itens
4. **Service**: Coordena operações
5. **Repository**: Persiste no banco
6. **Response**: Retorna dados processados

### **Busca de Dados**
1. **Controller**: Recebe parâmetros
2. **Service**: Aplica lógica de negócio
3. **Repository**: Consulta banco
4. **Response**: Retorna dados formatados

## 🚀 Deploy e Infraestrutura

### **Docker**
- PostgreSQL via Docker Compose
- Volumes persistentes
- Configuração de rede

### **Configuração**
- Properties externalizáveis
- Perfis de ambiente
- Configuração de CORS

## 📋 Próximos Passos

### **Melhorias Técnicas**
- Implementação de cache (Redis)
- Autenticação e autorização
- Testes de performance
- Monitoramento com Micrometer

### **Funcionalidades**
- Relatórios de vendas
- Notificações de status
- Integração com sistemas externos
- Dashboard administrativo

## 🎯 Conclusão

A arquitetura implementada segue as melhores práticas de desenvolvimento, proporcionando:

- **Manutenibilidade**: Código bem estruturado e documentado
- **Testabilidade**: Separação clara de responsabilidades
- **Escalabilidade**: Padrões que facilitam evolução
- **Documentação**: APIs bem documentadas
- **Robustez**: Validações e tratamento de erros adequados

O sistema está pronto para produção e pode ser facilmente estendido com novas funcionalidades. 