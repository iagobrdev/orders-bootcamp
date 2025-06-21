package com.br.bootcamp.orders.model.dto;

import com.br.bootcamp.orders.model.enums.CategoriaProduto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação e atualização de produtos")
public class ProdutoDTO {
    
    @Schema(description = "Nome do produto", example = "Notebook Dell Inspiron", required = true)
    private String nome;
    
    @Schema(description = "Descrição detalhada do produto", example = "Notebook Dell Inspiron 15 polegadas com processador Intel i5")
    private String descricao;
    
    @Schema(description = "Preço do produto", example = "3500.00", required = true)
    private BigDecimal preco;
    
    @Schema(description = "Quantidade disponível em estoque", example = "10", required = true)
    private Integer quantidadeEstoque;
    
    @Schema(
        description = "Categoria do produto",
        example = "ELETRONICOS",
        required = true
    )
    private CategoriaProduto categoria;
} 