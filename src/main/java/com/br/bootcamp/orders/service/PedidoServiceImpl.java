package com.br.bootcamp.orders.service;

import com.br.bootcamp.orders.model.ItemPedido;
import com.br.bootcamp.orders.model.Produto;
import com.br.bootcamp.orders.service.exception.BusinessException;
import com.br.bootcamp.orders.service.exception.ResourceNotFoundException;
import com.br.bootcamp.orders.model.Pedido;
import com.br.bootcamp.orders.model.dto.PedidoDTO;
import com.br.bootcamp.orders.model.enums.StatusPedido;
import com.br.bootcamp.orders.repository.ClienteRepository;
import com.br.bootcamp.orders.repository.PedidoRepository;
import com.br.bootcamp.orders.repository.ProdutoRepository;
import com.br.bootcamp.orders.service.contracts.IPedidoService;
import com.br.bootcamp.orders.service.util.PedidoCalculator;
import com.br.bootcamp.orders.service.util.PedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoServiceImpl implements IPedidoService {
    
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;
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
     * Busca pedidos por data específica
     */
    @Override
    public List<Pedido> buscarPorData(LocalDate data) {
        return pedidoRepository.findByDataPedidoDate(data);
    }
    
    /**
     * Busca pedidos por período
     */
    @Override
    public List<Pedido> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        return pedidoRepository.findByDataPedidoBetween(inicio, fim);
    }
    
    /**
     * Salva um novo pedido a partir de DTO
     */
    @Override
    public Pedido salvar(PedidoDTO pedidoDTO) {
        log.info("Iniciando criação de novo pedido a partir de DTO");
        
        Pedido pedido = new Pedido();
        pedido.setItens(new ArrayList<>());

        try {
            clienteRepository.findById(pedidoDTO.getClienteId())
                .ifPresentOrElse(
                    pedido::setCliente,
                    () -> { throw new BusinessException("Cliente não encontrado com ID: " + pedidoDTO.getClienteId()); }
                );
            
            pedido.setTipoPagamento(pedidoDTO.getTipoPagamento());

            pedidoDTO.getItens().forEach(itemDTO -> {
                Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                        .orElseThrow(() -> new BusinessException("Produto não encontrado com ID: " + itemDTO.getProdutoId()));

                ItemPedido item = new ItemPedido();
                item.setProduto(produto);
                item.setQuantidade(itemDTO.getQuantidade());
                item.setPedido(pedido);

                pedido.getItens().add(item);
            });

            pedidoValidator.validarPedido(pedido);
            configurarDadosIniciais(pedido);
            pedidoCalculator.prepararPedido(pedido);
            
            Pedido pedidoSalvo = pedidoRepository.save(pedido);
        
            log.info("Pedido criado com sucesso - ID: {}, Valor Total: {}", 
                    pedidoSalvo.getId(), pedidoSalvo.getValorTotal());
            
            return pedidoSalvo;

        } catch (RuntimeException e) {
            log.error("Erro ao criar o pedido: {}", e.getMessage(), e);
            throw new BusinessException("Erro ao processar o pedido. Verifique os dados fornecidos.");
        }
    }
    
    /**
     * Atualiza um pedido existente a partir de DTO
     */
    @Override
    public Pedido atualizar(Long id, PedidoDTO pedidoDTO) {
        log.info("Iniciando atualização do pedido ID: {}", id);
        
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));

        clienteRepository.findById(pedidoDTO.getClienteId())
            .ifPresentOrElse(
                pedidoExistente::setCliente,
                () -> { throw new BusinessException("Cliente não encontrado com ID: " + pedidoDTO.getClienteId()); }
            );

        pedidoExistente.setStatus(pedidoDTO.getStatus());
        pedidoExistente.setTipoPagamento(pedidoDTO.getTipoPagamento());

        Map<Long, ItemPedido> itensExistentesMap = pedidoExistente.getItens().stream()
                .collect(Collectors.toMap(item -> item.getProduto().getId(), item -> item));

        Set<Long> produtoIdsDoDto = pedidoDTO.getItens().stream()
                .map(PedidoDTO.ItemPedidoDTO::getProdutoId)
                .collect(Collectors.toSet());

        pedidoExistente.getItens().removeIf(item -> !produtoIdsDoDto.contains(item.getProduto().getId()));

        pedidoDTO.getItens().forEach(itemDTO -> {
            Long produtoId = itemDTO.getProdutoId();
            ItemPedido itemExistente = itensExistentesMap.get(produtoId);

            if (itemExistente != null) {
                log.debug("Atualizando item para o produto ID: {}. Nova quantidade: {}", produtoId, itemDTO.getQuantidade());
                itemExistente.setQuantidade(itemDTO.getQuantidade());
            } else {
                log.debug("Adicionando novo item para o produto ID: {}", produtoId);
                Produto produto = produtoRepository.findById(produtoId)
                        .orElseThrow(() -> new BusinessException("Produto não encontrado com ID: " + produtoId));
                
                ItemPedido novoItem = new ItemPedido();
                novoItem.setProduto(produto);
                novoItem.setQuantidade(itemDTO.getQuantidade());
                novoItem.setPedido(pedidoExistente);
                
                pedidoExistente.getItens().add(novoItem);
            }
        });

        try {
            pedidoValidator.validarPedido(pedidoExistente);
            pedidoCalculator.prepararPedido(pedidoExistente);
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }

        Pedido pedidoAtualizado = pedidoRepository.save(pedidoExistente);

        log.info("Pedido ID: {} atualizado com sucesso. Novo valor total: {}",
                pedidoAtualizado.getId(), pedidoAtualizado.getValorTotal());

        return pedidoAtualizado;
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
    private Double calcularValorTotal(Pedido pedido) {
        if (pedido == null) {
            throw new BusinessException("Pedido não pode ser nulo para calcular o valor total.");
        }
        
        BigDecimal valorTotal = pedidoCalculator.calcularValorTotal(pedido);
        return valorTotal.doubleValue();
    }

    /**
     * Calcula o valor total de um pedido por ID.
     */
    @Override
    public Double calcularValorTotal(Long pedidoId) {
        Pedido pedido = buscarPorId(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + pedidoId));
        return calcularValorTotal(pedido);
    }
} 