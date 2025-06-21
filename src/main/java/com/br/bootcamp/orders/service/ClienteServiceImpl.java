package com.br.bootcamp.orders.service;

import com.br.bootcamp.orders.model.Cliente;
import com.br.bootcamp.orders.model.dto.ClienteDTO;
import com.br.bootcamp.orders.repository.ClienteRepository;
import com.br.bootcamp.orders.service.contracts.IClienteService;
import com.br.bootcamp.orders.service.exception.BusinessException;
import com.br.bootcamp.orders.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements IClienteService {
    
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    
    /**
     * Lista todos os clientes
     */
    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
    
    /**
     * Busca cliente por ID
     */
    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return Optional.ofNullable(clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id)));
    }
    
    /**
     * Busca clientes por nome
     */
    @Override
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    /**
     * Busca cliente por email
     */
    @Override
    public Cliente buscarPorEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new ResourceNotFoundException("Cliente não encontrado com o email: " + email);
        }
        return cliente;
    }
    
    /**
     * Salva um novo cliente
     */
    @Override
    public Cliente salvar(ClienteDTO clienteDTO) {
        if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new BusinessException("Já existe um cliente cadastrado com este email: " + clienteDTO.getEmail());
        }
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        return clienteRepository.save(cliente);
    }
    
    /**
     * Atualiza um cliente existente
     */
    @Override
    public Cliente atualizar(Long id, ClienteDTO clienteDTO) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }
    
    /**
     * Deleta um cliente
     */
    @Override
    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
    
    /**
     * Conta o total de clientes
     */
    @Override
    public long contarClientes() {
        return clienteRepository.count();
    }
} 