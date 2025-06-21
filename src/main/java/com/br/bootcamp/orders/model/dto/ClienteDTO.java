package com.br.bootcamp.orders.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação e atualização de clientes")
public class ClienteDTO {

    @Schema(description = "Nome completo do cliente", example = "João da Silva", required = true)
    private String nome;

    @Schema(description = "Email do cliente (deve ser único)", example = "joao.silva@example.com", required = true)
    private String email;

    @Schema(description = "Telefone de contato do cliente", example = "(11) 99999-9999")
    private String telefone;

    @Schema(description = "Endereço do cliente", example = "Rua das Flores, 123, São Paulo, SP")
    private String endereco;
} 