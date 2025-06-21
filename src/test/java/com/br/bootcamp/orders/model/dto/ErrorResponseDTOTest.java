package com.br.bootcamp.orders.model.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes para ErrorResponseDTO")
class ErrorResponseDTOTest {

    @Test
    @DisplayName("Deve criar ErrorResponseDTO com todos os campos")
    void deveCriarErrorResponseDTOComTodosOsCampos() {
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorResponseDTO errorDTO = new ErrorResponseDTO();
        errorDTO.setTimestamp(timestamp);
        errorDTO.setStatus(404);
        errorDTO.setError("Not Found");
        errorDTO.setMessage("Cliente não encontrado com ID: 99");
        errorDTO.setPath("/api/clientes/99");

        assertEquals(timestamp, errorDTO.getTimestamp());
        assertEquals(404, errorDTO.getStatus());
        assertEquals("Not Found", errorDTO.getError());
        assertEquals("Cliente não encontrado com ID: 99", errorDTO.getMessage());
        assertEquals("/api/clientes/99", errorDTO.getPath());
    }

    @Test
    @DisplayName("Deve criar ErrorResponseDTO com construtor")
    void deveCriarErrorResponseDTOComConstrutor() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                timestamp,
                400,
                "Bad Request",
                "Dados inválidos fornecidos",
                "/api/clientes"
        );

        assertEquals(timestamp, errorDTO.getTimestamp());
        assertEquals(400, errorDTO.getStatus());
        assertEquals("Bad Request", errorDTO.getError());
        assertEquals("Dados inválidos fornecidos", errorDTO.getMessage());
        assertEquals("/api/clientes", errorDTO.getPath());
    }

    @Test
    @DisplayName("Deve criar ErrorResponseDTO vazio")
    void deveCriarErrorResponseDTOVazio() {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO();

        assertNull(errorDTO.getTimestamp());
        assertEquals(0, errorDTO.getStatus());
        assertNull(errorDTO.getError());
        assertNull(errorDTO.getMessage());
        assertNull(errorDTO.getPath());
    }

    @Test
    @DisplayName("Deve ser igual quando todos os campos são iguais")
    void deveSerIgualQuandoTodosOsCamposSaoIguais() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        ErrorResponseDTO error1 = new ErrorResponseDTO(timestamp, 404, "Not Found", "Erro", "/path");
        ErrorResponseDTO error2 = new ErrorResponseDTO(timestamp, 404, "Not Found", "Erro", "/path");

        assertEquals(error1, error2);
        assertEquals(error1.hashCode(), error2.hashCode());
    }

    @Test
    @DisplayName("Deve ser diferente quando campos são diferentes")
    void deveSerDiferenteQuandoCamposSaoDiferentes() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        ErrorResponseDTO error1 = new ErrorResponseDTO(timestamp, 404, "Not Found", "Erro 1", "/path1");
        ErrorResponseDTO error2 = new ErrorResponseDTO(timestamp, 500, "Internal Server Error", "Erro 2", "/path2");

        assertNotEquals(error1, error2);
        assertNotEquals(error1.hashCode(), error2.hashCode());
    }

    @Test
    @DisplayName("Deve retornar toString com todos os campos")
    void deveRetornarToStringComTodosOsCampos() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(timestamp, 404, "Not Found", "Erro", "/path");
        String toString = errorDTO.toString();

        assertTrue(toString.contains("404"));
        assertTrue(toString.contains("Not Found"));
        assertTrue(toString.contains("Erro"));
        assertTrue(toString.contains("/path"));
    }

    @Test
    @DisplayName("Deve ser igual a si mesmo")
    void deveSerIgualASiMesmo() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(timestamp, 404, "Not Found", "Erro", "/path");
        assertEquals(errorDTO, errorDTO);
    }

    @Test
    @DisplayName("Deve ser diferente de null")
    void deveSerDiferenteDeNull() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(timestamp, 404, "Not Found", "Erro", "/path");
        assertNotEquals(null, errorDTO);
    }

    @Test
    @DisplayName("Deve ser diferente de objeto de outro tipo")
    void deveSerDiferenteDeObjetoDeOutroTipo() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(timestamp, 404, "Not Found", "Erro", "/path");
        String string = "teste";
        assertNotEquals(errorDTO, string);
    }

    @Test
    @DisplayName("Deve lidar com status zero")
    void deveLidarComStatusZero() {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO();
        errorDTO.setStatus(0);

        assertEquals(0, errorDTO.getStatus());
    }

    @Test
    @DisplayName("Deve lidar com status negativo")
    void deveLidarComStatusNegativo() {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO();
        errorDTO.setStatus(-1);

        assertEquals(-1, errorDTO.getStatus());
    }

    @Test
    @DisplayName("Deve lidar com timestamp null")
    void deveLidarComTimestampNull() {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO();
        errorDTO.setTimestamp(null);

        assertNull(errorDTO.getTimestamp());
    }

    @Test
    @DisplayName("Deve lidar com mensagem vazia")
    void deveLidarComMensagemVazia() {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO();
        errorDTO.setMessage("");

        assertEquals("", errorDTO.getMessage());
    }

    @Test
    @DisplayName("Deve lidar com path vazio")
    void deveLidarComPathVazio() {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO();
        errorDTO.setPath("");

        assertEquals("", errorDTO.getPath());
    }
} 