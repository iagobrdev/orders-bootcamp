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

Este nível do C4 Model detalha os componentes internos do contêiner "API REST". Cada diagrama abaixo representa um domínio de negócio (`Produto`, `Cliente`, `Pedido`), mostrando os principais componentes (funcionalidades) e suas interações com o banco de dados.

#### Domínio: Produto

```mermaid
graph TD
    api_container("API REST<br>[Container]")
    
    subgraph "Componentes do Domínio Produto"
        p1("Lista todos os Produtos<br>[Componente]")
        p2("Lista Produtos por Nome<br>[Componente]")
        p3("Busca Produto por Id<br>[Componente]")
        p4("Grava Produto<br>[Componente]<br>Insere e atualiza produtos")
        p5("Deleta Produto por Id<br>[Componente]")
        p6("Conta Produtos Cadastrados<br>[Componente]")
    end
    
    db("Banco de Dados<br>[Container: PostgreSQL]")

    api_container --> p1 & p2 & p3 & p4 & p5 & p6
    
    p1 -- "Lê de" --> db
    p2 -- "Lê de" --> db
    p3 -- "Lê de" --> db
    p4 -- "Escreve em" --> db
    p5 -- "Escreve em" --> db
    p6 -- "Lê de" --> db
```

#### Domínio: Cliente

```mermaid
graph TD
    api_container("API REST<br>[Container]")
    
    subgraph "Componentes do Domínio Cliente"
        c1("Lista todos os Clientes<br>[Componente]")
        c2("Lista Clientes por Nome<br>[Componente]")
        c3("Busca Cliente por Id<br>[Componente]")
        c4("Grava Cliente<br>[Componente]<br>Insere e atualiza clientes")
        c5("Deleta Cliente por Id<br>[Componente]")
        c6("Conta Clientes Cadastrados<br>[Componente]")
    end
    
    db("Banco de Dados<br>[Container: PostgreSQL]")
    
    api_container --> c1 & c2 & c3 & c4 & c5 & c6
    
    c1 -- "Lê de" --> db
    c2 -- "Lê de" --> db
    c3 -- "Lê de" --> db
    c4 -- "Escreve em" --> db
    c5 -- "Escreve em" --> db
    c6 -- "Lê de" --> db
```

#### Domínio: Pedido

```mermaid
graph TD
    api_container("API REST<br>[Container]")
    
    subgraph "Componentes do Domínio Pedido"
        o1("Lista todos os Pedidos<br>[Componente]")
        o2("Busca Pedido por Id<br>[Componente]")
        o3("Busca Pedidos por Cliente<br>[Componente]")
        o4("Busca Pedidos por Status<br>[Componente]")
        o5("Grava Pedido<br>[Componente]<br>Insere e atualiza pedidos")
        o6("Deleta Pedido por Id<br>[Componente]")
        o7("Conta Pedidos Cadastrados<br>[Componente]")
    end
    
    db("Banco de Dados<br>[Container: PostgreSQL]")

    api_container --> o1 & o2 & o3 & o4 & o5 & o6 & o7

    o1 -- "Lê de" --> db
    o2 -- "Lê de" --> db
    o3 -- "Lê de" --> db
    o4 -- "Lê de" --> db
    o5 -- "Escreve em" --> db
    o6 -- "Escreve em" --> db
    o7 -- "Lê de" --> db
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

#### Diagrama de Classes (Domínio Produto)
Este diagrama detalha as classes do domínio "Produto" como um exemplo representativo da arquitetura.

```mermaid
classDiagram
    direction LR

    class ProdutoController {
        -IProdutoService produtoService
        +listarTodos() : ResponseEntity
        +buscarPorId(id) : ResponseEntity
        +buscarPorNome(nome) : ResponseEntity
        +contarProdutos() : ResponseEntity
        +criar(dto) : ResponseEntity
        +atualizar(id, dto) : ResponseEntity
        +deletar(id) : ResponseEntity
    }

    class IProdutoService {
        <<Interface>>
        +listarTodos() : List~Produto~
        +buscarPorId(id) : Optional~Produto~
        +buscarPorNome(nome) : List~Produto~
        +contarProdutos() : long
        +salvar(dto) : Produto
        +atualizar(id, dto) : Produto
        +deletar(id) : void
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
    }

    ProdutoController ..> IProdutoService
    ProdutoServiceImpl ..|> IProdutoService
    ProdutoServiceImpl ..> ProdutoRepository
    ProdutoRepository "1" -- "0..*" Produto
```

#### Diagrama de Classes (Domínio Cliente)
Este diagrama detalha as classes e o fluxo de dependências do domínio "Cliente".

```mermaid
classDiagram
    direction LR

    class ClienteController {
        -IClienteService clienteService
        +listarTodos() : ResponseEntity
        +buscarPorId(id) : ResponseEntity
        +buscarPorNome(nome) : ResponseEntity
        +criar(dto) : ResponseEntity
        +atualizar(id, dto) : ResponseEntity
        +deletar(id) : ResponseEntity
    }

    class IClienteService {
        <<Interface>>
        +listarTodos() : List~Cliente~
        +buscarPorId(id) : Optional~Cliente~
        +buscarPorNome(nome) : List~Cliente~
        +salvar(dto) : Cliente
        +atualizar(id, dto) : Cliente
        +deletar(id) : void
    }

    class ClienteServiceImpl {
        -ClienteRepository clienteRepository
    }

    class ClienteRepository {
        <<Interface>>
        +findByNomeContainingIgnoreCase(nome) : List~Cliente~
        +findByEmail(email) : Optional~Cliente~
    }

    class Cliente {
        -Long id
        -String nome
        -String email
        -String telefone
    }

    ClienteController ..> IClienteService
    ClienteServiceImpl ..|> IClienteService
    ClienteServiceImpl ..> ClienteRepository
    ClienteRepository "1" -- "0..*" Cliente
```

#### Diagrama de Classes (Domínio Pedido)
Este diagrama detalha as classes do domínio "Pedido" e suas dependências com outros domínios.

```mermaid
classDiagram
    direction LR
    
    class PedidoController {
        -IPedidoService pedidoService
        +listarTodos() : ResponseEntity
        +buscarPorId(id) : ResponseEntity
        +buscarPorCliente(clienteId) : ResponseEntity
        +buscarPorStatus(status) : ResponseEntity
        +buscarPorData(data) : ResponseEntity
        +buscarPorPeriodo(inicio, fim) : ResponseEntity
        +contarPedidos() : ResponseEntity
        +calcularValorTotal(id) : ResponseEntity
        +criar(dto) : ResponseEntity
        +atualizar(id, dto) : ResponseEntity
        +atualizarStatus(id, status) : ResponseEntity
        +deletar(id) : ResponseEntity
    }

    class IPedidoService {
        +listarTodos() : List~Pedido~
        +buscarPorId(id) : Optional~Pedido~
        +buscarPorCliente(id) : List~Pedido~
        +buscarPorStatus(status) : List~Pedido~
        +buscarPorData(data) : List~Pedido~
        +buscarPorPeriodo(inicio, fim) : List~Pedido~
        +contarPedidos() : long
        +calcularValorTotal(id) : Double
        +salvar(dto) : Pedido
        +atualizar(id, dto) : Pedido
        +atualizarStatus(id, status) : Pedido
        +deletar(id) : void
    }
    <<Interface>> IPedidoService

    class PedidoServiceImpl {
        -PedidoRepository pedidoRepository
        -ClienteRepository clienteRepository
        -ProdutoRepository produtoRepository
    }
    
    class PedidoRepository {
        % Nenhum método para simplificar
    }
    <<Interface>> PedidoRepository
    
    class Pedido {
        -Long id
        -Cliente cliente
        -LocalDateTime dataPedido
        -StatusPedido status
        -TipoPagamento tipoPagamento
        -BigDecimal valorTotal
    }
    
    class ItemPedido {
        -Long id
        -Produto produto
        -Integer quantidade
        -BigDecimal precoUnitario
        -BigDecimal subtotal
    }

    class Cliente
    class Produto

    PedidoController ..> IPedidoService
    PedidoServiceImpl ..|> IPedidoService
    PedidoServiceImpl ..> PedidoRepository
    PedidoServiceImpl ..> ClienteRepository
    PedidoServiceImpl ..> ProdutoRepository
    
    PedidoRepository --o Pedido
    Pedido "1" *-- "N" ItemPedido
    Cliente "1" -- "0..*" Pedido
    Produto "1" -- "0..*" ItemPedido