package com.br.bootcamp.orders.model.dto;

import com.br.bootcamp.orders.model.enums.StatusPedido;
import com.br.bootcamp.orders.model.enums.TipoPagamento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes para PedidoDTO")
class PedidoDTOTest {

    @Test
    @DisplayName("Deve criar PedidoDTO com todos os campos")
    void deveCriarPedidoDTOComTodosOsCampos() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setClienteId(1L);
        pedidoDTO.setDataPedido(LocalDateTime.of(2024, 1, 15, 10, 30));
        pedidoDTO.setStatus(StatusPedido.PENDENTE);
        pedidoDTO.setTipoPagamento(TipoPagamento.PIX);
        
        List<PedidoDTO.ItemPedidoDTO> itens = new ArrayList<>();
        itens.add(new PedidoDTO.ItemPedidoDTO(1L, 2));
        itens.add(new PedidoDTO.ItemPedidoDTO(2L, 1));
        pedidoDTO.setItens(itens);

        assertEquals(1L, pedidoDTO.getClienteId());
        assertEquals(LocalDateTime.of(2024, 1, 15, 10, 30), pedidoDTO.getDataPedido());
        assertEquals(StatusPedido.PENDENTE, pedidoDTO.getStatus());
        assertEquals(TipoPagamento.PIX, pedidoDTO.getTipoPagamento());
        assertEquals(2, pedidoDTO.getItens().size());
    }

    @Test
    @DisplayName("Deve criar PedidoDTO com construtor")
    void deveCriarPedidoDTOComConstrutor() {
        List<PedidoDTO.ItemPedidoDTO> itens = new ArrayList<>();
        itens.add(new PedidoDTO.ItemPedidoDTO(1L, 2));
        
        PedidoDTO pedidoDTO = new PedidoDTO(
                1L,
                LocalDateTime.of(2024, 1, 15, 10, 30),
                StatusPedido.APROVADO,
                TipoPagamento.CARTAO_CREDITO,
                itens
        );

        assertEquals(1L, pedidoDTO.getClienteId());
        assertEquals(LocalDateTime.of(2024, 1, 15, 10, 30), pedidoDTO.getDataPedido());
        assertEquals(StatusPedido.APROVADO, pedidoDTO.getStatus());
        assertEquals(TipoPagamento.CARTAO_CREDITO, pedidoDTO.getTipoPagamento());
        assertEquals(1, pedidoDTO.getItens().size());
    }

    @Test
    @DisplayName("Deve criar PedidoDTO vazio")
    void deveCriarPedidoDTOVazio() {
        PedidoDTO pedidoDTO = new PedidoDTO();

        assertNull(pedidoDTO.getClienteId());
        assertNull(pedidoDTO.getDataPedido());
        assertNull(pedidoDTO.getStatus());
        assertNull(pedidoDTO.getTipoPagamento());
        assertNull(pedidoDTO.getItens());
    }

    @Test
    @DisplayName("Deve ser igual quando todos os campos são iguais")
    void deveSerIgualQuandoTodosOsCamposSaoIguais() {
        List<PedidoDTO.ItemPedidoDTO> itens1 = new ArrayList<>();
        itens1.add(new PedidoDTO.ItemPedidoDTO(1L, 2));
        
        List<PedidoDTO.ItemPedidoDTO> itens2 = new ArrayList<>();
        itens2.add(new PedidoDTO.ItemPedidoDTO(1L, 2));
        
        PedidoDTO pedido1 = new PedidoDTO(1L, LocalDateTime.of(2024, 1, 15, 10, 30), StatusPedido.PENDENTE, TipoPagamento.PIX, itens1);
        PedidoDTO pedido2 = new PedidoDTO(1L, LocalDateTime.of(2024, 1, 15, 10, 30), StatusPedido.PENDENTE, TipoPagamento.PIX, itens2);

        assertEquals(pedido1, pedido2);
        assertEquals(pedido1.hashCode(), pedido2.hashCode());
    }

    @Test
    @DisplayName("Deve ser diferente quando campos são diferentes")
    void deveSerDiferenteQuandoCamposSaoDiferentes() {
        List<PedidoDTO.ItemPedidoDTO> itens = new ArrayList<>();
        itens.add(new PedidoDTO.ItemPedidoDTO(1L, 2));
        
        PedidoDTO pedido1 = new PedidoDTO(1L, LocalDateTime.of(2024, 1, 15, 10, 30), StatusPedido.PENDENTE, TipoPagamento.PIX, itens);
        PedidoDTO pedido2 = new PedidoDTO(2L, LocalDateTime.of(2024, 1, 16, 11, 30), StatusPedido.APROVADO, TipoPagamento.CARTAO_CREDITO, itens);

        assertNotEquals(pedido1, pedido2);
        assertNotEquals(pedido1.hashCode(), pedido2.hashCode());
    }

    @Test
    @DisplayName("Deve retornar toString com todos os campos")
    void deveRetornarToStringComTodosOsCampos() {
        List<PedidoDTO.ItemPedidoDTO> itens = new ArrayList<>();
        itens.add(new PedidoDTO.ItemPedidoDTO(1L, 2));
        
        PedidoDTO pedidoDTO = new PedidoDTO(1L, LocalDateTime.of(2024, 1, 15, 10, 30), StatusPedido.PENDENTE, TipoPagamento.PIX, itens);
        String toString = pedidoDTO.toString();

        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("PIX"));
    }

    @Test
    @DisplayName("Deve ser igual a si mesmo")
    void deveSerIgualASiMesmo() {
        List<PedidoDTO.ItemPedidoDTO> itens = new ArrayList<>();
        itens.add(new PedidoDTO.ItemPedidoDTO(1L, 2));
        
        PedidoDTO pedidoDTO = new PedidoDTO(1L, LocalDateTime.of(2024, 1, 15, 10, 30), StatusPedido.PENDENTE, TipoPagamento.PIX, itens);
        assertEquals(pedidoDTO, pedidoDTO);
    }

    @Test
    @DisplayName("Deve ser diferente de null")
    void deveSerDiferenteDeNull() {
        List<PedidoDTO.ItemPedidoDTO> itens = new ArrayList<>();
        itens.add(new PedidoDTO.ItemPedidoDTO(1L, 2));
        
        PedidoDTO pedidoDTO = new PedidoDTO(1L, LocalDateTime.of(2024, 1, 15, 10, 30), StatusPedido.PENDENTE, TipoPagamento.PIX, itens);
        assertNotEquals(null, pedidoDTO);
    }

    @Test
    @DisplayName("Deve ser diferente de objeto de outro tipo")
    void deveSerDiferenteDeObjetoDeOutroTipo() {
        List<PedidoDTO.ItemPedidoDTO> itens = new ArrayList<>();
        itens.add(new PedidoDTO.ItemPedidoDTO(1L, 2));
        
        PedidoDTO pedidoDTO = new PedidoDTO(1L, LocalDateTime.of(2024, 1, 15, 10, 30), StatusPedido.PENDENTE, TipoPagamento.PIX, itens);
        String string = "teste";
        assertNotEquals(pedidoDTO, string);
    }

    @Test
    @DisplayName("Deve lidar com lista de itens vazia")
    void deveLidarComListaDeItensVazia() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setItens(new ArrayList<>());

        assertNotNull(pedidoDTO.getItens());
        assertTrue(pedidoDTO.getItens().isEmpty());
    }

    @Test
    @DisplayName("Deve lidar com data null")
    void deveLidarComDataNull() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setDataPedido(null);

        assertNull(pedidoDTO.getDataPedido());
    }

    @DisplayName("Testes para ItemPedidoDTO")
    class ItemPedidoDTOTest {

        @Test
        @DisplayName("Deve criar ItemPedidoDTO com todos os campos")
        void deveCriarItemPedidoDTOComTodosOsCampos() {
            PedidoDTO.ItemPedidoDTO itemDTO = new PedidoDTO.ItemPedidoDTO();
            itemDTO.setProdutoId(1L);
            itemDTO.setQuantidade(2);

            assertEquals(1L, itemDTO.getProdutoId());
            assertEquals(2, itemDTO.getQuantidade());
        }

        @Test
        @DisplayName("Deve criar ItemPedidoDTO com construtor")
        void deveCriarItemPedidoDTOComConstrutor() {
            PedidoDTO.ItemPedidoDTO itemDTO = new PedidoDTO.ItemPedidoDTO(1L, 3);

            assertEquals(1L, itemDTO.getProdutoId());
            assertEquals(3, itemDTO.getQuantidade());
        }

        @Test
        @DisplayName("Deve criar ItemPedidoDTO vazio")
        void deveCriarItemPedidoDTOVazio() {
            PedidoDTO.ItemPedidoDTO itemDTO = new PedidoDTO.ItemPedidoDTO();

            assertNull(itemDTO.getProdutoId());
            assertNull(itemDTO.getQuantidade());
        }

        @Test
        @DisplayName("Deve ser igual quando todos os campos são iguais")
        void deveSerIgualQuandoTodosOsCamposSaoIguais() {
            PedidoDTO.ItemPedidoDTO item1 = new PedidoDTO.ItemPedidoDTO(1L, 2);
            PedidoDTO.ItemPedidoDTO item2 = new PedidoDTO.ItemPedidoDTO(1L, 2);

            assertEquals(item1, item2);
            assertEquals(item1.hashCode(), item2.hashCode());
        }

        @Test
        @DisplayName("Deve ser diferente quando campos são diferentes")
        void deveSerDiferenteQuandoCamposSaoDiferentes() {
            PedidoDTO.ItemPedidoDTO item1 = new PedidoDTO.ItemPedidoDTO(1L, 2);
            PedidoDTO.ItemPedidoDTO item2 = new PedidoDTO.ItemPedidoDTO(2L, 3);

            assertNotEquals(item1, item2);
            assertNotEquals(item1.hashCode(), item2.hashCode());
        }

        @Test
        @DisplayName("Deve retornar toString com todos os campos")
        void deveRetornarToStringComTodosOsCampos() {
            PedidoDTO.ItemPedidoDTO itemDTO = new PedidoDTO.ItemPedidoDTO(1L, 2);
            String toString = itemDTO.toString();

            assertTrue(toString.contains("1"));
            assertTrue(toString.contains("2"));
        }

        @Test
        @DisplayName("Deve ser igual a si mesmo")
        void deveSerIgualASiMesmo() {
            PedidoDTO.ItemPedidoDTO itemDTO = new PedidoDTO.ItemPedidoDTO(1L, 2);
            assertEquals(itemDTO, itemDTO);
        }

        @Test
        @DisplayName("Deve ser diferente de null")
        void deveSerDiferenteDeNull() {
            PedidoDTO.ItemPedidoDTO itemDTO = new PedidoDTO.ItemPedidoDTO(1L, 2);
            assertNotEquals(null, itemDTO);
        }

        @Test
        @DisplayName("Deve ser diferente de objeto de outro tipo")
        void deveSerDiferenteDeObjetoDeOutroTipo() {
            PedidoDTO.ItemPedidoDTO itemDTO = new PedidoDTO.ItemPedidoDTO(1L, 2);
            String string = "teste";
            assertNotEquals(itemDTO, string);
        }

        @Test
        @DisplayName("Deve lidar com quantidade zero")
        void deveLidarComQuantidadeZero() {
            PedidoDTO.ItemPedidoDTO itemDTO = new PedidoDTO.ItemPedidoDTO();
            itemDTO.setQuantidade(0);

            assertEquals(0, itemDTO.getQuantidade());
        }
    }
} 