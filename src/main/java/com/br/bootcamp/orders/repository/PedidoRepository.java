package com.br.bootcamp.orders.repository;

import com.br.bootcamp.orders.model.Pedido;
import com.br.bootcamp.orders.model.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
     * Busca pedidos por data específica
     */
    @Query("SELECT p FROM Pedido p WHERE CAST(p.dataPedido AS date) = :data")
    List<Pedido> findByDataPedidoDate(@Param("data") LocalDate data);
    
    /**
     * Busca pedidos por período
     */
    List<Pedido> findByDataPedidoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
} 