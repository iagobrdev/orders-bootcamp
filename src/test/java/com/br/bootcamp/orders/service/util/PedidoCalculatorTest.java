package com.br.bootcamp.orders.service.util;

import com.br.bootcamp.orders.model.Cliente;
import com.br.bootcamp.orders.model.ItemPedido;
import com.br.bootcamp.orders.model.Pedido;
import com.br.bootcamp.orders.model.Produto;
import com.br.bootcamp.orders.model.enums.StatusPedido;
import com.br.bootcamp.orders.model.enums.TipoPagamento;
import com.br.bootcamp.orders.service.contracts.IProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Testes para PedidoCalculator")
class PedidoCalculatorTest {

    @Mock
    private IProdutoService produtoService;

    private PedidoCalculator pedidoCalculator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedidoCalculator = new PedidoCalculator(produtoService);
    }

    @Test
    @DisplayName("Deve calcular valor total de pedido com itens")
    void deveCalcularValorTotalDePedidoComItens() {
        // Arrange
        Pedido pedido = criarPedidoComItens();
        
        // Act
        BigDecimal valorTotal = pedidoCalculator.calcularValorTotal(pedido);
        
        // Assert
        assertEquals(new BigDecimal("0"), valorTotal);
    }

    @Test
    @DisplayName("Deve calcular valor total de pedido sem itens")
    void deveCalcularValorTotalDePedidoSemItens() {
        // Arrange
        Pedido pedido = criarPedidoVazio();
        
        // Act
        BigDecimal valorTotal = pedidoCalculator.calcularValorTotal(pedido);
        
        // Assert
        assertEquals(BigDecimal.ZERO, valorTotal);
    }

    @Test
    @DisplayName("Deve calcular valor total de pedido com itens null")
    void deveCalcularValorTotalDePedidoComItensNull() {
        // Arrange
        Pedido pedido = criarPedidoVazio();
        pedido.setItens(null);
        
        // Act
        BigDecimal valorTotal = pedidoCalculator.calcularValorTotal(pedido);
        
        // Assert
        assertEquals(BigDecimal.ZERO, valorTotal);
    }

    @Test
    @DisplayName("Deve calcular subtotal de item")
    void deveCalcularSubtotalDeItem() {
        // Arrange
        ItemPedido item = new ItemPedido();
        item.setPrecoUnitario(new BigDecimal("25.00"));
        item.setQuantidade(3);
        
        // Act
        BigDecimal subtotal = pedidoCalculator.calcularSubtotalItem(item);
        
        // Assert
        assertEquals(new BigDecimal("75.00"), subtotal);
    }

    @Test
    @DisplayName("Deve calcular subtotal de item com preço null")
    void deveCalcularSubtotalDeItemComPrecoNull() {
        // Arrange
        ItemPedido item = new ItemPedido();
        item.setPrecoUnitario(null);
        item.setQuantidade(3);
        
        // Act
        BigDecimal subtotal = pedidoCalculator.calcularSubtotalItem(item);
        
        // Assert
        assertEquals(BigDecimal.ZERO, subtotal);
    }

    @Test
    @DisplayName("Deve calcular subtotal de item com quantidade null")
    void deveCalcularSubtotalDeItemComQuantidadeNull() {
        // Arrange
        ItemPedido item = new ItemPedido();
        item.setPrecoUnitario(new BigDecimal("25.00"));
        item.setQuantidade(null);
        
        // Act
        BigDecimal subtotal = pedidoCalculator.calcularSubtotalItem(item);
        
        // Assert
        assertEquals(BigDecimal.ZERO, subtotal);
    }

    @Test
    @DisplayName("Deve preparar itens do pedido")
    void devePrepararItensDoPedido() {
        // Arrange
        Pedido pedido = criarPedidoComItens();
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setPreco(new BigDecimal("50.00"));
        
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.of(produto));
        
        // Act
        pedidoCalculator.prepararItens(pedido);
        
        // Assert
        assertNotNull(pedido.getItens());
        assertEquals(2, pedido.getItens().size());
        
        ItemPedido primeiroItem = pedido.getItens().get(0);
        assertEquals(new BigDecimal("50.00"), primeiroItem.getPrecoUnitario());
        assertEquals(new BigDecimal("100.00"), primeiroItem.getSubtotal());
        assertEquals(pedido, primeiroItem.getPedido());
        
        verify(produtoService, times(2)).buscarPorId(anyLong());
    }

    @Test
    @DisplayName("Deve preparar itens de pedido vazio")
    void devePrepararItensDePedidoVazio() {
        // Arrange
        Pedido pedido = criarPedidoVazio();
        
        // Act
        pedidoCalculator.prepararItens(pedido);
        
        // Assert
        assertNotNull(pedido.getItens());
        assertTrue(pedido.getItens().isEmpty());
        
        verify(produtoService, never()).buscarPorId(anyLong());
    }

    @Test
    @DisplayName("Deve preparar itens de pedido com itens null")
    void devePrepararItensDePedidoComItensNull() {
        // Arrange
        Pedido pedido = criarPedidoVazio();
        pedido.setItens(null);
        
        // Act
        pedidoCalculator.prepararItens(pedido);
        
        // Assert
        assertNull(pedido.getItens());
        
        verify(produtoService, never()).buscarPorId(anyLong());
    }

    @Test
    @DisplayName("Deve calcular e atualizar valor total do pedido")
    void deveCalcularEAtualizarValorTotalDoPedido() {
        // Arrange
        Pedido pedido = criarPedidoComItens();
        
        // Act
        pedidoCalculator.calcularEAtualizarValorTotal(pedido);
        
        // Assert
        assertEquals(new BigDecimal("0"), pedido.getValorTotal());
    }

    @Test
    @DisplayName("Deve preparar pedido completamente")
    void devePrepararPedidoCompletamente() {
        // Arrange
        Pedido pedido = criarPedidoComItens();
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setPreco(new BigDecimal("50.00"));
        
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.of(produto));
        
        // Act
        pedidoCalculator.prepararPedido(pedido);
        
        // Assert
        assertNotNull(pedido.getItens());
        assertEquals(2, pedido.getItens().size());
        assertEquals(new BigDecimal("100.00"), pedido.getValorTotal());
        
        verify(produtoService, times(2)).buscarPorId(anyLong());
    }

    @Test
    @DisplayName("Deve lidar com produto não encontrado")
    void deveLidarComProdutoNaoEncontrado() {
        // Arrange
        Pedido pedido = criarPedidoComItens();
        
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.empty());
        
        // Act
        pedidoCalculator.prepararItens(pedido);
        
        // Assert
        assertNotNull(pedido.getItens());
        assertEquals(2, pedido.getItens().size());
        
        // Verifica que os itens não foram modificados quando produto não foi encontrado
        ItemPedido primeiroItem = pedido.getItens().get(0);
        assertNull(primeiroItem.getPrecoUnitario());
        assertNull(primeiroItem.getSubtotal());
        
        verify(produtoService, times(2)).buscarPorId(anyLong());
    }

    private Pedido criarPedidoComItens() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(new Cliente());
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setTipoPagamento(TipoPagamento.PIX);
        
        List<ItemPedido> itens = new ArrayList<>();
        
        ItemPedido item1 = new ItemPedido();
        item1.setId(1L);
        item1.setProduto(new Produto());
        item1.getProduto().setId(1L);
        item1.setQuantidade(2);
        item1.setPedido(pedido);
        
        ItemPedido item2 = new ItemPedido();
        item2.setId(2L);
        item2.setProduto(new Produto());
        item2.getProduto().setId(2L);
        item2.setQuantidade(1);
        item2.setPedido(pedido);
        
        itens.add(item1);
        itens.add(item2);
        pedido.setItens(itens);
        
        return pedido;
    }

    private Pedido criarPedidoVazio() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(new Cliente());
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setTipoPagamento(TipoPagamento.PIX);
        pedido.setItens(new ArrayList<>());
        
        return pedido;
    }
} 