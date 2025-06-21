package com.br.bootcamp.orders.model.enums;

import lombok.Getter;

/**
 * Enum que representa os tipos de pagamento aceitos no sistema.
 * 
 * <p>Este enum define as formas de pagamento disponíveis para os pedidos,
 * facilitando o controle e processamento dos pagamentos.</p>
 * 
 * @author Bootcamp Architecture Software
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public enum TipoPagamento {
    
    /**
     * Pagamento em dinheiro.
     */
    DINHEIRO("Dinheiro", "Pagamento em dinheiro"),
    
    /**
     * Pagamento com cartão de crédito.
     */
    CARTAO_CREDITO("Cartão de Crédito", "Pagamento com cartão de crédito"),
    
    /**
     * Pagamento com cartão de débito.
     */
    CARTAO_DEBITO("Cartão de Débito", "Pagamento com cartão de débito"),
    
    /**
     * Pagamento via PIX.
     */
    PIX("PIX", "Pagamento via PIX"),
    
    /**
     * Pagamento via transferência bancária.
     */
    TRANSFERENCIA("Transferência", "Pagamento via transferência bancária"),
    
    /**
     * Pagamento com boleto bancário.
     */
    BOLETO("Boleto", "Pagamento com boleto bancário"),
    
    /**
     * Pagamento via carteira digital.
     */
    CARTEIRA_DIGITAL("Carteira Digital", "Pagamento via carteira digital"),
    
    /**
     * Pagamento com vale refeição.
     */
    VALE_REFEICAO("Vale Refeição", "Pagamento com vale refeição"),
    
    /**
     * Pagamento com vale alimentação.
     */
    VALE_ALIMENTACAO("Vale Alimentação", "Pagamento com vale alimentação"),
    
    /**
     * Pagamento com cupom de desconto.
     */
    CUPOM("Cupom", "Pagamento com cupom de desconto"),
    
    /**
     * Outros tipos de pagamento.
     */
    OUTROS("Outros", "Outros tipos de pagamento");

    /**
     * -- GETTER --
     *  Retorna a descrição amigável do tipo de pagamento.
     *
     */
    private final String descricao;
    /**
     * -- GETTER --
     *  Retorna os detalhes do tipo de pagamento.
     *
     */
    private final String detalhes;
    
    /**
     * Construtor do enum.
     * 
     * @param descricao Descrição amigável do tipo de pagamento
     * @param detalhes Detalhes adicionais sobre o tipo de pagamento
     */
    TipoPagamento(String descricao, String detalhes) {
        this.descricao = descricao;
        this.detalhes = detalhes;
    }

    /**
     * Verifica se o tipo de pagamento é digital.
     * 
     * @return true se é pagamento digital, false caso contrário
     */
    public boolean isPagamentoDigital() {
        return this == PIX || this == CARTAO_CREDITO || this == CARTAO_DEBITO || 
               this == CARTEIRA_DIGITAL || this == TRANSFERENCIA;
    }
    
    /**
     * Verifica se o tipo de pagamento é em dinheiro.
     * 
     * @return true se é pagamento em dinheiro, false caso contrário
     */
    public boolean isPagamentoDinheiro() {
        return this == DINHEIRO;
    }
    
    /**
     * Verifica se o tipo de pagamento é com cartão.
     * 
     * @return true se é pagamento com cartão, false caso contrário
     */
    public boolean isPagamentoCartao() {
        return this == CARTAO_CREDITO || this == CARTAO_DEBITO;
    }
    
    /**
     * Verifica se o tipo de pagamento é com vale.
     * 
     * @return true se é pagamento com vale, false caso contrário
     */
    public boolean isPagamentoVale() {
        return this == VALE_REFEICAO || this == VALE_ALIMENTACAO;
    }
    
    /**
     * Verifica se o tipo de pagamento requer processamento.
     * 
     * @return true se requer processamento, false caso contrário
     */
    public boolean requerProcessamento() {
        return this != DINHEIRO && this != CUPOM;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
} 