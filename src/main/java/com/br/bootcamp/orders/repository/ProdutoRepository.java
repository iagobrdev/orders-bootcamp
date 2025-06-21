package com.br.bootcamp.orders.repository;

import com.br.bootcamp.orders.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    /**
     * Busca produtos por nome (case insensitive)
     */
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    
    /**
     * Busca produtos por categoria
     */
    List<Produto> findByCategoria(String categoria);
    
    /**
     * Busca produtos com estoque disponível
     */
    List<Produto> findByQuantidadeEstoqueGreaterThan(Integer quantidade);
    
    /**
     * Busca produtos dentro de uma faixa de preço
     */
    List<Produto> findByPrecoBetweenOrderByPrecoAsc(BigDecimal precoMinimo, BigDecimal precoMaximo);
} 