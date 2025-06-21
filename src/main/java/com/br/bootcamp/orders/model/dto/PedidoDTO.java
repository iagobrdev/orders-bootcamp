package com.br.bootcamp.orders.model.dto;

import com.br.bootcamp.orders.model.enums.StatusPedido;
import com.br.bootcamp.orders.model.enums.TipoPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação e atualização de pedidos")
public class PedidoDTO {
    
    @Schema(description = "ID do cliente", example = "1", required = true)
    private Long clienteId;
    
    @Schema(description = "Data e hora do pedido", example = "2024-01-15T10:30:00")
    private LocalDateTime dataPedido;
    
    @Schema(
        description = "Status do pedido",
        example = "PENDENTE",
        required = true
    )
    private StatusPedido status;
    
    @Schema(
        description = "Tipo de pagamento",
        example = "PIX",
        required = true
    )
    private TipoPagamento tipoPagamento;
    
    @Schema(description = "Lista de itens do pedido", required = true)
    private List<ItemPedidoDTO> itens;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "DTO para itens do pedido")
    public static class ItemPedidoDTO {
        
        @Schema(description = "ID do produto", example = "1", required = true)
        private Long produtoId;
        
        @Schema(description = "Quantidade do produto", example = "2", required = true)
        private Integer quantidade;
    }
} 