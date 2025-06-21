package com.br.bootcamp.orders.service.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes para ResourceNotFoundException")
class ResourceNotFoundExceptionTest {

    @Test
    @DisplayName("Deve criar ResourceNotFoundException com mensagem")
    void deveCriarResourceNotFoundExceptionComMensagem() {
        String mensagem = "Recurso não encontrado";
        ResourceNotFoundException exception = new ResourceNotFoundException(mensagem);

        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar ResourceNotFoundException com mensagem vazia")
    void deveCriarResourceNotFoundExceptionComMensagemVazia() {
        ResourceNotFoundException exception = new ResourceNotFoundException("");

        assertEquals("", exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar ResourceNotFoundException com mensagem null")
    void deveCriarResourceNotFoundExceptionComMensagemNull() {
        ResourceNotFoundException exception = new ResourceNotFoundException(null);

        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Deve ser instância de RuntimeException")
    void deveSerInstanciaDeRuntimeException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Erro");

        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Deve ter anotação ResponseStatus com NOT_FOUND")
    void deveTerAnotacaoResponseStatusComNotFound() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Erro");
        
        // Verifica se a classe tem a anotação @ResponseStatus
        assertTrue(ResourceNotFoundException.class.isAnnotationPresent(org.springframework.web.bind.annotation.ResponseStatus.class));
        
        org.springframework.web.bind.annotation.ResponseStatus annotation = 
            ResourceNotFoundException.class.getAnnotation(org.springframework.web.bind.annotation.ResponseStatus.class);
        
        assertEquals(org.springframework.http.HttpStatus.NOT_FOUND, annotation.value());
    }

    @Test
    @DisplayName("Deve manter stack trace")
    void deveManterStackTrace() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Recurso não encontrado");
        
        StackTraceElement[] stackTrace = exception.getStackTrace();
        
        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
    }
} 