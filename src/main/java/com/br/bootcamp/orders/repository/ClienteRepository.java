package com.br.bootcamp.orders.repository;

import com.br.bootcamp.orders.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * Busca clientes por nome (case insensitive)
     */
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    
    /**
     * Busca cliente por email
     */
    Cliente findByEmail(String email);
    
    /**
     * Verifica se existe cliente com o email informado
     */
    boolean existsByEmail(String email);
} 