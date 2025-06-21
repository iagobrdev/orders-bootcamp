package com.br.bootcamp.orders.service.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes para BusinessException")
class BusinessExceptionTest {

    @Test
    @DisplayName("Deve criar BusinessException com mensagem")
    void deveCriarBusinessExceptionComMensagem() {
        String mensagem = "Erro de negócio ocorreu";
        BusinessException exception = new BusinessException(mensagem);

        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar BusinessException com mensagem vazia")
    void deveCriarBusinessExceptionComMensagemVazia() {
        BusinessException exception = new BusinessException("");

        assertEquals("", exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar BusinessException com mensagem null")
    void deveCriarBusinessExceptionComMensagemNull() {
        BusinessException exception = new BusinessException(null);

        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Deve ser instância de RuntimeException")
    void deveSerInstanciaDeRuntimeException() {
        BusinessException exception = new BusinessException("Erro");

        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Deve ter anotação ResponseStatus com BAD_REQUEST")
    void deveTerAnotacaoResponseStatusComBadRequest() {
        BusinessException exception = new BusinessException("Erro");
        
        // Verifica se a classe tem a anotação @ResponseStatus
        assertTrue(BusinessException.class.isAnnotationPresent(org.springframework.web.bind.annotation.ResponseStatus.class));
        
        org.springframework.web.bind.annotation.ResponseStatus annotation = 
            BusinessException.class.getAnnotation(org.springframework.web.bind.annotation.ResponseStatus.class);
        
        assertEquals(org.springframework.http.HttpStatus.BAD_REQUEST, annotation.value());
    }

    @Test
    @DisplayName("Deve manter stack trace")
    void deveManterStackTrace() {
        BusinessException exception = new BusinessException("Erro de negócio");
        
        StackTraceElement[] stackTrace = exception.getStackTrace();
        
        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
    }
} 