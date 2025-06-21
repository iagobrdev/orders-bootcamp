package com.br.bootcamp.orders.service.util;

import com.br.bootcamp.orders.model.ItemPedido;
import com.br.bootcamp.orders.model.Pedido;
import com.br.bootcamp.orders.model.Produto;
import com.br.bootcamp.orders.service.contracts.IProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Classe utilitária responsável por cálculos relacionados a pedidos.
 * 
 * <p>Esta classe centraliza todos os cálculos relacionados a pedidos,
 * como cálculo de valores, subtotais e totais.</p>
 * 
 * @author Bootcamp Architecture Software
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoCalculator {
    
    private final IProdutoService produtoService;
    
    /**
     * Calcula o valor total de um pedido.
     * 
     * @param pedido Pedido para cálculo
     * @return Valor total calculado
     */
    public BigDecimal calcularValorTotal(Pedido pedido) {
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal valorTotal = BigDecimal.ZERO;
        
        for (ItemPedido item : pedido.getItens()) {
            BigDecimal subtotal = calcularSubtotalItem(item);
            valorTotal = valorTotal.add(subtotal);
        }
        
        log.debug("Valor total calculado: {}", valorTotal);
        return valorTotal;
    }
    
    /**
     * Calcula o subtotal de um item do pedido.
     * 
     * @param item Item para cálculo
     * @return Subtotal calculado
     */
    public BigDecimal calcularSubtotalItem(ItemPedido item) {
        if (item.getPrecoUnitario() == null || item.getQuantidade() == null) {
            return BigDecimal.ZERO;
        }
        
        return item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()));
    }
    
    /**
     * Prepara os itens do pedido com preços e subtotais calculados.
     * 
     * @param pedido Pedido a ser preparado
     */
    public void prepararItens(Pedido pedido) {
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            return;
        }
        
        for (ItemPedido item : pedido.getItens()) {
            prepararItem(item, pedido);
        }
        
        log.debug("Itens do pedido preparados com sucesso");
    }
    
    /**
     * Prepara um item específico com preço e subtotal calculados.
     * 
     * @param item Item a ser preparado
     * @param pedido Pedido ao qual o item pertence
     */
    private void prepararItem(ItemPedido item, Pedido pedido) {
        // Busca o produto para obter o preço atual
        Optional<Produto> produtoOpt = produtoService.buscarPorId(item.getProduto().getId());
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            
            // Define o preço unitário atual do produto
            item.setPrecoUnitario(produto.getPreco());
            
            // Calcula e define o subtotal
            BigDecimal subtotal = calcularSubtotalItem(item);
            item.setSubtotal(subtotal);
            
            // Define a referência ao pedido
            item.setPedido(pedido);
            
            log.debug("Item preparado - Produto: {}, Quantidade: {}, Preço: {}, Subtotal: {}", 
                     produto.getNome(), item.getQuantidade(), item.getPrecoUnitario(), item.getSubtotal());
        }
    }
    
    /**
     * Calcula o valor total de um pedido e define no objeto.
     * 
     * @param pedido Pedido para cálculo e atualização
     */
    public void calcularEAtualizarValorTotal(Pedido pedido) {
        BigDecimal valorTotal = calcularValorTotal(pedido);
        pedido.setValorTotal(valorTotal);
        
        log.info("Valor total do pedido calculado e atualizado: {}", valorTotal);
    }
    
    /**
     * Prepara completamente um pedido (itens e valor total).
     * 
     * @param pedido Pedido a ser preparado
     */
    public void prepararPedido(Pedido pedido) {
        prepararItens(pedido);
        calcularEAtualizarValorTotal(pedido);
        
        log.info("Pedido preparado completamente");
    }
} 