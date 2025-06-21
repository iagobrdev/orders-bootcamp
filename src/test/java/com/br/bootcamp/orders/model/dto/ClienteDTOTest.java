package com.br.bootcamp.orders.model.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes para ClienteDTO")
class ClienteDTOTest {

    @Test
    @DisplayName("Deve criar ClienteDTO com todos os campos")
    void deveCriarClienteDTOComTodosOsCampos() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("João da Silva");
        clienteDTO.setEmail("joao.silva@example.com");
        clienteDTO.setTelefone("(11) 99999-9999");
        clienteDTO.setEndereco("Rua das Flores, 123, São Paulo, SP");

        assertEquals("João da Silva", clienteDTO.getNome());
        assertEquals("joao.silva@example.com", clienteDTO.getEmail());
        assertEquals("(11) 99999-9999", clienteDTO.getTelefone());
        assertEquals("Rua das Flores, 123, São Paulo, SP", clienteDTO.getEndereco());
    }

    @Test
    @DisplayName("Deve criar ClienteDTO com construtor")
    void deveCriarClienteDTOComConstrutor() {
        ClienteDTO clienteDTO = new ClienteDTO(
                "Maria Santos",
                "maria.santos@example.com",
                "(11) 88888-8888",
                "Av. Paulista, 1000, São Paulo, SP"
        );

        assertEquals("Maria Santos", clienteDTO.getNome());
        assertEquals("maria.santos@example.com", clienteDTO.getEmail());
        assertEquals("(11) 88888-8888", clienteDTO.getTelefone());
        assertEquals("Av. Paulista, 1000, São Paulo, SP", clienteDTO.getEndereco());
    }

    @Test
    @DisplayName("Deve criar ClienteDTO vazio")
    void deveCriarClienteDTOVazio() {
        ClienteDTO clienteDTO = new ClienteDTO();

        assertNull(clienteDTO.getNome());
        assertNull(clienteDTO.getEmail());
        assertNull(clienteDTO.getTelefone());
        assertNull(clienteDTO.getEndereco());
    }

    @Test
    @DisplayName("Deve ser igual quando todos os campos são iguais")
    void deveSerIgualQuandoTodosOsCamposSaoIguais() {
        ClienteDTO cliente1 = new ClienteDTO("João", "joao@test.com", "123", "Rua A");
        ClienteDTO cliente2 = new ClienteDTO("João", "joao@test.com", "123", "Rua A");

        assertEquals(cliente1, cliente2);
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
    }

    @Test
    @DisplayName("Deve ser diferente quando campos são diferentes")
    void deveSerDiferenteQuandoCamposSaoDiferentes() {
        ClienteDTO cliente1 = new ClienteDTO("João", "joao@test.com", "123", "Rua A");
        ClienteDTO cliente2 = new ClienteDTO("Maria", "maria@test.com", "456", "Rua B");

        assertNotEquals(cliente1, cliente2);
        assertNotEquals(cliente1.hashCode(), cliente2.hashCode());
    }

    @Test
    @DisplayName("Deve retornar toString com todos os campos")
    void deveRetornarToStringComTodosOsCampos() {
        ClienteDTO clienteDTO = new ClienteDTO("João", "joao@test.com", "123", "Rua A");
        String toString = clienteDTO.toString();

        assertTrue(toString.contains("João"));
        assertTrue(toString.contains("joao@test.com"));
        assertTrue(toString.contains("123"));
        assertTrue(toString.contains("Rua A"));
    }

    @Test
    @DisplayName("Deve ser igual a si mesmo")
    void deveSerIgualASiMesmo() {
        ClienteDTO clienteDTO = new ClienteDTO("João", "joao@test.com", "123", "Rua A");
        assertEquals(clienteDTO, clienteDTO);
    }

    @Test
    @DisplayName("Deve ser diferente de null")
    void deveSerDiferenteDeNull() {
        ClienteDTO clienteDTO = new ClienteDTO("João", "joao@test.com", "123", "Rua A");
        assertNotEquals(null, clienteDTO);
    }

    @Test
    @DisplayName("Deve ser diferente de objeto de outro tipo")
    void deveSerDiferenteDeObjetoDeOutroTipo() {
        ClienteDTO clienteDTO = new ClienteDTO("João", "joao@test.com", "123", "Rua A");
        String string = "teste";
        assertNotEquals(clienteDTO, string);
    }
} 