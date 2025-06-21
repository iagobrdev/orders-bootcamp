package com.br.bootcamp.orders.service.util;

import com.br.bootcamp.orders.model.Cliente;
import com.br.bootcamp.orders.model.ItemPedido;
import com.br.bootcamp.orders.model.Pedido;
import com.br.bootcamp.orders.model.Produto;
import com.br.bootcamp.orders.service.contracts.IClienteService;
import com.br.bootcamp.orders.service.contracts.IProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Testes para PedidoValidator")
class PedidoValidatorTest {

    @Mock
    private IClienteService clienteService;

    @Mock
    private IProdutoService produtoService;

    private PedidoValidator pedidoValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedidoValidator = new PedidoValidator(clienteService, produtoService);
    }

    @Test
    @DisplayName("Deve validar pedido válido")
    void deveValidarPedidoValido() {
        // Arrange
        Pedido pedido = criarPedidoValido();
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setQuantidadeEstoque(10);
        
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.of(produto));
        
        // Act & Assert
        assertDoesNotThrow(() -> pedidoValidator.validarPedido(pedido));
        
        verify(clienteService).buscarPorId(1L);
        verify(produtoService).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando pedido é null")
    void deveLancarExcecaoQuandoPedidoENull() {
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> pedidoValidator.validarPedido(null));
        
        assertEquals("Pedido não pode ser null", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente é null")
    void deveLancarExcecaoQuandoClienteENull() {
        // Arrange
        Pedido pedido = criarPedidoValido();
        pedido.setCliente(null);
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> pedidoValidator.validarPedido(pedido));
        
        assertEquals("Cliente é obrigatório para criar um pedido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente ID é null")
    void deveLancarExcecaoQuandoClienteIdENull() {
        // Arrange
        Pedido pedido = criarPedidoValido();
        pedido.getCliente().setId(null);
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> pedidoValidator.validarPedido(pedido));
        
        assertEquals("Cliente é obrigatório para criar um pedido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não existe")
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        // Arrange
        Pedido pedido = criarPedidoValido();
        
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.empty());
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> pedidoValidator.validarPedido(pedido));
        
        assertEquals("Cliente não encontrado com ID: 1", exception.getMessage());
        
        verify(clienteService).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve validar pedido sem itens")
    void deveValidarPedidoSemItens() {
        // Arrange
        Pedido pedido = criarPedidoValido();
        pedido.setItens(new ArrayList<>());
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        
        // Act & Assert
        assertDoesNotThrow(() -> pedidoValidator.validarPedido(pedido));
        
        verify(clienteService).buscarPorId(1L);
        verify(produtoService, never()).buscarPorId(anyLong());
    }

    @Test
    @DisplayName("Deve validar pedido com itens null")
    void deveValidarPedidoComItensNull() {
        // Arrange
        Pedido pedido = criarPedidoValido();
        pedido.setItens(null);
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        
        // Act & Assert
        assertDoesNotThrow(() -> pedidoValidator.validarPedido(pedido));
        
        verify(clienteService).buscarPorId(1L);
        verify(produtoService, never()).buscarPorId(anyLong());
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto do item é null")
    void deveLancarExcecaoQuandoProdutoDoItemENull() {
        // Arrange
        Pedido pedido = criarPedidoValido();
        pedido.getItens().get(0).setProduto(null);
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> pedidoValidator.validarPedido(pedido));
        
        assertEquals("Produto é obrigatório para cada item do pedido", exception.getMessage());
        
        verify(clienteService).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto ID do item é null")
    void deveLancarExcecaoQuandoProdutoIdDoItemENull() {
        // Arrange
        Pedido pedido = criarPedidoValido();
        pedido.getItens().get(0).getProduto().setId(null);
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> pedidoValidator.validarPedido(pedido));
        
        assertEquals("Produto é obrigatório para cada item do pedido", exception.getMessage());
        
        verify(clienteService).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto não existe")
    void deveLancarExcecaoQuandoProdutoNaoExiste() {
        // Arrange
        Pedido pedido = criarPedidoValido();
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.empty());
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> pedidoValidator.validarPedido(pedido));
        
        assertEquals("Produto não encontrado com ID: 1", exception.getMessage());
        
        verify(clienteService).buscarPorId(1L);
        verify(produtoService).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando quantidade é zero")
    void deveLancarExcecaoQuandoQuantidadeEZero() {
        // Arrange
        Pedido pedido = criarPedidoValido();
        pedido.getItens().get(0).setQuantidade(0);
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setQuantidadeEstoque(10);
        
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.of(produto));
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> pedidoValidator.validarPedido(pedido));
        
        assertEquals("Quantidade deve ser maior que zero para o produto: Produto Teste", exception.getMessage());
        
        verify(clienteService).buscarPorId(1L);
        verify(produtoService).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando quantidade é negativa")
    void deveLancarExcecaoQuandoQuantidadeENegativa() {
        // Arrange
        Pedido pedido = criarPedidoValido();
        pedido.getItens().get(0).setQuantidade(-1);
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setQuantidadeEstoque(10);
        
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.of(produto));
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> pedidoValidator.validarPedido(pedido));
        
        assertEquals("Quantidade deve ser maior que zero para o produto: Produto Teste", exception.getMessage());
        
        verify(clienteService).buscarPorId(1L);
        verify(produtoService).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando estoque é insuficiente")
    void deveLancarExcecaoQuandoEstoqueEInsuficiente() {
        // Arrange
        Pedido pedido = criarPedidoValido();
        pedido.getItens().get(0).setQuantidade(15);
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setQuantidadeEstoque(10);
        
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.of(produto));
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> pedidoValidator.validarPedido(pedido));
        
        assertEquals("Estoque insuficiente para o produto: Produto Teste", exception.getMessage());
        
        verify(clienteService).buscarPorId(1L);
        verify(produtoService).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve validar cliente separadamente")
    void deveValidarClienteSeparadamente() {
        // Arrange
        Pedido pedido = criarPedidoValido();
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        
        // Act & Assert
        assertDoesNotThrow(() -> pedidoValidator.validarCliente(pedido));
        
        verify(clienteService).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve validar itens separadamente")
    void deveValidarItensSeparadamente() {
        // Arrange
        Pedido pedido = criarPedidoValido();
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setQuantidadeEstoque(10);
        
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.of(produto));
        
        // Act & Assert
        assertDoesNotThrow(() -> pedidoValidator.validarItens(pedido));
        
        verify(produtoService).buscarPorId(1L);
    }

    private Pedido criarPedidoValido() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        pedido.setCliente(cliente);
        
        pedido.setDataPedido(LocalDateTime.now());
        
        List<ItemPedido> itens = new ArrayList<>();
        
        ItemPedido item = new ItemPedido();
        item.setId(1L);
        
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setQuantidadeEstoque(10);
        
        item.setProduto(produto);
        item.setQuantidade(2);
        item.setPedido(pedido);
        
        itens.add(item);
        pedido.setItens(itens);
        
        return pedido;
    }
} 