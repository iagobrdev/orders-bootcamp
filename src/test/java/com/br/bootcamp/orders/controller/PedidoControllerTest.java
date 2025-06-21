package com.br.bootcamp.orders.controller;

import com.br.bootcamp.orders.model.Cliente;
import com.br.bootcamp.orders.model.Pedido;
import com.br.bootcamp.orders.model.Produto;
import com.br.bootcamp.orders.model.dto.PedidoDTO;
import com.br.bootcamp.orders.model.enums.CategoriaProduto;
import com.br.bootcamp.orders.model.enums.StatusPedido;
import com.br.bootcamp.orders.model.enums.TipoPagamento;
import com.br.bootcamp.orders.service.contracts.IPedidoService;
import com.br.bootcamp.orders.service.exception.BusinessException;
import com.br.bootcamp.orders.service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Testes para PedidoController")
class PedidoControllerTest {

    @Mock
    private IPedidoService pedidoService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private Pedido pedido;
    private PedidoDTO pedidoDTO;
    private Cliente cliente;
    private Produto produto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new PedidoController(pedidoService)).build();
        objectMapper = new ObjectMapper();

        // Setup dados de teste
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@example.com");

        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Notebook");
        produto.setPreco(new BigDecimal("1500.00"));
        produto.setCategoria(CategoriaProduto.ELETRONICOS);

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setTipoPagamento(TipoPagamento.PIX);
        pedido.setValorTotal(new BigDecimal("1500.00"));
        pedido.setItens(new ArrayList<>());

        pedidoDTO = new PedidoDTO();
        pedidoDTO.setClienteId(1L);
        pedidoDTO.setStatus(StatusPedido.PENDENTE);
        pedidoDTO.setTipoPagamento(TipoPagamento.PIX);
        pedidoDTO.setItens(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve listar todos os pedidos com sucesso")
    void deveListarTodosOsPedidosComSucesso() throws Exception {
        // Arrange
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoService.listarTodos()).thenReturn(pedidos);

        // Act & Assert
        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].cliente.id").value(1))
                .andExpect(jsonPath("$[0].status").value("PENDENTE"));

        verify(pedidoService).listarTodos();
    }

    @Test
    @DisplayName("Deve buscar pedido por ID com sucesso")
    void deveBuscarPedidoPorIdComSucesso() throws Exception {
        // Arrange
        when(pedidoService.buscarPorId(1L)).thenReturn(Optional.of(pedido));

        // Act & Assert
        mockMvc.perform(get("/api/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cliente.id").value(1))
                .andExpect(jsonPath("$.status").value("PENDENTE"));

        verify(pedidoService).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 quando pedido não encontrado por ID")
    void deveRetornar404QuandoPedidoNaoEncontradoPorId() throws Exception {
        // Arrange
        when(pedidoService.buscarPorId(999L)).thenThrow(new ResourceNotFoundException("Pedido não encontrado"));

        // Act & Assert
        mockMvc.perform(get("/api/pedidos/999"))
                .andExpect(status().isNotFound());

        verify(pedidoService).buscarPorId(999L);
    }

    @Test
    @DisplayName("Deve buscar pedidos por cliente com sucesso")
    void deveBuscarPedidosPorClienteComSucesso() throws Exception {
        // Arrange
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoService.buscarPorCliente(1L)).thenReturn(pedidos);

        // Act & Assert
        mockMvc.perform(get("/api/pedidos/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].cliente.id").value(1));

        verify(pedidoService).buscarPorCliente(1L);
    }

    @Test
    @DisplayName("Deve buscar pedidos por status com sucesso")
    void deveBuscarPedidosPorStatusComSucesso() throws Exception {
        // Arrange
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoService.buscarPorStatus(StatusPedido.PENDENTE)).thenReturn(pedidos);

        // Act & Assert
        mockMvc.perform(get("/api/pedidos/status")
                .param("status", "PENDENTE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].status").value("PENDENTE"));

        verify(pedidoService).buscarPorStatus(StatusPedido.PENDENTE);
    }

    @Test
    @DisplayName("Deve buscar pedidos por data com sucesso")
    void deveBuscarPedidosPorDataComSucesso() throws Exception {
        // Arrange
        LocalDate data = LocalDate.of(2024, 1, 15);
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoService.buscarPorData(data)).thenReturn(pedidos);

        // Act & Assert
        mockMvc.perform(get("/api/pedidos/data")
                .param("data", "15/01/2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(pedidoService).buscarPorData(data);
    }

    @Test
    @DisplayName("Deve buscar pedidos por período com sucesso")
    void deveBuscarPedidosPorPeriodoComSucesso() throws Exception {
        // Arrange
        LocalDate dataInicio = LocalDate.of(2024, 1, 1);
        LocalDate dataFim = LocalDate.of(2024, 1, 31);
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoService.buscarPorPeriodo(dataInicio, dataFim)).thenReturn(pedidos);

        // Act & Assert
        mockMvc.perform(get("/api/pedidos/periodo")
                .param("dataInicio", "01/01/2024")
                .param("dataFim", "31/01/2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(pedidoService).buscarPorPeriodo(dataInicio, dataFim);
    }

    @Test
    @DisplayName("Deve contar total de pedidos com sucesso")
    void deveContarTotalDePedidosComSucesso() throws Exception {
        // Arrange
        when(pedidoService.contarPedidos()).thenReturn(5L);

        // Act & Assert
        mockMvc.perform(get("/api/pedidos/contar"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

        verify(pedidoService).contarPedidos();
    }

    @Test
    @DisplayName("Deve calcular valor total do pedido com sucesso")
    void deveCalcularValorTotalDoPedidoComSucesso() throws Exception {
        // Arrange
        when(pedidoService.calcularValorTotal(1L)).thenReturn(1500.0);

        // Act & Assert
        mockMvc.perform(get("/api/pedidos/1/valor-total"))
                .andExpect(status().isOk())
                .andExpect(content().string("1500.0"));

        verify(pedidoService).calcularValorTotal(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 quando pedido não encontrado para cálculo de valor total")
    void deveRetornar404QuandoPedidoNaoEncontradoParaCalculoValorTotal() throws Exception {
        // Arrange
        when(pedidoService.calcularValorTotal(999L)).thenThrow(new ResourceNotFoundException("Pedido não encontrado"));

        // Act & Assert
        mockMvc.perform(get("/api/pedidos/999/valor-total"))
                .andExpect(status().isNotFound());

        verify(pedidoService).calcularValorTotal(999L);
    }

    @Test
    @DisplayName("Deve criar novo pedido com sucesso")
    void deveCriarNovoPedidoComSucesso() throws Exception {
        // Arrange
        when(pedidoService.salvar(pedidoDTO)).thenReturn(pedido);

        // Act & Assert
        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cliente.id").value(1))
                .andExpect(jsonPath("$.status").value("PENDENTE"));

        verify(pedidoService).salvar(pedidoDTO);
    }

    @Test
    @DisplayName("Deve retornar 400 quando dados inválidos para criar pedido")
    void deveRetornar400QuandoDadosInvalidosParaCriarPedido() throws Exception {
        // Arrange
        when(pedidoService.salvar(pedidoDTO)).thenThrow(new BusinessException("Cliente não encontrado"));

        // Act & Assert
        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andExpect(status().isBadRequest());

        verify(pedidoService).salvar(pedidoDTO);
    }

    @Test
    @DisplayName("Deve atualizar pedido com sucesso")
    void deveAtualizarPedidoComSucesso() throws Exception {
        // Arrange
        when(pedidoService.atualizar(1L, pedidoDTO)).thenReturn(pedido);

        // Act & Assert
        mockMvc.perform(put("/api/pedidos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cliente.id").value(1));

        verify(pedidoService).atualizar(1L, pedidoDTO);
    }

    @Test
    @DisplayName("Deve retornar 404 quando pedido não encontrado para atualização")
    void deveRetornar404QuandoPedidoNaoEncontradoParaAtualizacao() throws Exception {
        // Arrange
        when(pedidoService.atualizar(999L, pedidoDTO)).thenThrow(new ResourceNotFoundException("Pedido não encontrado"));

        // Act & Assert
        mockMvc.perform(put("/api/pedidos/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andExpect(status().isNotFound());

        verify(pedidoService).atualizar(999L, pedidoDTO);
    }

    @Test
    @DisplayName("Deve atualizar status do pedido com sucesso")
    void deveAtualizarStatusDoPedidoComSucesso() throws Exception {
        // Arrange
        Pedido pedidoAtualizado = new Pedido();
        pedidoAtualizado.setId(1L);
        pedidoAtualizado.setStatus(StatusPedido.APROVADO);
        when(pedidoService.atualizarStatus(1L, StatusPedido.APROVADO)).thenReturn(pedidoAtualizado);

        // Act & Assert
        mockMvc.perform(put("/api/pedidos/1/status")
                .param("novoStatus", "APROVADO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("APROVADO"));

        verify(pedidoService).atualizarStatus(1L, StatusPedido.APROVADO);
    }

    @Test
    @DisplayName("Deve retornar 404 quando pedido não encontrado para atualização de status")
    void deveRetornar404QuandoPedidoNaoEncontradoParaAtualizacaoDeStatus() throws Exception {
        // Arrange
        when(pedidoService.atualizarStatus(999L, StatusPedido.APROVADO))
                .thenThrow(new ResourceNotFoundException("Pedido não encontrado"));

        // Act & Assert
        mockMvc.perform(put("/api/pedidos/999/status")
                .param("novoStatus", "APROVADO"))
                .andExpect(status().isNotFound());

        verify(pedidoService).atualizarStatus(999L, StatusPedido.APROVADO);
    }

    @Test
    @DisplayName("Deve deletar pedido com sucesso")
    void deveDeletarPedidoComSucesso() throws Exception {
        // Arrange
        doNothing().when(pedidoService).deletar(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/pedidos/1"))
                .andExpect(status().isNoContent());

        verify(pedidoService).deletar(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 quando pedido não encontrado para deleção")
    void deveRetornar404QuandoPedidoNaoEncontradoParaDelecao() throws Exception {
        // Arrange
        doThrow(new ResourceNotFoundException("Pedido não encontrado")).when(pedidoService).deletar(999L);

        // Act & Assert
        mockMvc.perform(delete("/api/pedidos/999"))
                .andExpect(status().isNotFound());

        verify(pedidoService).deletar(999L);
    }
} 