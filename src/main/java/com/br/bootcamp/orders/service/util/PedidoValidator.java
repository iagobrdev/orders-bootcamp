package com.br.bootcamp.orders.service.util;

import com.br.bootcamp.orders.model.Cliente;
import com.br.bootcamp.orders.model.ItemPedido;
import com.br.bootcamp.orders.model.Pedido;
import com.br.bootcamp.orders.model.Produto;
import com.br.bootcamp.orders.service.contracts.IClienteService;
import com.br.bootcamp.orders.service.contracts.IProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Classe utilitária responsável por validar pedidos e seus componentes.
 * 
 * <p>Esta classe centraliza todas as validações relacionadas a pedidos,
 * separando a lógica de validação da lógica de negócio principal.</p>
 * 
 * @author Bootcamp Architecture Software
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {
    
    private final IClienteService clienteService;
    private final IProdutoService produtoService;
    
    /**
     * Valida se o cliente do pedido existe e é válido.
     * 
     * @param pedido Pedido a ser validado
     * @throws RuntimeException se o cliente for inválido ou não existir
     */
    public void validarCliente(Pedido pedido) {
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            throw new RuntimeException("Cliente é obrigatório para criar um pedido");
        }
        
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(pedido.getCliente().getId());
        if (clienteOpt.isEmpty()) {
            throw new RuntimeException("Cliente não encontrado com ID: " + pedido.getCliente().getId());
        }
        
        log.debug("Cliente validado com sucesso: {}", pedido.getCliente().getId());
    }
    
    /**
     * Valida se os itens do pedido são válidos.
     * 
     * @param pedido Pedido a ser validado
     * @throws RuntimeException se os itens forem inválidos
     */
    public void validarItens(Pedido pedido) {
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            log.warn("Pedido sem itens - valor total será zero");
            return;
        }
        
        for (ItemPedido item : pedido.getItens()) {
            validarItem(item);
        }
        
        log.debug("Itens do pedido validados com sucesso");
    }
    
    /**
     * Valida um item específico do pedido.
     * 
     * @param item Item a ser validado
     * @throws RuntimeException se o item for inválido
     */
    private void validarItem(ItemPedido item) {
        validarProdutoDoItem(item);
        validarQuantidadeDoItem(item);
    }
    
    /**
     * Valida se o produto do item existe.
     * 
     * @param item Item a ser validado
     * @throws RuntimeException se o produto não existir
     */
    private void validarProdutoDoItem(ItemPedido item) {
        if (item.getProduto() == null || item.getProduto().getId() == null) {
            throw new RuntimeException("Produto é obrigatório para cada item do pedido");
        }
        
        Optional<Produto> produtoOpt = produtoService.buscarPorId(item.getProduto().getId());
        if (produtoOpt.isEmpty()) {
            throw new RuntimeException("Produto não encontrado com ID: " + item.getProduto().getId());
        }
    }
    
    /**
     * Valida se a quantidade do item está disponível em estoque.
     * 
     * @param item Item a ser validado
     * @throws RuntimeException se o estoque for insuficiente
     */
    private void validarQuantidadeDoItem(ItemPedido item) {
        Optional<Produto> produtoOpt = produtoService.buscarPorId(item.getProduto().getId());
        Produto produto = produtoOpt.get();
        
        if (produto.getQuantidadeEstoque() < item.getQuantidade()) {
            throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
        }
        
        if (item.getQuantidade() <= 0) {
            throw new RuntimeException("Quantidade deve ser maior que zero para o produto: " + produto.getNome());
        }
    }
    
    /**
     * Valida todo o pedido (cliente e itens).
     * 
     * @param pedido Pedido a ser validado
     * @throws RuntimeException se o pedido for inválido
     */
    public void validarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new RuntimeException("Pedido não pode ser null");
        }
        
        validarCliente(pedido);
        validarItens(pedido);
        
        log.info("Pedido validado com sucesso");
    }
} 