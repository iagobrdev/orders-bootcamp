package com.br.bootcamp.orders.service.contracts;

import com.br.bootcamp.orders.model.Pedido;
import com.br.bootcamp.orders.model.dto.PedidoDTO;
import com.br.bootcamp.orders.model.enums.StatusPedido;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface que define os contratos para operações de negócio relacionadas a pedidos.
 * 
 * <p>Esta interface estabelece os métodos necessários para gerenciar pedidos no sistema,
 * incluindo operações CRUD básicas e consultas específicas por diferentes critérios.</p>
 * 
 * <p>A implementação desta interface deve seguir as regras de negócio estabelecidas:</p>
 * <ul>
 *   <li>Validação de cliente existente</li>
 *   <li>Validação de produtos existentes e com estoque suficiente</li>
 *   <li>Cálculo automático de valores totais</li>
 *   <li>Controle de status do pedido</li>
 *   <li>Atualização automática de estoque</li>
 * </ul>
 * 
 * @author Bootcamp Architecture Software
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPedidoService {
    
    /**
     * Lista todos os pedidos cadastrados no sistema.
     * 
     * <p>Este método retorna uma lista contendo todos os pedidos ativos no sistema,
     * ordenados por data de criação (mais recentes primeiro). A lista pode estar
     * vazia se não houver pedidos cadastrados.</p>
     * 
     * @return Lista de todos os pedidos cadastrados
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    List<Pedido> listarTodos();
    
    /**
     * Busca um pedido específico pelo seu ID.
     * 
     * <p>Este método realiza uma busca por ID e retorna um Optional contendo o pedido
     * se encontrado, ou um Optional vazio se o pedido não existir.</p>
     * 
     * @param id ID único do pedido a ser buscado
     * @return Optional contendo o pedido se encontrado, ou vazio se não encontrado
     * @throws IllegalArgumentException se o ID for null ou negativo
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    Optional<Pedido> buscarPorId(Long id);
    
    /**
     * Busca pedidos de um cliente específico.
     * 
     * <p>Este método retorna todos os pedidos associados a um cliente específico,
     * ordenados por data de criação (mais recentes primeiro).</p>
     * 
     * @param clienteId ID do cliente
     * @return Lista de pedidos do cliente especificado
     * @throws IllegalArgumentException se o ID do cliente for null ou negativo
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    List<Pedido> buscarPorCliente(Long clienteId);
    
    /**
     * Busca pedidos por status específico.
     * 
     * <p>Este método retorna todos os pedidos que possuem o status especificado,
     * ordenados por data de criação (mais recentes primeiro).</p>
     * 
     * @param status Status dos pedidos a serem buscados
     * @return Lista de pedidos com o status especificado
     * @throws IllegalArgumentException se o status for null
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    List<Pedido> buscarPorStatus(StatusPedido status);
    
    /**
     * Busca pedidos dentro de um período específico.
     * 
     * <p>Este método retorna todos os pedidos criados entre as datas especificadas,
     * inclusive. As datas devem estar no formato LocalDateTime.</p>
     * 
     * @param dataInicio Data de início do período (inclusive)
     * @param dataFim Data de fim do período (inclusive)
     * @return Lista de pedidos no período especificado
     * @throws IllegalArgumentException se as datas forem null ou se a data de início for posterior à data de fim
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    List<Pedido> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim);
    
    /**
     * Busca pedidos por data específica.
     * 
     * <p>Este método retorna todos os pedidos criados em uma data específica,
     * inclusive. A data deve estar no formato LocalDateTime.</p>
     * 
     * @param data Data específica para busca de pedidos
     * @return Lista de pedidos criados na data especificada
     * @throws IllegalArgumentException se a data for null
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    List<Pedido> buscarPorData(LocalDate data);
    
    /**
     * Salva um novo pedido no sistema.
     * 
     * <p>Este método cria um novo pedido no sistema, aplicando as seguintes validações:</p>
     * <ul>
     *   <li>Cliente deve existir no sistema</li>
     *   <li>Produtos devem existir e ter estoque suficiente</li>
     *   <li>Quantidades devem ser positivas</li>
     *   <li>Valores são calculados automaticamente</li>
     * </ul>
     * 
     * <p>O método também atualiza automaticamente o estoque dos produtos.</p>
     * 
     * @param pedido Pedido a ser salvo
     * @return Pedido salvo com ID gerado e valores calculados
     * @throws IllegalArgumentException se o pedido for null ou dados inválidos
     * @throws RuntimeException se cliente/produtos não existirem, estoque insuficiente ou erro na persistência
     */
    Pedido salvar(PedidoDTO pedido);
    
    /**
     * Atualiza os dados de um pedido existente.
     * 
     * <p>Este método atualiza os dados de um pedido existente, aplicando as mesmas
     * validações do método salvar. A atualização pode incluir mudanças nos itens
     * do pedido, o que pode afetar o estoque dos produtos.</p>
     * 
     * @param id ID do pedido a ser atualizado
     * @param pedido Novos dados do pedido
     * @return Pedido atualizado
     * @throws IllegalArgumentException se o ID for null ou o pedido for null
     * @throws RuntimeException se o pedido não existir ou houver erro na atualização
     */
    Pedido atualizar(Long id, PedidoDTO pedido);
    
    /**
     * Remove um pedido do sistema.
     * 
     * <p>Este método remove permanentemente um pedido do sistema. A remoção é
     * física e não pode ser desfeita. O estoque dos produtos pode ser restaurado
     * dependendo do status do pedido.</p>
     * 
     * @param id ID do pedido a ser removido
     * @throws IllegalArgumentException se o ID for null
     * @throws RuntimeException se o pedido não existir ou houver erro na remoção
     */
    void deletar(Long id);
    
    /**
     * Conta o número total de pedidos cadastrados no sistema.
     * 
     * <p>Este método retorna a contagem total de pedidos ativos no sistema,
     * incluindo todos os registros da tabela de pedidos.</p>
     * 
     * @return Número total de pedidos cadastrados
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    long contarPedidos();
    
    /**
     * Atualiza o status de um pedido.
     * 
     * <p>Este método permite alterar o status de um pedido existente. Dependendo
     * do status, podem ser aplicadas regras de negócio específicas (ex: cancelamento
     * pode restaurar estoque).</p>
     * 
     * @param id ID do pedido
     * @param novoStatus Novo status do pedido
     * @return Pedido com status atualizado
     * @throws IllegalArgumentException se o ID for null ou o status for null
     * @throws RuntimeException se o pedido não existir ou houver erro na atualização
     */
    Pedido atualizarStatus(Long id, StatusPedido novoStatus);
    
    /**
     * Calcula o valor total de um pedido usando seu ID.
     * 
     * <p>Este método calcula automaticamente o valor total de um pedido baseado
     * nos itens e suas quantidades. O cálculo considera preços unitários e
     * quantidades de cada item.</p>
     * 
     * @param pedidoId ID do pedido para cálculo do valor total
     * @return Valor total calculado do pedido
     * @throws IllegalArgumentException se o pedidoId for null ou negativo
     * @throws RuntimeException se houver erro no cálculo
     */
    Double calcularValorTotal(Long pedidoId);
} 