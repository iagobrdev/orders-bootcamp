package com.br.bootcamp.orders.service.contracts;

import com.br.bootcamp.orders.model.Cliente;
import com.br.bootcamp.orders.model.dto.ClienteDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interface que define os contratos para operações de negócio relacionadas a clientes.
 * 
 * <p>Esta interface estabelece os métodos necessários para gerenciar clientes no sistema,
 * incluindo operações CRUD básicas e consultas específicas por diferentes critérios.</p>
 * 
 * <p>A implementação desta interface deve seguir as regras de negócio estabelecidas:</p>
 * <ul>
 *   <li>Validação de email único</li>
 *   <li>Campos obrigatórios (nome e email)</li>
 *   <li>Tratamento de erros apropriado</li>
 * </ul>
 * 
 * @author Bootcamp Architecture Software
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IClienteService {
    
    /**
     * Lista todos os clientes cadastrados no sistema.
     * 
     * <p>Este método retorna uma lista contendo todos os clientes ativos no sistema,
     * ordenados por ID. A lista pode estar vazia se não houver clientes cadastrados.</p>
     * 
     * @return Lista de todos os clientes cadastrados
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    List<Cliente> listarTodos();
    
    /**
     * Busca um cliente específico pelo seu ID.
     * 
     * <p>Este método realiza uma busca por ID e retorna um Optional contendo o cliente
     * se encontrado, ou um Optional vazio se o cliente não existir.</p>
     * 
     * @param id ID único do cliente a ser buscado
     * @return Optional contendo o cliente se encontrado, ou vazio se não encontrado
     * @throws IllegalArgumentException se o ID for null ou negativo
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    Optional<Cliente> buscarPorId(Long id);
    
    /**
     * Busca clientes cujo nome contenha o termo fornecido.
     * 
     * <p>A busca é case-insensitive e retorna todos os clientes cujo nome contenha
     * o termo especificado. A busca é parcial, não necessitando correspondência exata.</p>
     * 
     * @param nome Termo a ser buscado no nome dos clientes
     * @return Lista de clientes que contêm o termo no nome
     * @throws IllegalArgumentException se o nome for null ou vazio
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    List<Cliente> buscarPorNome(String nome);
    
    /**
     * Busca um cliente específico pelo seu email.
     * 
     * <p>Este método realiza uma busca exata por email e retorna o cliente se encontrado,
     * ou null se o cliente não existir. Como o email é único no sistema, no máximo
     * um cliente será retornado.</p>
     * 
     * @param email Email único do cliente a ser buscado
     * @return Cliente se encontrado, ou null se não encontrado
     * @throws IllegalArgumentException se o email for null ou vazio
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    Cliente buscarPorEmail(String email);
    
    /**
     * Salva um novo cliente no sistema.
     * 
     * <p>Este método cria um novo cliente no sistema, aplicando as seguintes validações:</p>
     * <ul>
     *   <li>Email deve ser único no sistema</li>
     *   <li>Nome e email são obrigatórios</li>
     *   <li>Email deve ter formato válido</li>
     * </ul>
     * 
     * @param cliente Cliente a ser salvo
     * @return Cliente salvo com ID gerado
     * @throws IllegalArgumentException se o cliente for null
     * @throws RuntimeException se o email já existir no sistema ou houver erro na persistência
     */
    Cliente salvar(ClienteDTO cliente);
    
    /**
     * Atualiza os dados de um cliente existente.
     * 
     * <p>Este método atualiza os dados de um cliente existente, aplicando as mesmas
     * validações do método salvar, exceto a verificação de email único que considera
     * o cliente atual.</p>
     * 
     * @param id ID do cliente a ser atualizado
     * @param cliente Novos dados do cliente
     * @return Cliente atualizado
     * @throws IllegalArgumentException se o ID for null ou o cliente for null
     * @throws RuntimeException se o cliente não existir ou houver erro na atualização
     */
    Cliente atualizar(Long id, ClienteDTO cliente);
    
    /**
     * Remove um cliente do sistema.
     * 
     * <p>Este método remove permanentemente um cliente do sistema. A remoção é
     * física e não pode ser desfeita.</p>
     * 
     * @param id ID do cliente a ser removido
     * @throws IllegalArgumentException se o ID for null
     * @throws RuntimeException se o cliente não existir ou houver erro na remoção
     */
    void deletar(Long id);
    
    /**
     * Conta o número total de clientes cadastrados no sistema.
     * 
     * <p>Este método retorna a contagem total de clientes ativos no sistema,
     * incluindo todos os registros da tabela de clientes.</p>
     * 
     * @return Número total de clientes cadastrados
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    long contarClientes();
} 