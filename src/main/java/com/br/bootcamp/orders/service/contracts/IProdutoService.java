package com.br.bootcamp.orders.service.contracts;

import com.br.bootcamp.orders.model.Produto;
import com.br.bootcamp.orders.model.dto.ProdutoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interface que define os contratos para operações de negócio relacionadas a produtos.
 * 
 * <p>Esta interface estabelece os métodos necessários para gerenciar produtos no sistema,
 * incluindo operações CRUD básicas e consultas específicas por diferentes critérios.</p>
 * 
 * <p>A implementação desta interface deve seguir as regras de negócio estabelecidas:</p>
 * <ul>
 *   <li>Validação de campos obrigatórios (nome, preço, descrição)</li>
 *   <li>Preço deve ser positivo</li>
 *   <li>Tratamento de erros apropriado</li>
 *   <li>Controle de estoque</li>
 * </ul>
 * 
 * @author Bootcamp Architecture Software
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IProdutoService {
    
    /**
     * Lista todos os produtos cadastrados no sistema.
     * 
     * <p>Este método retorna uma lista contendo todos os produtos ativos no sistema,
     * ordenados por ID. A lista pode estar vazia se não houver produtos cadastrados.</p>
     * 
     * @return Lista de todos os produtos cadastrados
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    List<Produto> listarTodos();
    
    /**
     * Busca um produto específico pelo seu ID.
     * 
     * <p>Este método realiza uma busca por ID e retorna um Optional contendo o produto
     * se encontrado, ou um Optional vazio se o produto não existir.</p>
     * 
     * @param id ID único do produto a ser buscado
     * @return Optional contendo o produto se encontrado, ou vazio se não encontrado
     * @throws IllegalArgumentException se o ID for null ou negativo
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    Optional<Produto> buscarPorId(Long id);
    
    /**
     * Busca produtos cujo nome contenha o termo fornecido.
     * 
     * <p>A busca é case-insensitive e retorna todos os produtos cujo nome contenha
     * o termo especificado. A busca é parcial, não necessitando correspondência exata.</p>
     * 
     * @param nome Termo a ser buscado no nome dos produtos
     * @return Lista de produtos que contêm o termo no nome
     * @throws IllegalArgumentException se o nome for null ou vazio
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    List<Produto> buscarPorNome(String nome);
    
    /**
     * Busca produtos dentro de uma faixa de preço específica.
     * 
     * <p>Este método retorna todos os produtos cujo preço esteja entre o valor mínimo
     * e máximo especificados, inclusive.</p>
     * 
     * @param precoMinimo Preço mínimo da faixa (inclusive)
     * @param precoMaximo Preço máximo da faixa (inclusive)
     * @return Lista de produtos dentro da faixa de preço especificada
     * @throws IllegalArgumentException se os preços forem negativos ou se o mínimo for maior que o máximo
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    List<Produto> buscarPorFaixaPreco(Double precoMinimo, Double precoMaximo);
    
    /**
     * Salva um novo produto no sistema.
     * 
     * <p>Este método cria um novo produto no sistema, aplicando as seguintes validações:</p>
     * <ul>
     *   <li>Nome, preço e descrição são obrigatórios</li>
     *   <li>Preço deve ser positivo</li>
     *   <li>Quantidade em estoque deve ser não negativa</li>
     * </ul>
     * 
     * @param produto Produto a ser salvo
     * @return Produto salvo com ID gerado
     * @throws IllegalArgumentException se o produto for null ou dados inválidos
     * @throws RuntimeException se houver erro na persistência
     */
    Produto salvar(ProdutoDTO produto);
    
    /**
     * Atualiza os dados de um produto existente.
     * 
     * <p>Este método atualiza os dados de um produto existente, aplicando as mesmas
     * validações do método salvar.</p>
     * 
     * @param id ID do produto a ser atualizado
     * @param produto Novos dados do produto
     * @return Produto atualizado
     * @throws IllegalArgumentException se o ID for null ou o produto for null
     * @throws RuntimeException se o produto não existir ou houver erro na atualização
     */
    Produto atualizar(Long id, ProdutoDTO produto);
    
    /**
     * Remove um produto do sistema.
     * 
     * <p>Este método remove permanentemente um produto do sistema. A remoção é
     * física e não pode ser desfeita. Produtos que estejam em pedidos ativos
     * podem ter restrições de remoção.</p>
     * 
     * @param id ID do produto a ser removido
     * @throws IllegalArgumentException se o ID for null
     * @throws RuntimeException se o produto não existir ou houver erro na remoção
     */
    void deletar(Long id);
    
    /**
     * Conta o número total de produtos cadastrados no sistema.
     * 
     * <p>Este método retorna a contagem total de produtos ativos no sistema,
     * incluindo todos os registros da tabela de produtos.</p>
     * 
     * @return Número total de produtos cadastrados
     * @throws RuntimeException se houver erro na consulta ao banco de dados
     */
    long contarProdutos();
    
    /**
     * Atualiza a quantidade em estoque de um produto.
     * 
     * <p>Este método permite ajustar a quantidade em estoque de um produto específico.
     * A nova quantidade deve ser não negativa.</p>
     * 
     * @param id ID do produto
     * @param novaQuantidade Nova quantidade em estoque
     * @return Produto com estoque atualizado
     * @throws IllegalArgumentException se o ID for null ou a quantidade for negativa
     * @throws RuntimeException se o produto não existir ou houver erro na atualização
     */
    Produto atualizarEstoque(Long id, Integer novaQuantidade);
} 