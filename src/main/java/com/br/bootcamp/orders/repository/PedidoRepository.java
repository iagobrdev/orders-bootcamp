package com.br.bootcamp.orders.repository;

import com.br.bootcamp.orders.model.enums.StatusPedido;
import com.br.bootcamp.orders.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    /**
     * Busca pedidos por cliente
     */
    List<Pedido> findByClienteId(Long clienteId);
    
    /**
     * Busca pedidos por status
     */
    List<Pedido> findByStatus(StatusPedido status);
    
    /**
     * Busca pedidos por período
     */
    List<Pedido> findByDataPedidoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    /**
     * Busca pedidos por cliente e status
     */
    List<Pedido> findByClienteIdAndStatus(Long clienteId, StatusPedido status);
} 