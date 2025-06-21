package com.br.bootcamp.orders.service;

import com.br.bootcamp.orders.model.Cliente;
import com.br.bootcamp.orders.model.dto.ClienteDTO;
import com.br.bootcamp.orders.repository.ClienteRepository;
import com.br.bootcamp.orders.service.exception.BusinessException;
import com.br.bootcamp.orders.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

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

@DisplayName("Testes para ClienteServiceImpl")
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ModelMapper modelMapper;

    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteService = new ClienteServiceImpl(clienteRepository, modelMapper);
    }

    @Test
    @DisplayName("Deve listar todos os clientes")
    void deveListarTodosOsClientes() {
        // Arrange
        List<Cliente> clientes = Arrays.asList(
                criarCliente(1L, "João", "joao@test.com"),
                criarCliente(2L, "Maria", "maria@test.com")
        );
        when(clienteRepository.findAll()).thenReturn(clientes);

        // Act
        List<Cliente> resultado = clienteService.listarTodos();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("João", resultado.get(0).getNome());
        assertEquals("Maria", resultado.get(1).getNome());
        verify(clienteRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar cliente por ID quando existe")
    void deveBuscarClientePorIdQuandoExiste() {
        // Arrange
        Cliente cliente = criarCliente(1L, "João", "joao@test.com");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // Act
        Optional<Cliente> resultado = clienteService.buscarPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("João", resultado.get().getNome());
        verify(clienteRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não existe por ID")
    void deveLancarExcecaoQuandoClienteNaoExistePorId() {
        // Arrange
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> clienteService.buscarPorId(1L));
        
        assertEquals("Cliente não encontrado com ID: 1", exception.getMessage());
        verify(clienteRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve buscar clientes por nome")
    void deveBuscarClientesPorNome() {
        // Arrange
        List<Cliente> clientes = Arrays.asList(
                criarCliente(1L, "João Silva", "joao@test.com"),
                criarCliente(2L, "João Santos", "joao2@test.com")
        );
        when(clienteRepository.findByNomeContainingIgnoreCase("João")).thenReturn(clientes);

        // Act
        List<Cliente> resultado = clienteService.buscarPorNome("João");

        // Assert
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(c -> c.getNome().contains("João")));
        verify(clienteRepository).findByNomeContainingIgnoreCase("João");
    }

    @Test
    @DisplayName("Deve buscar cliente por email quando existe")
    void deveBuscarClientePorEmailQuandoExiste() {
        // Arrange
        Cliente cliente = criarCliente(1L, "João", "joao@test.com");
        when(clienteRepository.findByEmail("joao@test.com")).thenReturn(cliente);

        // Act
        Cliente resultado = clienteService.buscarPorEmail("joao@test.com");

        // Assert
        assertNotNull(resultado);
        assertEquals("joao@test.com", resultado.getEmail());
        verify(clienteRepository).findByEmail("joao@test.com");
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não existe por email")
    void deveLancarExcecaoQuandoClienteNaoExistePorEmail() {
        // Arrange
        when(clienteRepository.findByEmail("joao@test.com")).thenReturn(null);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> clienteService.buscarPorEmail("joao@test.com"));
        
        assertEquals("Cliente não encontrado com o email: joao@test.com", exception.getMessage());
        verify(clienteRepository).findByEmail("joao@test.com");
    }

    @Test
    @DisplayName("Deve salvar cliente com sucesso")
    void deveSalvarClienteComSucesso() {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO("João", "joao@test.com", "123", "Rua A");
        Cliente cliente = criarCliente(1L, "João", "joao@test.com");
        
        when(clienteRepository.existsByEmail("joao@test.com")).thenReturn(false);
        when(modelMapper.map(clienteDTO, Cliente.class)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Act
        Cliente resultado = clienteService.salvar(clienteDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
        verify(clienteRepository).existsByEmail("joao@test.com");
        verify(modelMapper).map(clienteDTO, Cliente.class);
        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar cliente com email duplicado")
    void deveLancarExcecaoAoSalvarClienteComEmailDuplicado() {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO("João", "joao@test.com", "123", "Rua A");
        when(clienteRepository.existsByEmail("joao@test.com")).thenReturn(true);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> clienteService.salvar(clienteDTO));
        
        assertEquals("Já existe um cliente cadastrado com este email: joao@test.com", exception.getMessage());
        verify(clienteRepository).existsByEmail("joao@test.com");
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar cliente com sucesso")
    void deveAtualizarClienteComSucesso() {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO("João Atualizado", "joao@test.com", "123", "Rua B");
        Cliente cliente = criarCliente(1L, "João Atualizado", "joao@test.com");
        
        when(clienteRepository.existsById(1L)).thenReturn(true);
        when(modelMapper.map(clienteDTO, Cliente.class)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Act
        Cliente resultado = clienteService.atualizar(1L, clienteDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Atualizado", resultado.getNome());
        assertEquals(1L, resultado.getId());
        verify(clienteRepository).existsById(1L);
        verify(modelMapper).map(clienteDTO, Cliente.class);
        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar cliente inexistente")
    void deveLancarExcecaoAoAtualizarClienteInexistente() {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO("João", "joao@test.com", "123", "Rua A");
        when(clienteRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> clienteService.atualizar(1L, clienteDTO));
        
        assertEquals("Cliente não encontrado com ID: 1", exception.getMessage());
        verify(clienteRepository).existsById(1L);
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar cliente com sucesso")
    void deveDeletarClienteComSucesso() {
        // Arrange
        when(clienteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(1L);

        // Act
        assertDoesNotThrow(() -> clienteService.deletar(1L));

        // Assert
        verify(clienteRepository).existsById(1L);
        verify(clienteRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar cliente inexistente")
    void deveLancarExcecaoAoDeletarClienteInexistente() {
        // Arrange
        when(clienteRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> clienteService.deletar(1L));
        
        assertEquals("Cliente não encontrado com ID: 1", exception.getMessage());
        verify(clienteRepository).existsById(1L);
        verify(clienteRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve contar clientes")
    void deveContarClientes() {
        // Arrange
        when(clienteRepository.count()).thenReturn(5L);

        // Act
        long resultado = clienteService.contarClientes();

        // Assert
        assertEquals(5L, resultado);
        verify(clienteRepository).count();
    }

    private Cliente criarCliente(Long id, String nome, String email) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setTelefone("123");
        cliente.setEndereco("Rua A");
        return cliente;
    }
} 