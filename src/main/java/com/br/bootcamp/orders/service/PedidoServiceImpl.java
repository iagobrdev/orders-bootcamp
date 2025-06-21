package com.br.bootcamp.orders.service;

import com.br.bootcamp.orders.service.exception.BusinessException;
import com.br.bootcamp.orders.service.exception.ResourceNotFoundException;
import com.br.bootcamp.orders.model.Pedido;
import com.br.bootcamp.orders.model.dto.PedidoDTO;
import com.br.bootcamp.orders.model.enums.StatusPedido;
import com.br.bootcamp.orders.repository.PedidoRepository;
import com.br.bootcamp.orders.service.contracts.IPedidoService;
import com.br.bootcamp.orders.service.util.PedidoCalculator;
import com.br.bootcamp.orders.service.util.PedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoServiceImpl implements IPedidoService {
    
    private final PedidoRepository pedidoRepository;
    private final PedidoValidator pedidoValidator;
    private final PedidoCalculator pedidoCalculator;
    private final ModelMapper modelMapper;
    
    /**
     * Lista todos os pedidos
     */
    @Override
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }
    
    /**
     * Busca pedido por ID
     */
    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return Optional.ofNullable(pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id)));
    }
    
    /**
     * Busca pedidos por cliente
     */
    @Override
    public List<Pedido> buscarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }
    
    /**
     * Busca pedidos por status
     */
    @Override
    public List<Pedido> buscarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }
    
    /**
     * Busca pedidos por período
     */
    @Override
    public List<Pedido> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return pedidoRepository.findByDataPedidoBetween(dataInicio, dataFim);
    }
    
    /**
     * Salva um novo pedido a partir de DTO
     */
    public Pedido salvar(PedidoDTO pedidoDTO) {
        log.info("Iniciando criação de novo pedido a partir de DTO");
        
        Pedido pedido = modelMapper.map(pedidoDTO, Pedido.class);
        
        try {
            pedidoValidator.validarPedido(pedido);
            configurarDadosIniciais(pedido);
            pedidoCalculator.prepararPedido(pedido);
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
        
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        
        log.info("Pedido criado com sucesso - ID: {}, Valor Total: {}", 
                pedidoSalvo.getId(), pedidoSalvo.getValorTotal());
        
        return pedidoSalvo;
    }
    
    /**
     * Salva um novo pedido (método original)
     */
    @Override
    public Pedido salvar(Pedido pedido) {
        log.info("Iniciando criação de novo pedido");
        
        try {
            pedidoValidator.validarPedido(pedido);
            configurarDadosIniciais(pedido);
            pedidoCalculator.prepararPedido(pedido);
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
        
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        
        log.info("Pedido criado com sucesso - ID: {}, Valor Total: {}", 
                pedidoSalvo.getId(), pedidoSalvo.getValorTotal());
        
        return pedidoSalvo;
    }
    
    /**
     * Atualiza um pedido existente a partir de DTO
     */
    public Pedido atualizar(Long id, PedidoDTO pedidoDTO) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado com ID: " + id);
        }
        
        Pedido pedido = modelMapper.map(pedidoDTO, Pedido.class);
        pedido.setId(id);
        
        return pedidoRepository.save(pedido);
    }
    
    /**
     * Configura os dados iniciais do pedido
     */
    private void configurarDadosIniciais(Pedido pedido) {
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);
        
        log.debug("Dados iniciais configurados - Data: {}, Status: {}", 
                 pedido.getDataPedido(), pedido.getStatus());
    }
    
    /**
     * Atualiza um pedido existente (método original)
     */
    @Override
    public Pedido atualizar(Long id, Pedido pedido) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado com ID: " + id);
        }
        pedido.setId(id);
        return pedidoRepository.save(pedido);
    }
    
    /**
     * Atualiza o status de um pedido
     */
    @Override
    public Pedido atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));
        
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }
    
    /**
     * Deleta um pedido
     */
    @Override
    public void deletar(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado com ID: " + id);
        }
        pedidoRepository.deleteById(id);
    }
    
    /**
     * Conta o total de pedidos
     */
    @Override
    public long contarPedidos() {
        return pedidoRepository.count();
    }
    
    /**
     * Calcula o valor total de um pedido
     */
    @Override
    public Double calcularValorTotal(Pedido pedido) {
        if (pedido == null) {
            throw new BusinessException("Pedido não pode ser nulo para calcular o valor total.");
        }
        
        BigDecimal valorTotal = pedidoCalculator.calcularValorTotal(pedido);
        return valorTotal.doubleValue();
    }
} 