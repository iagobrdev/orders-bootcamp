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

A API deverá ser do tipo **RESTful** e deverá conter os seguintes métodos públicos expostos.

#### Endpoints de Clientes (`/api/clientes`)
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

#### Endpoints de Produtos (`/api/produtos`)
-   **`listarTodos()`** = Lista todos os Produtos cadastrados
    -   verbo **GET** (`/`)
-   **`buscarPorId(id)`** = Retorna 1 Produto com o ID indicado
    -   verbo **GET** (`/{id}`)
-   **`buscarPorNome(nome)`** = Lista os Produtos com similaridade de nome
    -   verbo **GET** (`/nome/{nome}`)
-   **`criar(produtoDTO)`** = Insere um novo produto
    -   verbo **POST** (`/`)
-   **`atualizar(id, produtoDTO)`** = Atualiza um produto existente
    -   verbo **PUT** (`/{id}`)
-   **`deletar(id)`** = Exclui um registro com o ID informado
    -   verbo **DELETE** (`/{id}`)
-   **`contarProdutos()`** = Retorna a quantidade de registros cadastrados
    -   verbo **GET** (`/contar`)

#### Endpoints de Pedidos (`/api/pedidos`)
-   **`listarTodos()`** = Lista todos os Pedidos cadastrados
    -   verbo **GET** (`/`)
-   **`buscarPorId(id)`** = Retorna 1 Pedido com o ID indicado
    -   verbo **GET** (`/{id}`)
-   **`buscarPorCliente(id)`** = Lista os Pedidos de um cliente específico
    -   verbo **GET** (`/cliente/{id}`)
-   **`buscarPorStatus(status)`** = Lista os Pedidos por status
    -   verbo **GET** (`/status/{status}`)
-   **`criar(pedidoDTO)`** = Insere um novo pedido
    -   verbo **POST** (`/`)
-   **`atualizar(id, pedidoDTO)`** = Atualiza um pedido existente
    -   verbo **PUT** (`/{id}`)
-   **`deletar(id)`** = Exclui um registro com o ID informado
    -   verbo **DELETE** (`/{id}`)
-   **`contarPedidos()`** = Retorna a quantidade de registros cadastrados
    -   verbo **GET** (`/contar`)

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

### C4 Model Nível 1: Diagrama de Contexto MVC

Este diagrama apresenta a visão mais abstrata do sistema, mostrando-o como uma caixa preta com a qual o usuário interage.

```mermaid
graph TD
    person_user("Usuário<br/>[Person]<br/>Cliente ou Parceiro")
    system_mvc("Sistema de Pedidos<br/>[Software System]<br/>Plataforma de gerenciamento<br/>baseada em MVC.")
    
    person_user -- "Interage via API REST" --> system_mvc
```

### C4 Model Nível 2: Diagrama de Contêineres

Este diagrama detalha os principais contêineres que compõem a solução: a aplicação API e o banco de dados.

```mermaid
graph TD
    person_user("Usuário<br/>[Person]")
    
    subgraph "Sistema de Pedidos"
        container_api("API REST<br/>[Container: Spring Boot]<br/>Expõe os endpoints e contém<br/>toda a lógica de negócio.")
        container_db("Banco de Dados<br/>[Container: PostgreSQL]<br/>Armazena os dados de<br/>Clientes, Produtos e Pedidos.")
        
        container_api -- "Lê e Escreve<br/>[JDBC]" --> container_db
    end

    person_user -- "Faz requisições<br/>[HTTPS/JSON]" --> container_api
```

### C4 Model Nível 3: Diagrama de Componentes

Este diagrama mostra os principais componentes lógicos da API, representando as funcionalidades centrais do sistema.

```mermaid
graph TD
    subgraph "Contêiner: API REST"
        component_clientes("Gestão de Clientes<br/>[Componente]<br/>Gerencia o CRUD e as<br/>regras para clientes.")
        component_produtos("Gestão de Produtos<br/>[Componente]<br/>Gerencia o CRUD e as<br/>regras para produtos.")
        component_pedidos("Gestão de Pedidos<br/>[Componente]<br/>Orquestra o CRUD e as<br/>regras para pedidos.")
    end
    
    container_db("Banco de Dados<br/>[Container: PostgreSQL]")

    component_clientes -- "Usa" --> container_db
    component_produtos -- "Usa" --> container_db
    component_pedidos -- "Usa" --> container_db
```

### C4 Model Nível 4: Pacotes, Classes e Sequência

#### Diagrama de Pacotes
Este diagrama mostra a organização dos pacotes da aplicação, refletindo a arquitetura em camadas do projeto.

```mermaid
graph TD
    subgraph "Estrutura do Projeto (src/main/java/com/br/bootcamp/orders)"
        direction LR
        controller["controller"]
        service["service"]
        repository["repository"]
        model["model"]
    end
```

#### Diagrama de Classes (Exemplo: Domínio Produto)
Este diagrama detalha as classes do domínio "Produto" como um exemplo representativo da arquitetura.

```mermaid
classDiagram
    direction LR

    class ProdutoController {
        -IProdutoService produtoService
        +listarTodos() : ResponseEntity
        +buscarPorId(id) : ResponseEntity
        +criar(dto) : ResponseEntity
    }

    class IProdutoService {
        <<Interface>>
        +listarTodos() : List~Produto~
        +buscarPorId(id) : Optional~Produto~
        +salvar(dto) : Produto
    }

    class ProdutoServiceImpl {
        -ProdutoRepository produtoRepository
    }

    class ProdutoRepository {
        <<Interface>>
        +findByNomeContainingIgnoreCase(nome) : List~Produto~
    }

    class Produto {
        -Long id
        -String nome
        -String descricao
        -BigDecimal preco
        -Integer quantidadeEstoque
        -CategoriaProduto categoria
    }

    ProdutoController ..> IProdutoService
    ProdutoServiceImpl ..|> IProdutoService
    ProdutoServiceImpl ..> ProdutoRepository
    ProdutoRepository "1" -- "0..*" Produto
```

#### Diagrama de Sequência (Exemplo: Listar Produtos)
Este diagrama ilustra a sequência de chamadas para a listagem de todos os produtos.

```mermaid
sequenceDiagram
    participant User
    participant Controller as ProdutoController
    participant Service as IProdutoService
    participant Repository as ProdutoRepository
    participant DB as Database

    User->>Controller: GET /api/produtos
    Controller->>Service: listarTodos()
    Service->>Repository: findAll()
    Repository->>DB: SELECT * FROM produtos
    DB-->>Repository: Retorna lista de produtos
    Repository-->>Service: Retorna List<Produto>
    Service-->>Controller: Retorna List<Produto>
    Controller-->>User: Resposta 200 OK com JSON
```
