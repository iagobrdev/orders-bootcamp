package com.br.bootcamp.orders.service.exception;

import com.br.bootcamp.orders.model.dto.ErrorResponseDTO;
import com.br.bootcamp.orders.model.enums.StatusPedido;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Testes para GlobalExceptionHandler")
class GlobalExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new GlobalExceptionHandler();
        
        when(request.getRequestURI()).thenReturn("/api/test");
    }

    @Test
    @DisplayName("Deve tratar ResourceNotFoundException")
    void deveTratarResourceNotFoundException() {
        // Arrange
        ResourceNotFoundException exception = new ResourceNotFoundException("Recurso não encontrado");
        
        // Act
        ResponseEntity<ErrorResponseDTO> response = handler.handleResourceNotFoundException(exception, request);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Not Found", response.getBody().getError());
        assertEquals("Recurso não encontrado", response.getBody().getMessage());
        assertEquals("/api/test", response.getBody().getPath());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    @DisplayName("Deve tratar BusinessException")
    void deveTratarBusinessException() {
        // Arrange
        BusinessException exception = new BusinessException("Erro de negócio");
        
        // Act
        ResponseEntity<ErrorResponseDTO> response = handler.handleBusinessException(exception, request);
        
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Bad Request", response.getBody().getError());
        assertEquals("Erro de negócio", response.getBody().getMessage());
        assertEquals("/api/test", response.getBody().getPath());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    @DisplayName("Deve tratar MethodArgumentTypeMismatchException com StatusPedido")
    void deveTratarMethodArgumentTypeMismatchExceptionComStatusPedido() {
        // Arrange
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn("status");
        when(exception.getValue()).thenReturn("INVALIDO");
        when(exception.getRequiredType()).thenReturn((Class) StatusPedido.class);
        
        // Act
        ResponseEntity<ErrorResponseDTO> response = handler.handleMethodArgumentTypeMismatchException(exception, request);
        
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Bad Request", response.getBody().getError());
        assertTrue(response.getBody().getMessage().contains("Status inválido"));
        assertTrue(response.getBody().getMessage().contains("INVALIDO"));
        assertEquals("/api/test", response.getBody().getPath());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    @DisplayName("Deve tratar MethodArgumentTypeMismatchException genérico")
    void deveTratarMethodArgumentTypeMismatchExceptionGenerico() {
        // Arrange
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn("id");
        when(exception.getValue()).thenReturn("abc");
        when(exception.getRequiredType()).thenReturn((Class) Long.class);
        
        // Act
        ResponseEntity<ErrorResponseDTO> response = handler.handleMethodArgumentTypeMismatchException(exception, request);
        
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Bad Request", response.getBody().getError());
        assertTrue(response.getBody().getMessage().contains("Parâmetro inválido"));
        assertTrue(response.getBody().getMessage().contains("id"));
        assertTrue(response.getBody().getMessage().contains("abc"));
        assertEquals("/api/test", response.getBody().getPath());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    @DisplayName("Deve tratar MissingServletRequestParameterException")
    void deveTratarMissingServletRequestParameterException() {
        // Arrange
        MissingServletRequestParameterException exception = mock(MissingServletRequestParameterException.class);
        when(exception.getParameterName()).thenReturn("id");
        
        // Act
        ResponseEntity<ErrorResponseDTO> response = handler.handleMissingServletRequestParameterException(exception, request);
        
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Bad Request", response.getBody().getError());
        assertTrue(response.getBody().getMessage().contains("Parâmetro obrigatório não fornecido"));
        assertTrue(response.getBody().getMessage().contains("id"));
        assertEquals("/api/test", response.getBody().getPath());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    @DisplayName("Deve tratar MethodArgumentNotValidException")
    void deveTratarMethodArgumentNotValidException() {
        // Arrange
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        org.springframework.validation.FieldError fieldError = mock(org.springframework.validation.FieldError.class);
        when(fieldError.getDefaultMessage()).thenReturn("Campo obrigatório");
        org.springframework.validation.BindingResult bindingResult = mock(org.springframework.validation.BindingResult.class);
        when(bindingResult.getFieldError()).thenReturn(fieldError);
        when(exception.getBindingResult()).thenReturn(bindingResult);
        
        // Act
        ResponseEntity<ErrorResponseDTO> response = handler.handleMethodArgumentNotValidException(exception, request);
        
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Bad Request", response.getBody().getError());
        assertTrue(response.getBody().getMessage().contains("Dados de entrada inválidos"));
        assertTrue(response.getBody().getMessage().contains("Campo obrigatório"));
        assertEquals("/api/test", response.getBody().getPath());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    @DisplayName("Deve tratar Exception genérica")
    void deveTratarExceptionGenerica() {
        // Arrange
        Exception exception = new Exception("Erro inesperado");
        
        // Act
        ResponseEntity<ErrorResponseDTO> response = handler.handleGenericException(exception, request);
        
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
        assertEquals("Internal Server Error", response.getBody().getError());
        assertEquals("Ocorreu um erro inesperado no servidor.", response.getBody().getMessage());
        assertEquals("/api/test", response.getBody().getPath());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    @DisplayName("Deve tratar exceções com mensagens vazias")
    void deveTratarExcecoesComMensagensVazias() {
        // Arrange
        ResourceNotFoundException exception = new ResourceNotFoundException("");
        
        // Act
        ResponseEntity<ErrorResponseDTO> response = handler.handleResourceNotFoundException(exception, request);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("", response.getBody().getMessage());
    }

    @Test
    @DisplayName("Deve tratar exceções com mensagens null")
    void deveTratarExcecoesComMensagensNull() {
        // Arrange
        ResourceNotFoundException exception = new ResourceNotFoundException(null);
        
        // Act
        ResponseEntity<ErrorResponseDTO> response = handler.handleResourceNotFoundException(exception, request);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNull(response.getBody().getMessage());
    }

    @Test
    @DisplayName("Deve tratar MethodArgumentTypeMismatchException com requiredType null")
    void deveTratarMethodArgumentTypeMismatchExceptionComRequiredTypeNull() {
        // Arrange
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn("param");
        when(exception.getValue()).thenReturn("value");
        when(exception.getRequiredType()).thenReturn(null);
        
        // Act
        ResponseEntity<ErrorResponseDTO> response = handler.handleMethodArgumentTypeMismatchException(exception, request);
        
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Parâmetro inválido"));
    }
} 