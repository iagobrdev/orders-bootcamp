package com.br.bootcamp.orders.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes para StatusPedido")
class StatusPedidoTest {

    @Test
    @DisplayName("Deve retornar descrição correta para cada status")
    void deveRetornarDescricaoCorreta() {
        assertEquals("Pendente", StatusPedido.PENDENTE.getDescricao());
        assertEquals("Aprovado", StatusPedido.APROVADO.getDescricao());
        assertEquals("Em Preparação", StatusPedido.EM_PREPARACAO.getDescricao());
        assertEquals("Enviado", StatusPedido.ENVIADO.getDescricao());
        assertEquals("Entregue", StatusPedido.ENTREGUE.getDescricao());
        assertEquals("Cancelado", StatusPedido.CANCELADO.getDescricao());
    }

    @Test
    @DisplayName("Deve retornar detalhes corretos para cada status")
    void deveRetornarDetalhesCorretos() {
        assertEquals("Pedido aguardando aprovação", StatusPedido.PENDENTE.getDetalhes());
        assertEquals("Pedido aprovado pelo cliente", StatusPedido.APROVADO.getDetalhes());
        assertEquals("Produtos sendo preparados", StatusPedido.EM_PREPARACAO.getDetalhes());
        assertEquals("Pedido enviado para entrega", StatusPedido.ENVIADO.getDetalhes());
        assertEquals("Pedido entregue ao cliente", StatusPedido.ENTREGUE.getDetalhes());
        assertEquals("Pedido cancelado", StatusPedido.CANCELADO.getDetalhes());
    }

    @Test
    @DisplayName("Deve permitir cancelamento para status apropriados")
    void devePermitirCancelamentoParaStatusApropriados() {
        assertTrue(StatusPedido.PENDENTE.permiteCancelamento());
        assertTrue(StatusPedido.APROVADO.permiteCancelamento());
        assertTrue(StatusPedido.EM_PREPARACAO.permiteCancelamento());
        assertFalse(StatusPedido.ENVIADO.permiteCancelamento());
        assertFalse(StatusPedido.ENTREGUE.permiteCancelamento());
        assertFalse(StatusPedido.CANCELADO.permiteCancelamento());
    }

    @Test
    @DisplayName("Deve identificar status final corretamente")
    void deveIdentificarStatusFinalCorretamente() {
        assertFalse(StatusPedido.PENDENTE.isStatusFinal());
        assertFalse(StatusPedido.APROVADO.isStatusFinal());
        assertFalse(StatusPedido.EM_PREPARACAO.isStatusFinal());
        assertFalse(StatusPedido.ENVIADO.isStatusFinal());
        assertTrue(StatusPedido.ENTREGUE.isStatusFinal());
        assertTrue(StatusPedido.CANCELADO.isStatusFinal());
    }

    @Test
    @DisplayName("Deve permitir alteração para status apropriados")
    void devePermitirAlteracaoParaStatusApropriados() {
        assertTrue(StatusPedido.PENDENTE.permiteAlteracao());
        assertTrue(StatusPedido.APROVADO.permiteAlteracao());
        assertFalse(StatusPedido.EM_PREPARACAO.permiteAlteracao());
        assertFalse(StatusPedido.ENVIADO.permiteAlteracao());
        assertFalse(StatusPedido.ENTREGUE.permiteAlteracao());
        assertFalse(StatusPedido.CANCELADO.permiteAlteracao());
    }

    @Test
    @DisplayName("Deve retornar descrição no toString")
    void deveRetornarDescricaoNoToString() {
        assertEquals("Pendente", StatusPedido.PENDENTE.toString());
        assertEquals("Aprovado", StatusPedido.APROVADO.toString());
        assertEquals("Em Preparação", StatusPedido.EM_PREPARACAO.toString());
        assertEquals("Enviado", StatusPedido.ENVIADO.toString());
        assertEquals("Entregue", StatusPedido.ENTREGUE.toString());
        assertEquals("Cancelado", StatusPedido.CANCELADO.toString());
    }

    @Test
    @DisplayName("Deve ter valores únicos para cada status")
    void deveTerValoresUnicosParaCadaStatus() {
        assertEquals(6, StatusPedido.values().length);
        
        // Verifica se todos os valores são únicos
        long count = java.util.Arrays.stream(StatusPedido.values())
                .map(StatusPedido::getDescricao)
                .distinct()
                .count();
        assertEquals(6, count);
    }
} 