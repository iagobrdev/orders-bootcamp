# Documento de Desenho Arquitetural - Sistema de Pedidos

**Repositório do Projeto:** [https://github.com/iagobrdev/orders-bootcamp](https://github.com/iagobrdev/orders-bootcamp)

## 1. Descritivo da Solução

A Solução deverá proporcionar acesso via API Rest para consulta, e manipulação de dados, para clientes internos e parceiros, dos domínios **"Cliente"**, **"Produto"** e **"Pedido"**.

---

### O Elemento "Cliente"

O Elemento "Cliente" deverá possuir as seguintes características, representando a entidade no banco de dados:

| Campo      | Tipo         | Descrição                                         |
| :--------- | :----------- | :------------------------------------------------ |
| `id`       | Long         | Representando o código sequencial do cliente.     |
| `nome`     | String       | Representando o nome do cliente.                  |
| `email`    | String       | Representando o email (único) do cliente.         |
| `telefone` | String       | Representando o telefone de contato do cliente.   |
| `endereco` | String       | Representando o endereço do cliente.              |

*Outros elementos como **Produto** e **Pedido** também possuem características próprias e endpoints dedicados.*

---

### O Elemento "Produto"

| Campo               | Tipo             | Descrição                                          |
| :------------------ | :--------------- | :------------------------------------------------- |
| `id`                | Long             | Representando o código sequencial do produto.      |
| `nome`              | String           | Representando o nome do produto.                   |
| `descricao`         | String           | Representando a descrição detalhada do produto.    |
| `preco`             | BigDecimal       | Representando o preço do produto.                  |
| `quantidadeEstoque` | Integer          | Representando a quantidade disponível em estoque.  |
| `categoria`         | CategoriaProduto | Representando a categoria do produto (Enum).       |

---

### O Elemento "Pedido"

| Campo         | Tipo            | Descrição                                           |
| :------------ | :-------------- | :-------------------------------------------------- |
| `id`          | Long            | Representando o código sequencial do pedido.        |
| `cliente`     | Cliente         | Representando o cliente que fez o pedido.           |
| `dataPedido`  | LocalDateTime   | Representando a data e hora em que o pedido foi feito. |
| `status`      | StatusPedido    | Representando o status atual do pedido (Enum).      |
| `tipoPagamento` | TipoPagamento   | Representando a forma de pagamento (Enum).          |
| `valorTotal`  | BigDecimal      | Representando o valor total calculado do pedido.    |
| `itens`       | List<ItemPedido> | Representando a lista de itens do pedido.          |

---

### API

A API deverá ser do tipo **RESTful** e deverá conter os seguintes métodos públicos expostos para o domínio **Cliente**:

-   **Path**: `/api/clientes`

-   **`listarTodos()`** = Lista todos os Clientes cadastrados
    -   verbo **GET** (`/`)

-   **`buscarPorNome(nome)`** = Lista os Clientes com similaridade de nome
    -   verbo **GET** (`/nome/{nome}`)

-   **`buscarPorId(id)`** = Retorna 1 Cliente com o ID indicado
    -   verbo **GET** (`/{id}`)

-   **`criar(clienteDTO)`** = Insere um novo cliente
    -   verbo **POST** (`/`)

-   **`atualizar(id, clienteDTO)`** = Atualiza um cliente existente
    -   verbo **PUT** (`/{id}`)

-   **`deletar(id)`** = Exclui um registro com o ID informado
    -   verbo **DELETE** (`/{id}`)

-   **`contarClientes()`** = Retorna a quantidade de registros cadastrados
    -   verbo **GET** (`/contar`)

A API também expõe endpoints para o domínio "Produto" e "Pedido":

#### Endpoints de Produtos (`/api/produtos`)
-   **`GET /`**: Lista todos os Produtos.
-   **`GET /{id}`**: Retorna um Produto pelo ID.
-   **`GET /nome/{nome}`**: Lista Produtos por nome.
-   **`GET /contar`**: Retorna a quantidade de Produtos.
-   **`POST /`**: Insere um novo Produto.
-   **`PUT /{id}`**: Atualiza um Produto existente.
-   **`DELETE /{id}`**: Exclui um Produto.

#### Endpoints de Pedidos (`/api/pedidos`)
-   **`GET /`**: Lista todos os Pedidos.
-   **`GET /{id}`**: Retorna um Pedido pelo ID.
-   **`GET /cliente/{id}`**: Lista Pedidos de um cliente específico.
-   **`GET /status/{status}`**: Lista Pedidos por status.
-   **`GET /contar`**: Retorna a quantidade de Pedidos.
-   **`POST /`**: Insere um novo Pedido.
-   **`PUT /{id}`**: Atualiza um Pedido existente.
-   **`DELETE /{id}`**: Exclui um Pedido.

---

### Persistência

A persistência será realizada por meio de um banco de dados relacional **PostgreSQL (versão 15)**, gerenciado via Docker. A conexão com a aplicação é feita através do seguinte JDBC:

`JDBC: jdbc:postgresql://localhost:5435/orders_db`

As migrações de schema do banco de dados são controladas pela ferramenta **Flyway**.

---

## 2. Estrutura de Pastas

A estrutura de pastas do projeto segue o padrão do Spring Boot, separando as responsabilidades em camadas bem definidas, conforme o padrão MVC.

```
src/main/java/com/br/bootcamp/orders/
├── OrdersApplication.java
├── model/
│   ├── Cliente.java
│   ├── Produto.java
│   ├── Pedido.java
│   ├── dto/
│   └── enums/
├── repository/
│   ├── ClienteRepository.java
│   ├── ProdutoRepository.java
│   └── PedidoRepository.java
├── service/
│   ├── contracts/
│   ├── exception/
│   ├── util/
│   ├── ClienteServiceImpl.java
│   ├── ProdutoServiceImpl.java
│   └── PedidoServiceImpl.java
└── controller/
    ├── config/
    ├── ClienteController.java
    ├── ProdutoController.java
    └── PedidoController.java
```

-   **`controller`** = Camada de Apresentação (View). Controla o fluxo das requisições HTTP, recebendo as chamadas e retornando as respostas.
-   **`service`** = Camada de Lógica de Negócio (Controller). Executa as regras de negócio, validações e orquestra as operações.
-   **`model`** = Camada de Modelo (Model). Contém as entidades de domínio (ex: `Cliente`, `Produto`), os DTOs para transferência de dados e os Enums.
-   **`repository`** = Camada de Acesso a Dados. Abstrai a comunicação com o banco de dados, provendo métodos para persistência das entidades.

---

## 3. Diagramas (C4 Model)

### C4 Model Nível 1: Diagrama de Contexto

Este diagrama apresenta uma visão geral do sistema, seus usuários e as interações de alto nível.

```mermaid
graph TD
    subgraph "Sistema de Gestão de Pedidos"
        direction LR
        
        person_intern_user("Usuário Interno<br/>[Person]<br/>Funcionário da empresa")
        person_extern_user("Parceiro / Cliente<br/>[Person]<br/>Consumidor da API")
        
        system_orders("API de Pedidos<br/>[Software System]<br/>Permite o gerenciamento de<br/>Clientes, Produtos e Pedidos.")
        
        person_intern_user -- "Consulta e gerencia dados" --> system_orders
        person_extern_user -- "Realiza pedidos e consultas via API" --> system_orders
    end
```

### C4 Model Nível 2: Diagrama de Contêineres

Este diagrama detalha os contêineres que compõem o sistema (aplicações e bancos de dados).

```mermaid
graph TD
    subgraph "Ambiente Operacional"
        direction TB

        person_user("Usuário<br/>[Person]<br/>Interno ou Externo")

        subgraph "Sistema de Pedidos"
            direction TB
            
            container_api("API REST<br/>[Container: Spring Boot]<br/>Gerencia a lógica de negócio<br/>e expõe os endpoints.")
            container_db("Banco de Dados<br/>[Container: PostgreSQL]<br/>Armazena os dados de<br/>Clientes, Produtos e Pedidos.")
            
            container_api -- "Lê e escreve em" --> container_db
        end

        person_user -- "Faz requisições HTTPS/JSON para" --> container_api
    end
```

### C4 Model Nível 3: Diagrama de Componentes (Domínio Cliente)

Este diagrama foca nos componentes internos do contêiner da API, mostrando como as funcionalidades do domínio "Cliente" são implementadas.

```mermaid
graph TD
    subgraph "Contêiner: API REST (Spring Boot)"
        direction TB

        component_controller("ClienteController<br/>[Componente: Spring @RestController]<br/>Recebe requisições HTTP e<br/>expõe a API para o domínio Cliente.")
        component_service("ClienteService<br/>[Componente: Spring @Service]<br/>Implementa as regras de negócio<br/>para o gerenciamento de clientes.")
        component_repository("ClienteRepository<br/>[Componente: Spring @Repository]<br/>Gerencia o acesso e a<br/>persistência dos dados de clientes.")

        component_controller -- "Chama" --> component_service
        component_service -- "Usa" --> component_repository
    end

    person_user("Usuário<br/>[Person]")
    container_db("Banco de Dados<br/>[Container: PostgreSQL]")
    
    person_user -- "Interage com" --> component_controller
    component_repository -- "Lê e escreve em" --> container_db
```

### C4 Model Nível 4: Diagrama de Classes (Domínio Cliente)

Este diagrama mostra as principais classes envolvidas no domínio "Cliente" e seus relacionamentos.

```mermaid
classDiagram
    class ClienteController {
        -IClienteService clienteService
        +ResponseEntity~List~Cliente~~ listarTodos()
        +ResponseEntity~Cliente~ buscarPorId(Long id)
        +ResponseEntity~List~Cliente~~ buscarPorNome(String nome)
        +ResponseEntity~Cliente~ criar(ClienteDTO dto)
        +ResponseEntity~Cliente~ atualizar(Long id, ClienteDTO dto)
        +ResponseEntity~Void~ deletar(Long id)
        +ResponseEntity~Long~ contarClientes()
    }

    class IClienteService {
        <<Interface>>
        +List~Cliente~ listarTodos()
        +Optional~Cliente~ buscarPorId(Long id)
        +List~Cliente~ buscarPorNome(String nome)
        +Cliente salvar(ClienteDTO dto)
        +Cliente atualizar(Long id, ClienteDTO dto)
        +void deletar(Long id)
        +long contarClientes()
    }

    class ClienteServiceImpl {
        -ClienteRepository clienteRepository
        -ModelMapper modelMapper
        +List~Cliente~ listarTodos()
        +Optional~Cliente~ buscarPorId(Long id)
        +List~Cliente~ buscarPorNome(String nome)
        +Cliente salvar(ClienteDTO dto)
        +Cliente atualizar(Long id, ClienteDTO dto)
        +void deletar(Long id)
        +long contarClientes()
    }

    class ClienteRepository {
        <<Interface (JPA)>>
        +List~Cliente~ findByNomeContainingIgnoreCase(String nome)
        +Optional~Cliente~ findByEmail(String email)
    }

    class Cliente {
        -Long id
        -String nome
        -String email
        -String telefone
        -String endereco
    }
    
    class ClienteDTO {
      -String nome
      -String email
      -String telefone
      -String endereco
    }

    ClienteController ..> IClienteService : depends on
    ClienteServiceImpl ..|> IClienteService : implements
    ClienteServiceImpl ..> ClienteRepository : depends on
    ClienteServiceImpl ..> Cliente : works with
    ClienteController ..> ClienteDTO : uses
    ClienteServiceImpl ..> ClienteDTO : uses
```

### Diagrama de Sequência: Buscar Cliente por ID

Este diagrama ilustra a sequência de chamadas para a funcionalidade de busca de um cliente pelo seu ID.

```mermaid
sequenceDiagram
    participant User
    participant Controller as ClienteController
    participant Service as IClienteService
    participant Repository as ClienteRepository
    participant DB as Banco de Dados (PostgreSQL)

    User->>Controller: GET /api/clientes/{id}
    Controller->>Service: buscarPorId(id)
    Service->>Repository: findById(id)
    Repository->>DB: SELECT * FROM clientes WHERE id = ?
    DB-->>Repository: Retorna dados do cliente
    Repository-->>Service: Retorna Optional~Cliente~
    Service-->>Controller: Retorna Cliente
    Controller-->>User: Responde com 200 OK e JSON do Cliente
```

### C4 Model Nível 3: Diagrama de Componentes (Domínio Produto)

Este diagrama foca nos componentes internos do contêiner da API para o domínio "Produto".

```mermaid
graph TD
    subgraph "Contêiner: API REST (Spring Boot)"
        direction TB

        component_controller("ProdutoController<br/>[Componente: Spring @RestController]<br/>Recebe requisições HTTP e<br/>expõe a API para o domínio Produto.")
        component_service("ProdutoService<br/>[Componente: Spring @Service]<br/>Implementa as regras de negócio<br/>para o gerenciamento de produtos.")
        component_repository("ProdutoRepository<br/>[Componente: Spring @Repository]<br/>Gerencia o acesso e a<br/>persistência dos dados de produtos.")

        component_controller -- "Chama" --> component_service
        component_service -- "Usa" --> component_repository
    end

    person_user("Usuário<br/>[Person]")
    container_db("Banco de Dados<br/>[Container: PostgreSQL]")
    
    person_user -- "Interage com" --> component_controller
    component_repository -- "Lê e escreve em" --> container_db
```

### C4 Model Nível 3: Diagrama de Componentes (Domínio Pedido)

Este diagrama foca nos componentes internos do contêiner da API para o domínio "Pedido", incluindo seus utilitários.

```mermaid
graph TD
    subgraph "Contêiner: API REST (Spring Boot)"
        direction TB

        component_controller("PedidoController<br/>[Componente: Spring @RestController]<br/>Recebe requisições HTTP e<br/>expõe a API para o domínio Pedido.")
        component_service("PedidoService<br/>[Componente: Spring @Service]<br/>Implementa as regras de negócio<br/>para o gerenciamento de pedidos.")
        component_validator("PedidoValidator<br/>[Componente: Spring @Component]<br/>Valida regras de negócio<br/>complexas para pedidos.")
        component_calculator("PedidoCalculator<br/>[Componente: Spring @Component]<br/>Calcula o valor total e<br/>subtotais dos pedidos.")
        component_repository("PedidoRepository<br/>[Componente: Spring @Repository]<br/>Gerencia o acesso e a<br/>persistência dos dados de pedidos.")

        component_controller -- "Chama" --> component_service
        component_service -- "Usa" --> component_validator
        component_service -- "Usa" --> component_calculator
        component_service -- "Usa" --> component_repository
    end

    person_user("Usuário<br/>[Person]")
    container_db("Banco de Dados<br/>[Container: PostgreSQL]")
    
    person_user -- "Interage com" --> component_controller
    component_repository -- "Lê e escreve em" --> container_db
```

### C4 Model Nível 4: Diagrama de Classes (Domínio Cliente)

Este diagrama mostra as principais classes envolvidas no domínio "Cliente" e seus relacionamentos.

```mermaid
classDiagram
    class ClienteController {
        -IClienteService clienteService
        +ResponseEntity~List~Cliente~~ listarTodos()
        +ResponseEntity~Cliente~ buscarPorId(Long id)
        +ResponseEntity~List~Cliente~~ buscarPorNome(String nome)
        +ResponseEntity~Cliente~ criar(ClienteDTO dto)
        +ResponseEntity~Cliente~ atualizar(Long id, ClienteDTO dto)
        +ResponseEntity~Void~ deletar(Long id)
        +ResponseEntity~Long~ contarClientes()
    }

    class IClienteService {
        <<Interface>>
        +List~Cliente~ listarTodos()
        +Optional~Cliente~ buscarPorId(Long id)
        +List~Cliente~ buscarPorNome(String nome)
        +Cliente salvar(ClienteDTO dto)
        +Cliente atualizar(Long id, ClienteDTO dto)
        +void deletar(Long id)
        +long contarClientes()
    }

    class ClienteServiceImpl {
        -ClienteRepository clienteRepository
        -ModelMapper modelMapper
        +List~Cliente~ listarTodos()
        +Optional~Cliente~ buscarPorId(Long id)
        +List~Cliente~ buscarPorNome(String nome)
        +Cliente salvar(ClienteDTO dto)
        +Cliente atualizar(Long id, ClienteDTO dto)
        +void deletar(Long id)
        +long contarClientes()
    }

    class ClienteRepository {
        <<Interface (JPA)>>
        +List~Cliente~ findByNomeContainingIgnoreCase(String nome)
        +Optional~Cliente~ findByEmail(String email)
    }

    class Cliente {
        -Long id
        -String nome
        -String email
        -String telefone
        -String endereco
    }
    
    class ClienteDTO {
      -String nome
      -String email
      -String telefone
      -String endereco
    }

    ClienteController ..> IClienteService : depends on
    ClienteServiceImpl ..|> IClienteService : implements
    ClienteServiceImpl ..> ClienteRepository : depends on
    ClienteServiceImpl ..> Cliente : works with
    ClienteController ..> ClienteDTO : uses
    ClienteServiceImpl ..> ClienteDTO : uses
```

### C4 Model Nível 4: Diagrama de Classes (Domínio Produto)

Este diagrama mostra as principais classes envolvidas no domínio "Produto".

```mermaid
classDiagram
    class ProdutoController {
        -IProdutoService produtoService
        +ResponseEntity~Produto~ criar(ProdutoDTO dto)
        +ResponseEntity~Produto~ buscarPorId(Long id)
    }

    class IProdutoService {
        <<Interface>>
        +Produto salvar(ProdutoDTO dto)
        +Optional~Produto~ buscarPorId(Long id)
    }

    class ProdutoServiceImpl {
        -ProdutoRepository produtoRepository
        -ModelMapper modelMapper
    }

    class ProdutoRepository {
        <<Interface (JPA)>>
        +List~Produto~ findByNomeContainingIgnoreCase(String nome)
    }

    class Produto {
        -Long id
        -String nome
        -String descricao
        -BigDecimal preco
        -Integer quantidadeEstoque
        -CategoriaProduto categoria
    }
    
    class ProdutoDTO {
      -String nome
      -String descricao
      -BigDecimal preco
      -Integer quantidadeEstoque
      -CategoriaProduto categoria
    }

    ProdutoController ..> IProdutoService
    ProdutoServiceImpl ..|> IProdutoService
    ProdutoServiceImpl ..> ProdutoRepository
    ProdutoServiceImpl ..> Produto
    ProdutoController ..> ProdutoDTO
    ProdutoServiceImpl ..> ProdutoDTO
```

### C4 Model Nível 4: Diagrama de Classes (Domínio Pedido)

Este diagrama mostra a complexidade e os relacionamentos das classes no domínio "Pedido".

```mermaid
classDiagram
    direction TB
    
    class Pedido {
      -Long id
      -LocalDateTime dataPedido
      -StatusPedido status
      -BigDecimal valorTotal
    }
    
    class ItemPedido {
      -Long id
      -Integer quantidade
      -BigDecimal precoUnitario
    }

    class PedidoController {
        -IPedidoService pedidoService
        +ResponseEntity~Pedido~ criar(PedidoDTO dto)
    }

    class IPedidoService {
        <<Interface>>
        +Pedido salvar(PedidoDTO dto)
    }
    
    class PedidoServiceImpl {
        -IPedidoRepository pedidoRepository
        -IClienteRepository clienteRepository
        -IProdutoRepository produtoRepository
        -PedidoValidator pedidoValidator
        -PedidoCalculator pedidoCalculator
    }

    class PedidoValidator {
        +void validarPedido(Pedido pedido)
    }

    class PedidoCalculator {
        +void calcularValorTotal(Pedido pedido)
    }

    Pedido "1" -- "1..*" ItemPedido : contém
    Cliente "1" -- "0..*" Pedido : faz
    Produto "1" -- "0..*" ItemPedido : compõe

    PedidoController ..> IPedidoService
    PedidoServiceImpl ..|> IPedidoService
    PedidoServiceImpl ..> PedidoValidator
    PedidoServiceImpl ..> PedidoCalculator
    PedidoServiceImpl ..> PedidoRepository
    PedidoServiceImpl ..> ClienteRepository
    PedidoServiceImpl ..> ProdutoRepository
```

### Diagrama de Sequência: Criar um Novo Pedido

Este diagrama ilustra o fluxo complexo para a criação de um novo pedido.

```mermaid
sequenceDiagram
    participant User
    participant Controller as PedidoController
    participant Service as IPedidoService
    participant Validator as PedidoValidator
    participant Calculator as PedidoCalculator
    participant Repo as PedidoRepository
    participant DB as Banco de Dados

    User->>Controller: POST /api/pedidos com PedidoDTO
    Controller->>Service: salvar(pedidoDTO)
    
    Service->>Validator: validarPedido(pedido)
    Validator-->>Service: OK
    
    Service->>Calculator: calcularValorTotal(pedido)
    Calculator-->>Service: Valor Calculado
    
    Service->>Repo: save(pedido)
    Repo->>DB: INSERT em pedidos e itens_pedido
    DB-->>Repo: Pedido salvo
    Repo-->>Service: Retorna Pedido com ID
    
    Service-->>Controller: Retorna Pedido salvo
    Controller-->>User: Responde com 201 Created e JSON do Pedido
``` 