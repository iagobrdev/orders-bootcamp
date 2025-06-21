CREATE TABLE clientes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    endereco VARCHAR(200)
);

CREATE TABLE produtos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    preco NUMERIC(10,2) NOT NULL,
    quantidade_estoque INTEGER NOT NULL,
    categoria VARCHAR(50)
);

CREATE TABLE pedidos (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL REFERENCES clientes(id),
    data_pedido TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    valor_total NUMERIC(10,2) NOT NULL
);

CREATE TABLE itens_pedido (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    produto_id BIGINT NOT NULL REFERENCES produtos(id),
    quantidade INTEGER NOT NULL,
    preco_unitario NUMERIC(10,2) NOT NULL,
    subtotal NUMERIC(10,2) NOT NULL
);

CREATE INDEX idx_pedidos_cliente_id ON pedidos(cliente_id);
CREATE INDEX idx_itens_pedido_pedido_id ON itens_pedido(pedido_id);
CREATE INDEX idx_itens_pedido_produto_id ON itens_pedido(produto_id); 