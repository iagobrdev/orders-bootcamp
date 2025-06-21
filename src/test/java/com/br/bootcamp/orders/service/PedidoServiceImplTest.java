package com.br.bootcamp.orders.service;

import com.br.bootcamp.orders.model.Cliente;
import com.br.bootcamp.orders.model.Pedido;
import com.br.bootcamp.orders.model.Produto;
import com.br.bootcamp.orders.model.dto.PedidoDTO;
import com.br.bootcamp.orders.model.enums.StatusPedido;
import com.br.bootcamp.orders.model.enums.TipoPagamento;
import com.br.bootcamp.orders.repository.ClienteRepository;
import com.br.bootcamp.orders.repository.PedidoRepository;
import com.br.bootcamp.orders.repository.ProdutoRepository;
import com.br.bootcamp.orders.service.exception.BusinessException;
import com.br.bootcamp.orders.service.exception.ResourceNotFoundException;
import com.br.bootcamp.orders.service.util.PedidoCalculator;
import com.br.bootcamp.orders.service.util.PedidoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Testes para PedidoServiceImpl")
class PedidoServiceImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PedidoValidator pedidoValidator;

    @Mock
    private PedidoCalculator pedidoCalculator;

    @Mock
    private ModelMapper modelMapper;

    private PedidoServiceImpl pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedidoService = new PedidoServiceImpl(
                pedidoRepository, produtoRepository, clienteRepository,
                pedidoValidator, pedidoCalculator, modelMapper
        );
    }

    @Test
    @DisplayName("Deve listar todos os pedidos")
    void deveListarTodosOsPedidos() {
        // Arrange
        List<Pedido> pedidos = Arrays.asList(
                criarPedido(1L, StatusPedido.PENDENTE),
                criarPedido(2L, StatusPedido.APROVADO)
        );
        when(pedidoRepository.findAll()).thenReturn(pedidos);

        // Act
        List<Pedido> resultado = pedidoService.listarTodos();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals(StatusPedido.PENDENTE, resultado.get(0).getStatus());
        assertEquals(StatusPedido.APROVADO, resultado.get(1).getStatus());
        verify(pedidoRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar pedido por ID quando existe")
    void deveBuscarPedidoPorIdQuandoExiste() {
        // Arrange
        Pedido pedido = criarPedido(1L, StatusPedido.PENDENTE);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        // Act
        Optional<Pedido> resultado = pedidoService.buscarPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(StatusPedido.PENDENTE, resultado.get().getStatus());
        verify(pedidoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando pedido não existe por ID")
    void deveLancarExcecaoQuandoPedidoNaoExistePorId() {
        // Arrange
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> pedidoService.buscarPorId(1L));
        
        assertEquals("Pedido não encontrado com ID: 1", exception.getMessage());
        verify(pedidoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve buscar pedidos por cliente")
    void deveBuscarPedidosPorCliente() {
        // Arrange
        List<Pedido> pedidos = Arrays.asList(
                criarPedido(1L, StatusPedido.PENDENTE),
                criarPedido(2L, StatusPedido.APROVADO)
        );
        when(pedidoRepository.findByClienteId(1L)).thenReturn(pedidos);

        // Act
        List<Pedido> resultado = pedidoService.buscarPorCliente(1L);

        // Assert
        assertEquals(2, resultado.size());
        verify(pedidoRepository).findByClienteId(1L);
    }

    @Test
    @DisplayName("Deve buscar pedidos por status")
    void deveBuscarPedidosPorStatus() {
        // Arrange
        List<Pedido> pedidos = Arrays.asList(
                criarPedido(1L, StatusPedido.PENDENTE),
                criarPedido(2L, StatusPedido.PENDENTE)
        );
        when(pedidoRepository.findByStatus(StatusPedido.PENDENTE)).thenReturn(pedidos);

        // Act
        List<Pedido> resultado = pedidoService.buscarPorStatus(StatusPedido.PENDENTE);

        // Assert
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(p -> p.getStatus() == StatusPedido.PENDENTE));
        verify(pedidoRepository).findByStatus(StatusPedido.PENDENTE);
    }

    @Test
    @DisplayName("Deve buscar pedidos por data")
    void deveBuscarPedidosPorData() {
        // Arrange
        LocalDate data = LocalDate.of(2024, 1, 15);
        List<Pedido> pedidos = Arrays.asList(
                criarPedido(1L, StatusPedido.PENDENTE),
                criarPedido(2L, StatusPedido.APROVADO)
        );
        when(pedidoRepository.findByDataPedidoDate(data)).thenReturn(pedidos);

        // Act
        List<Pedido> resultado = pedidoService.buscarPorData(data);

        // Assert
        assertEquals(2, resultado.size());
        verify(pedidoRepository).findByDataPedidoDate(data);
    }

    @Test
    @DisplayName("Deve buscar pedidos por período")
    void deveBuscarPedidosPorPeriodo() {
        // Arrange
        LocalDate dataInicio = LocalDate.of(2024, 1, 1);
        LocalDate dataFim = LocalDate.of(2024, 1, 31);
        List<Pedido> pedidos = Arrays.asList(
                criarPedido(1L, StatusPedido.PENDENTE),
                criarPedido(2L, StatusPedido.APROVADO)
        );
        when(pedidoRepository.findByDataPedidoBetween(any(), any())).thenReturn(pedidos);

        // Act
        List<Pedido> resultado = pedidoService.buscarPorPeriodo(dataInicio, dataFim);

        // Assert
        assertEquals(2, resultado.size());
        verify(pedidoRepository).findByDataPedidoBetween(
                dataInicio.atStartOfDay(),
                dataFim.atTime(23, 59, 59)
        );
    }

    @Test
    @DisplayName("Deve salvar pedido com sucesso")
    void deveSalvarPedidoComSucesso() {
        // Arrange
        PedidoDTO pedidoDTO = criarPedidoDTO();
        Cliente cliente = criarCliente(1L);
        Produto produto = criarProduto(1L);
        Pedido pedido = criarPedido(1L, StatusPedido.PENDENTE);
        
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        doNothing().when(pedidoValidator).validarPedido(any(Pedido.class));
        doNothing().when(pedidoCalculator).prepararPedido(any(Pedido.class));

        // Act
        Pedido resultado = pedidoService.salvar(pedidoDTO);

        // Assert
        assertNotNull(resultado);
        verify(clienteRepository).findById(1L);
        verify(produtoRepository).findById(1L);
        verify(pedidoValidator).validarPedido(any(Pedido.class));
        verify(pedidoCalculator).prepararPedido(any(Pedido.class));
        verify(pedidoRepository).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar pedido com cliente inexistente")
    void deveLancarExcecaoAoSalvarPedidoComClienteInexistente() {
        // Arrange
        PedidoDTO pedidoDTO = criarPedidoDTO();
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> pedidoService.salvar(pedidoDTO));
        
        assertEquals("Cliente não encontrado com ID: 1", exception.getMessage());
        verify(clienteRepository).findById(1L);
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar pedido com produto inexistente")
    void deveLancarExcecaoAoSalvarPedidoComProdutoInexistente() {
        // Arrange
        PedidoDTO pedidoDTO = criarPedidoDTO();
        Cliente cliente = criarCliente(1L);
        
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> pedidoService.salvar(pedidoDTO));
        
        assertEquals("Produto não encontrado com ID: 1", exception.getMessage());
        verify(clienteRepository).findById(1L);
        verify(produtoRepository).findById(1L);
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar pedido com sucesso")
    void deveAtualizarPedidoComSucesso() {
        // Arrange
        PedidoDTO pedidoDTO = criarPedidoDTO();
        Pedido pedidoExistente = criarPedido(1L, StatusPedido.PENDENTE);
        Cliente cliente = criarCliente(1L);
        Produto produto = criarProduto(1L);
        
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoExistente));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoExistente);
        doNothing().when(pedidoValidator).validarPedido(any(Pedido.class));
        doNothing().when(pedidoCalculator).prepararPedido(any(Pedido.class));

        // Act
        Pedido resultado = pedidoService.atualizar(1L, pedidoDTO);

        // Assert
        assertNotNull(resultado);
        verify(pedidoRepository).findById(1L);
        verify(clienteRepository).findById(1L);
        verify(produtoRepository).findById(1L);
        verify(pedidoValidator).validarPedido(any(Pedido.class));
        verify(pedidoCalculator).prepararPedido(any(Pedido.class));
        verify(pedidoRepository).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar pedido inexistente")
    void deveLancarExcecaoAoAtualizarPedidoInexistente() {
        // Arrange
        PedidoDTO pedidoDTO = criarPedidoDTO();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> pedidoService.atualizar(1L, pedidoDTO));
        
        assertEquals("Pedido não encontrado com ID: 1", exception.getMessage());
        verify(pedidoRepository).findById(1L);
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar status do pedido")
    void deveAtualizarStatusDoPedido() {
        // Arrange
        Pedido pedido = criarPedido(1L, StatusPedido.PENDENTE);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        // Act
        Pedido resultado = pedidoService.atualizarStatus(1L, StatusPedido.APROVADO);

        // Assert
        assertNotNull(resultado);
        assertEquals(StatusPedido.APROVADO, resultado.getStatus());
        verify(pedidoRepository).findById(1L);
        verify(pedidoRepository).save(pedido);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar status de pedido inexistente")
    void deveLancarExcecaoAoAtualizarStatusDePedidoInexistente() {
        // Arrange
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> pedidoService.atualizarStatus(1L, StatusPedido.APROVADO));
        
        assertEquals("Pedido não encontrado com ID: 1", exception.getMessage());
        verify(pedidoRepository).findById(1L);
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar pedido com sucesso")
    void deveDeletarPedidoComSucesso() {
        // Arrange
        when(pedidoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pedidoRepository).deleteById(1L);

        // Act
        assertDoesNotThrow(() -> pedidoService.deletar(1L));

        // Assert
        verify(pedidoRepository).existsById(1L);
        verify(pedidoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar pedido inexistente")
    void deveLancarExcecaoAoDeletarPedidoInexistente() {
        // Arrange
        when(pedidoRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> pedidoService.deletar(1L));
        
        assertEquals("Pedido não encontrado com ID: 1", exception.getMessage());
        verify(pedidoRepository).existsById(1L);
        verify(pedidoRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve contar pedidos")
    void deveContarPedidos() {
        // Arrange
        when(pedidoRepository.count()).thenReturn(5L);

        // Act
        long resultado = pedidoService.contarPedidos();

        // Assert
        assertEquals(5L, resultado);
        verify(pedidoRepository).count();
    }

    @Test
    @DisplayName("Deve calcular valor total do pedido")
    void deveCalcularValorTotalDoPedido() {
        // Arrange
        Pedido pedido = criarPedido(1L, StatusPedido.PENDENTE);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoCalculator.calcularValorTotal(pedido)).thenReturn(new BigDecimal("150.00"));

        // Act
        Double resultado = pedidoService.calcularValorTotal(1L);

        // Assert
        assertEquals(150.0, resultado);
        verify(pedidoRepository).findById(1L);
        verify(pedidoCalculator).calcularValorTotal(pedido);
    }

    @Test
    @DisplayName("Deve lançar exceção ao calcular valor total de pedido inexistente")
    void deveLancarExcecaoAoCalcularValorTotalDePedidoInexistente() {
        // Arrange
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> pedidoService.calcularValorTotal(1L));
        
        assertEquals("Pedido não encontrado com ID: 1", exception.getMessage());
        verify(pedidoRepository).findById(1L);
        verify(pedidoCalculator, never()).calcularValorTotal(any());
    }

    private PedidoDTO criarPedidoDTO() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setClienteId(1L);
        pedidoDTO.setStatus(StatusPedido.PENDENTE);
        pedidoDTO.setTipoPagamento(TipoPagamento.PIX);
        
        List<PedidoDTO.ItemPedidoDTO> itens = new ArrayList<>();
        PedidoDTO.ItemPedidoDTO item = new PedidoDTO.ItemPedidoDTO(1L, 2);
        itens.add(item);
        pedidoDTO.setItens(itens);
        
        return pedidoDTO;
    }

    private Pedido criarPedido(Long id, StatusPedido status) {
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setCliente(criarCliente(1L));
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(status);
        pedido.setTipoPagamento(TipoPagamento.PIX);
        pedido.setValorTotal(new BigDecimal("150.00"));
        pedido.setItens(new ArrayList<>());
        return pedido;
    }

    private Cliente criarCliente(Long id) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome("João");
        cliente.setEmail("joao@test.com");
        return cliente;
    }

    private Produto criarProduto(Long id) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome("Notebook");
        produto.setPreco(new BigDecimal("75.00"));
        produto.setQuantidadeEstoque(10);
        return produto;
    }
} 