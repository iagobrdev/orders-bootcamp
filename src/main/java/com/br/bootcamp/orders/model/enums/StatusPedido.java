package com.br.bootcamp.orders.model.enums;

import lombok.Getter;

/**
 * Enum que representa os possíveis status de um pedido no sistema.
 * 
 * <p>Este enum define todos os estados que um pedido pode assumir durante
 * seu ciclo de vida, desde a criação até a entrega ou cancelamento.</p>
 * 
 * @author Bootcamp Architecture Software
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public enum StatusPedido {
    
    /**
     * Pedido criado e aguardando aprovação.
     * Estado inicial de todos os pedidos.
     */
    PENDENTE("Pendente", "Pedido aguardando aprovação"),
    
    /**
     * Pedido aprovado e pronto para processamento.
     * Cliente confirmou o pedido.
     */
    APROVADO("Aprovado", "Pedido aprovado pelo cliente"),
    
    /**
     * Pedido em processo de preparação.
     * Produtos estão sendo preparados para envio.
     */
    EM_PREPARACAO("Em Preparação", "Produtos sendo preparados"),
    
    /**
     * Pedido enviado para entrega.
     * Produtos foram enviados ao cliente.
     */
    ENVIADO("Enviado", "Pedido enviado para entrega"),
    
    /**
     * Pedido entregue com sucesso.
     * Cliente recebeu os produtos.
     */
    ENTREGUE("Entregue", "Pedido entregue ao cliente"),
    
    /**
     * Pedido cancelado.
     * Pedido foi cancelado por qualquer motivo.
     */
    CANCELADO("Cancelado", "Pedido cancelado");

    /**
     * -- GETTER --
     *  Retorna a descrição amigável do status.
     *
     */
    private final String descricao;
    /**
     * -- GETTER --
     *  Retorna os detalhes do status.
     *
     */
    private final String detalhes;
    
    /**
     * Construtor do enum.
     * 
     * @param descricao Descrição amigável do status
     * @param detalhes Detalhes adicionais sobre o status
     */
    StatusPedido(String descricao, String detalhes) {
        this.descricao = descricao;
        this.detalhes = detalhes;
    }

    /**
     * Verifica se o status permite cancelamento.
     * 
     * @return true se o pedido pode ser cancelado, false caso contrário
     */
    public boolean permiteCancelamento() {
        return this == PENDENTE || this == APROVADO || this == EM_PREPARACAO;
    }
    
    /**
     * Verifica se o status é final (não pode mais ser alterado).
     * 
     * @return true se o status é final, false caso contrário
     */
    public boolean isStatusFinal() {
        return this == ENTREGUE || this == CANCELADO;
    }
    
    /**
     * Verifica se o status permite alteração de itens.
     * 
     * @return true se permite alteração, false caso contrário
     */
    public boolean permiteAlteracao() {
        return this == PENDENTE || this == APROVADO;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
} 