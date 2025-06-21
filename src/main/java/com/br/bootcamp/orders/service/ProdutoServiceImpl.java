package com.br.bootcamp.orders.service;

import com.br.bootcamp.orders.model.Produto;
import com.br.bootcamp.orders.model.dto.ProdutoDTO;
import com.br.bootcamp.orders.repository.ProdutoRepository;
import com.br.bootcamp.orders.service.contracts.IProdutoService;
import com.br.bootcamp.orders.service.exception.BusinessException;
import com.br.bootcamp.orders.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutoServiceImpl implements IProdutoService {
    
    private final ProdutoRepository produtoRepository;
    private final ModelMapper modelMapper;
    
    /**
     * Lista todos os produtos
     */
    @Override
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
    
    /**
     * Busca produto por ID
     */
    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return Optional.ofNullable(produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id)));
    }
    
    /**
     * Busca produtos por nome
     */
    @Override
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    /**
     * Busca produtos por faixa de preço
     */
    @Override
    public List<Produto> buscarPorFaixaPreco(Double precoMinimo, Double precoMaximo) {
        if (precoMinimo == null || precoMaximo == null) {
            throw new BusinessException("Preço mínimo e máximo são obrigatórios.");
        }
        if (precoMinimo < 0 || precoMaximo < 0) {
            throw new BusinessException("Preços não podem ser negativos.");
        }
        if (precoMinimo > precoMaximo) {
            throw new BusinessException("Preço mínimo não pode ser maior que o preço máximo.");
        }
        return produtoRepository.findByPrecoBetweenOrderByPrecoAsc(BigDecimal.valueOf(precoMinimo), BigDecimal.valueOf(precoMaximo));
    }
    
    /**
     * Salva um novo produto a partir de DTO
     */
    @Override
    public Produto salvar(ProdutoDTO produtoDTO) {
        Produto produto = modelMapper.map(produtoDTO, Produto.class);
        validarProduto(produto);
        Produto produtoSalvo = produtoRepository.save(produto);
        log.info("Produto criado com sucesso - ID: {}, Nome: {}", produtoSalvo.getId(), produtoSalvo.getNome());
        return produtoSalvo;
    }
    
    /**
     * Atualiza um produto existente a partir de DTO
     */
    @Override
    public Produto atualizar(Long id, ProdutoDTO produtoDTO) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com ID: " + id);
        }
        Produto produto = modelMapper.map(produtoDTO, Produto.class);
        produto.setId(id);
        validarProduto(produto);
        return produtoRepository.save(produto);
    }
    
    /**
     * Deleta um produto
     */
    @Override
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
    
    /**
     * Conta o total de produtos
     */
    @Override
    public long contarProdutos() {
        return produtoRepository.count();
    }
    
    /**
     * Atualiza o estoque de um produto
     */
    @Override
    public Produto atualizarEstoque(Long id, Integer quantidade) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        if (quantidade < 0) {
            throw new BusinessException("A quantidade em estoque não pode ser negativa.");
        }
        produto.setQuantidadeEstoque(quantidade);
        return produtoRepository.save(produto);
    }
    
    private void validarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new BusinessException("Nome do produto é obrigatório.");
        }
        if (produto.getPreco() == null || produto.getPreco().doubleValue() <= 0) {
            throw new BusinessException("Preço do produto deve ser maior que zero.");
        }
        if (produto.getQuantidadeEstoque() == null || produto.getQuantidadeEstoque() < 0) {
            throw new BusinessException("Quantidade em estoque não pode ser negativa.");
        }
    }
} 