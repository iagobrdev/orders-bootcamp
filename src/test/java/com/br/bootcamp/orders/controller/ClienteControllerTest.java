package com.br.bootcamp.orders.controller;

import com.br.bootcamp.orders.model.Cliente;
import com.br.bootcamp.orders.model.dto.ClienteDTO;
import com.br.bootcamp.orders.service.contracts.IClienteService;
import com.br.bootcamp.orders.service.exception.BusinessException;
import com.br.bootcamp.orders.service.exception.GlobalExceptionHandler;
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

import java.util.Arrays;
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

@DisplayName("Testes para ClienteController")
class ClienteControllerTest {

    @Mock
    private IClienteService clienteService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new ClienteController(clienteService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Deve listar todos os clientes com sucesso")
    void deveListarTodosOsClientesComSucesso() throws Exception {
        // Arrange
        List<Cliente> clientes = Arrays.asList(
                criarCliente(1L, "João", "joao@test.com"),
                criarCliente(2L, "Maria", "maria@test.com")
        );
        when(clienteService.listarTodos()).thenReturn(clientes);

        // Act & Assert
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("João"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Maria"));

        verify(clienteService).listarTodos();
    }

    @Test
    @DisplayName("Deve buscar cliente por ID com sucesso")
    void deveBuscarClientePorIdComSucesso() throws Exception {
        // Arrange
        Cliente cliente = criarCliente(1L, "João", "joao@test.com");
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        // Act & Assert
        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.email").value("joao@test.com"));

        verify(clienteService).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 quando cliente não existe por ID")
    void deveRetornar404QuandoClienteNaoExistePorId() throws Exception {
        // Arrange
        when(clienteService.buscarPorId(1L)).thenThrow(new ResourceNotFoundException("Cliente não encontrado com ID: 1"));

        // Act & Assert
        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Cliente não encontrado com ID: 1"));

        verify(clienteService).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve buscar clientes por nome com sucesso")
    void deveBuscarClientesPorNomeComSucesso() throws Exception {
        // Arrange
        List<Cliente> clientes = Arrays.asList(
                criarCliente(1L, "João Silva", "joao@test.com"),
                criarCliente(2L, "João Santos", "joao2@test.com")
        );
        when(clienteService.buscarPorNome("João")).thenReturn(clientes);

        // Act & Assert
        mockMvc.perform(get("/api/clientes/nome/João"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nome").value("João Silva"))
                .andExpect(jsonPath("$[1].nome").value("João Santos"));

        verify(clienteService).buscarPorNome("João");
    }

    @Test
    @DisplayName("Deve buscar cliente por email com sucesso")
    void deveBuscarClientePorEmailComSucesso() throws Exception {
        // Arrange
        Cliente cliente = criarCliente(1L, "João", "joao@test.com");
        when(clienteService.buscarPorEmail("joao@test.com")).thenReturn(cliente);

        // Act & Assert
        mockMvc.perform(get("/api/clientes/email/joao@test.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.email").value("joao@test.com"));

        verify(clienteService).buscarPorEmail("joao@test.com");
    }

    @Test
    @DisplayName("Deve retornar 404 quando cliente não existe por email")
    void deveRetornar404QuandoClienteNaoExistePorEmail() throws Exception {
        // Arrange
        when(clienteService.buscarPorEmail("joao@test.com")).thenThrow(new ResourceNotFoundException("Cliente não encontrado com o email: joao@test.com"));

        // Act & Assert
        mockMvc.perform(get("/api/clientes/email/joao@test.com"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Cliente não encontrado com o email: joao@test.com"));

        verify(clienteService).buscarPorEmail("joao@test.com");
    }

    @Test
    @DisplayName("Deve contar clientes com sucesso")
    void deveContarClientesComSucesso() throws Exception {
        // Arrange
        when(clienteService.contarClientes()).thenReturn(5L);

        // Act & Assert
        mockMvc.perform(get("/api/clientes/contar"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

        verify(clienteService).contarClientes();
    }

    @Test
    @DisplayName("Deve criar cliente com sucesso")
    void deveCriarClienteComSucesso() throws Exception {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO("João", "joao@test.com", "123", "Rua A");
        Cliente cliente = criarCliente(1L, "João", "joao@test.com");
        when(clienteService.salvar(clienteDTO)).thenReturn(cliente);

        // Act & Assert
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.email").value("joao@test.com"));

        verify(clienteService).salvar(clienteDTO);
    }

    @Test
    @DisplayName("Deve retornar 400 ao criar cliente com email duplicado")
    void deveRetornar400AoCriarClienteComEmailDuplicado() throws Exception {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO("João", "joao@test.com", "123", "Rua A");
        when(clienteService.salvar(clienteDTO)).thenThrow(new BusinessException("Já existe um cliente cadastrado com este email: joao@test.com"));

        // Act & Assert
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Já existe um cliente cadastrado com este email: joao@test.com"));

        verify(clienteService).salvar(clienteDTO);
    }

    @Test
    @DisplayName("Deve atualizar cliente com sucesso")
    void deveAtualizarClienteComSucesso() throws Exception {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO("João Atualizado", "joao@test.com", "123", "Rua B");
        Cliente cliente = criarCliente(1L, "João Atualizado", "joao@test.com");
        when(clienteService.atualizar(1L, clienteDTO)).thenReturn(cliente);

        // Act & Assert
        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Atualizado"))
                .andExpect(jsonPath("$.email").value("joao@test.com"));

        verify(clienteService).atualizar(1L, clienteDTO);
    }

    @Test
    @DisplayName("Deve retornar 404 ao atualizar cliente inexistente")
    void deveRetornar404AoAtualizarClienteInexistente() throws Exception {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO("João", "joao@test.com", "123", "Rua A");
        when(clienteService.atualizar(1L, clienteDTO)).thenThrow(new ResourceNotFoundException("Cliente não encontrado com ID: 1"));

        // Act & Assert
        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Cliente não encontrado com ID: 1"));

        verify(clienteService).atualizar(1L, clienteDTO);
    }

    @Test
    @DisplayName("Deve deletar cliente com sucesso")
    void deveDeletarClienteComSucesso() throws Exception {
        // Arrange
        doNothing().when(clienteService).deletar(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());

        verify(clienteService).deletar(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 ao deletar cliente inexistente")
    void deveRetornar404AoDeletarClienteInexistente() throws Exception {
        // Arrange
        doThrow(new ResourceNotFoundException("Cliente não encontrado com ID: 1")).when(clienteService).deletar(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Cliente não encontrado com ID: 1"));

        verify(clienteService).deletar(1L);
    }

    @Test
    @DisplayName("Deve retornar 400 ao criar cliente com dados inválidos")
    void deveRetornar400AoCriarClienteComDadosInvalidos() throws Exception {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO("", "email-invalido", "", "");
        when(clienteService.salvar(clienteDTO)).thenThrow(new BusinessException("Dados do cliente são inválidos"));

        // Act & Assert
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Dados do cliente são inválidos"));

        verify(clienteService).salvar(clienteDTO);
    }

    private Cliente criarCliente(Long id, String nome, String email) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setTelefone("123456789");
        cliente.setEndereco("Rua Teste, 123");
        return cliente;
    }
} 