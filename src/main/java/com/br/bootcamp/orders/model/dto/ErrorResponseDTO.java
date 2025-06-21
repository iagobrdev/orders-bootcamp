package com.br.bootcamp.orders.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para respostas de erro padronizadas")
public class ErrorResponseDTO {

    @Schema(description = "Timestamp do erro", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Código de status HTTP", example = "404")
    private int status;

    @Schema(description = "Descrição do status HTTP", example = "Not Found")
    private String error;

    @Schema(description = "Mensagem detalhada do erro", example = "Cliente não encontrado com ID: 99")
    private String message;

    @Schema(description = "Caminho da requisição que gerou o erro", example = "/api/clientes/99")
    private String path;
} 