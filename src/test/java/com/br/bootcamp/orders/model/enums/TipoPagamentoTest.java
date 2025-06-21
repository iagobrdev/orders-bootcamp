package com.br.bootcamp.orders.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes para TipoPagamento")
class TipoPagamentoTest {

    @Test
    @DisplayName("Deve retornar descrição correta para cada tipo de pagamento")
    void deveRetornarDescricaoCorreta() {
        assertEquals("Dinheiro", TipoPagamento.DINHEIRO.getDescricao());
        assertEquals("Cartão de Crédito", TipoPagamento.CARTAO_CREDITO.getDescricao());
        assertEquals("Cartão de Débito", TipoPagamento.CARTAO_DEBITO.getDescricao());
        assertEquals("PIX", TipoPagamento.PIX.getDescricao());
        assertEquals("Transferência", TipoPagamento.TRANSFERENCIA.getDescricao());
        assertEquals("Boleto", TipoPagamento.BOLETO.getDescricao());
        assertEquals("Carteira Digital", TipoPagamento.CARTEIRA_DIGITAL.getDescricao());
        assertEquals("Vale Refeição", TipoPagamento.VALE_REFEICAO.getDescricao());
        assertEquals("Vale Alimentação", TipoPagamento.VALE_ALIMENTACAO.getDescricao());
        assertEquals("Cupom", TipoPagamento.CUPOM.getDescricao());
        assertEquals("Outros", TipoPagamento.OUTROS.getDescricao());
    }

    @Test
    @DisplayName("Deve retornar detalhes corretos para cada tipo de pagamento")
    void deveRetornarDetalhesCorretos() {
        assertEquals("Pagamento em dinheiro", TipoPagamento.DINHEIRO.getDetalhes());
        assertEquals("Pagamento com cartão de crédito", TipoPagamento.CARTAO_CREDITO.getDetalhes());
        assertEquals("Pagamento com cartão de débito", TipoPagamento.CARTAO_DEBITO.getDetalhes());
        assertEquals("Pagamento via PIX", TipoPagamento.PIX.getDetalhes());
        assertEquals("Pagamento via transferência bancária", TipoPagamento.TRANSFERENCIA.getDetalhes());
        assertEquals("Pagamento com boleto bancário", TipoPagamento.BOLETO.getDetalhes());
        assertEquals("Pagamento via carteira digital", TipoPagamento.CARTEIRA_DIGITAL.getDetalhes());
        assertEquals("Pagamento com vale refeição", TipoPagamento.VALE_REFEICAO.getDetalhes());
        assertEquals("Pagamento com vale alimentação", TipoPagamento.VALE_ALIMENTACAO.getDetalhes());
        assertEquals("Pagamento com cupom de desconto", TipoPagamento.CUPOM.getDetalhes());
        assertEquals("Outros tipos de pagamento", TipoPagamento.OUTROS.getDetalhes());
    }

    @Test
    @DisplayName("Deve identificar pagamento digital corretamente")
    void deveIdentificarPagamentoDigitalCorretamente() {
        assertTrue(TipoPagamento.PIX.isPagamentoDigital());
        assertTrue(TipoPagamento.CARTAO_CREDITO.isPagamentoDigital());
        assertTrue(TipoPagamento.CARTAO_DEBITO.isPagamentoDigital());
        assertTrue(TipoPagamento.CARTEIRA_DIGITAL.isPagamentoDigital());
        assertTrue(TipoPagamento.TRANSFERENCIA.isPagamentoDigital());
        assertFalse(TipoPagamento.DINHEIRO.isPagamentoDigital());
        assertFalse(TipoPagamento.BOLETO.isPagamentoDigital());
        assertFalse(TipoPagamento.VALE_REFEICAO.isPagamentoDigital());
        assertFalse(TipoPagamento.VALE_ALIMENTACAO.isPagamentoDigital());
        assertFalse(TipoPagamento.CUPOM.isPagamentoDigital());
        assertFalse(TipoPagamento.OUTROS.isPagamentoDigital());
    }

    @Test
    @DisplayName("Deve identificar pagamento em dinheiro corretamente")
    void deveIdentificarPagamentoDinheiroCorretamente() {
        assertTrue(TipoPagamento.DINHEIRO.isPagamentoDinheiro());
        assertFalse(TipoPagamento.CARTAO_CREDITO.isPagamentoDinheiro());
        assertFalse(TipoPagamento.CARTAO_DEBITO.isPagamentoDinheiro());
        assertFalse(TipoPagamento.PIX.isPagamentoDinheiro());
        assertFalse(TipoPagamento.TRANSFERENCIA.isPagamentoDinheiro());
        assertFalse(TipoPagamento.BOLETO.isPagamentoDinheiro());
        assertFalse(TipoPagamento.CARTEIRA_DIGITAL.isPagamentoDinheiro());
        assertFalse(TipoPagamento.VALE_REFEICAO.isPagamentoDinheiro());
        assertFalse(TipoPagamento.VALE_ALIMENTACAO.isPagamentoDinheiro());
        assertFalse(TipoPagamento.CUPOM.isPagamentoDinheiro());
        assertFalse(TipoPagamento.OUTROS.isPagamentoDinheiro());
    }

    @Test
    @DisplayName("Deve identificar pagamento com cartão corretamente")
    void deveIdentificarPagamentoCartaoCorretamente() {
        assertTrue(TipoPagamento.CARTAO_CREDITO.isPagamentoCartao());
        assertTrue(TipoPagamento.CARTAO_DEBITO.isPagamentoCartao());
        assertFalse(TipoPagamento.DINHEIRO.isPagamentoCartao());
        assertFalse(TipoPagamento.PIX.isPagamentoCartao());
        assertFalse(TipoPagamento.TRANSFERENCIA.isPagamentoCartao());
        assertFalse(TipoPagamento.BOLETO.isPagamentoCartao());
        assertFalse(TipoPagamento.CARTEIRA_DIGITAL.isPagamentoCartao());
        assertFalse(TipoPagamento.VALE_REFEICAO.isPagamentoCartao());
        assertFalse(TipoPagamento.VALE_ALIMENTACAO.isPagamentoCartao());
        assertFalse(TipoPagamento.CUPOM.isPagamentoCartao());
        assertFalse(TipoPagamento.OUTROS.isPagamentoCartao());
    }

    @Test
    @DisplayName("Deve identificar pagamento com vale corretamente")
    void deveIdentificarPagamentoValeCorretamente() {
        assertTrue(TipoPagamento.VALE_REFEICAO.isPagamentoVale());
        assertTrue(TipoPagamento.VALE_ALIMENTACAO.isPagamentoVale());
        assertFalse(TipoPagamento.DINHEIRO.isPagamentoVale());
        assertFalse(TipoPagamento.CARTAO_CREDITO.isPagamentoVale());
        assertFalse(TipoPagamento.CARTAO_DEBITO.isPagamentoVale());
        assertFalse(TipoPagamento.PIX.isPagamentoVale());
        assertFalse(TipoPagamento.TRANSFERENCIA.isPagamentoVale());
        assertFalse(TipoPagamento.BOLETO.isPagamentoVale());
        assertFalse(TipoPagamento.CARTEIRA_DIGITAL.isPagamentoVale());
        assertFalse(TipoPagamento.CUPOM.isPagamentoVale());
        assertFalse(TipoPagamento.OUTROS.isPagamentoVale());
    }

    @Test
    @DisplayName("Deve identificar se requer processamento corretamente")
    void deveIdentificarSeRequerProcessamentoCorretamente() {
        assertFalse(TipoPagamento.DINHEIRO.requerProcessamento());
        assertFalse(TipoPagamento.CUPOM.requerProcessamento());
        assertTrue(TipoPagamento.CARTAO_CREDITO.requerProcessamento());
        assertTrue(TipoPagamento.CARTAO_DEBITO.requerProcessamento());
        assertTrue(TipoPagamento.PIX.requerProcessamento());
        assertTrue(TipoPagamento.TRANSFERENCIA.requerProcessamento());
        assertTrue(TipoPagamento.BOLETO.requerProcessamento());
        assertTrue(TipoPagamento.CARTEIRA_DIGITAL.requerProcessamento());
        assertTrue(TipoPagamento.VALE_REFEICAO.requerProcessamento());
        assertTrue(TipoPagamento.VALE_ALIMENTACAO.requerProcessamento());
        assertTrue(TipoPagamento.OUTROS.requerProcessamento());
    }

    @Test
    @DisplayName("Deve retornar descrição no toString")
    void deveRetornarDescricaoNoToString() {
        assertEquals("Dinheiro", TipoPagamento.DINHEIRO.toString());
        assertEquals("Cartão de Crédito", TipoPagamento.CARTAO_CREDITO.toString());
        assertEquals("Cartão de Débito", TipoPagamento.CARTAO_DEBITO.toString());
        assertEquals("PIX", TipoPagamento.PIX.toString());
        assertEquals("Transferência", TipoPagamento.TRANSFERENCIA.toString());
        assertEquals("Boleto", TipoPagamento.BOLETO.toString());
        assertEquals("Carteira Digital", TipoPagamento.CARTEIRA_DIGITAL.toString());
        assertEquals("Vale Refeição", TipoPagamento.VALE_REFEICAO.toString());
        assertEquals("Vale Alimentação", TipoPagamento.VALE_ALIMENTACAO.toString());
        assertEquals("Cupom", TipoPagamento.CUPOM.toString());
        assertEquals("Outros", TipoPagamento.OUTROS.toString());
    }

    @Test
    @DisplayName("Deve ter valores únicos para cada tipo de pagamento")
    void deveTerValoresUnicosParaCadaTipoPagamento() {
        assertEquals(11, TipoPagamento.values().length);
        
        // Verifica se todos os valores são únicos
        long count = java.util.Arrays.stream(TipoPagamento.values())
                .map(TipoPagamento::getDescricao)
                .distinct()
                .count();
        assertEquals(11, count);
    }
} 