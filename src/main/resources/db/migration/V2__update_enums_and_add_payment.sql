ALTER TABLE produtos
ALTER COLUMN categoria TYPE VARCHAR(50);

ALTER TABLE produtos
ADD CONSTRAINT chk_categoria_produto 
CHECK (categoria IN (
    'ELETRONICOS', 'VESTUARIO', 'CASA_DECORACAO', 'BELEZA', 'ESPORTES',
    'INFORMATICA', 'ALIMENTACAO', 'SAUDE', 'INFANTIL', 'AUTOMOTIVO',
    'JARDINAGEM', 'LIVROS', 'OUTROS'
));

ALTER TABLE pedidos
ALTER COLUMN status TYPE VARCHAR(20);

ALTER TABLE pedidos
ADD CONSTRAINT chk_status_pedido 
CHECK (status IN (
    'PENDENTE', 'APROVADO', 'EM_PREPARACAO', 'ENVIADO', 'ENTREGUE', 'CANCELADO'
));

ALTER TABLE pedidos
ADD COLUMN tipo_pagamento VARCHAR(30) NOT NULL DEFAULT 'DINHEIRO';

ALTER TABLE pedidos
ADD CONSTRAINT chk_tipo_pagamento 
CHECK (tipo_pagamento IN (
    'DINHEIRO', 'CARTAO_CREDITO', 'CARTAO_DEBITO', 'PIX', 'TRANSFERENCIA',
    'BOLETO', 'CARTEIRA_DIGITAL', 'VALE_REFEICAO', 'VALE_ALIMENTACAO',
    'CUPOM', 'OUTROS'
));

CREATE INDEX idx_produtos_categoria ON produtos(categoria);
CREATE INDEX idx_pedidos_status ON pedidos(status);
CREATE INDEX idx_pedidos_tipo_pagamento ON pedidos(tipo_pagamento);

COMMENT ON COLUMN produtos.categoria IS 'Categoria do produto usando enum CategoriaProduto';
COMMENT ON COLUMN pedidos.status IS 'Status do pedido usando enum StatusPedido';
COMMENT ON COLUMN pedidos.tipo_pagamento IS 'Tipo de pagamento usando enum TipoPagamento'; 