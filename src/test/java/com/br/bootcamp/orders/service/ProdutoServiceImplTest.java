package com.br.bootcamp.orders.service;

import com.br.bootcamp.orders.model.Produto;
import com.br.bootcamp.orders.model.dto.ProdutoDTO;
import com.br.bootcamp.orders.model.enums.CategoriaProduto;
import com.br.bootcamp.orders.repository.ProdutoRepository;
import com.br.bootcamp.orders.service.exception.BusinessException;
import com.br.bootcamp.orders.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Testes para ProdutoServiceImpl")
class ProdutoServiceImplTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ModelMapper modelMapper;

    private ProdutoServiceImpl produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        produtoService = new ProdutoServiceImpl(produtoRepository, modelMapper);
    }

    @Test
    @DisplayName("Deve listar todos os produtos")
    void deveListarTodosOsProdutos() {
        // Arrange
        List<Produto> produtos = Arrays.asList(
                criarProduto(1L, "Notebook", new BigDecimal("3500.00")),
                criarProduto(2L, "Smartphone", new BigDecimal("2500.00"))
        );
        when(produtoRepository.findAll()).thenReturn(produtos);

        // Act
        List<Produto> resultado = produtoService.listarTodos();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Notebook", resultado.get(0).getNome());
        assertEquals("Smartphone", resultado.get(1).getNome());
        verify(produtoRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar produto por ID quando existe")
    void deveBuscarProdutoPorIdQuandoExiste() {
        // Arrange
        Produto produto = criarProduto(1L, "Notebook", new BigDecimal("3500.00"));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        // Act
        Optional<Produto> resultado = produtoService.buscarPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Notebook", resultado.get().getNome());
        verify(produtoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto não existe por ID")
    void deveLancarExcecaoQuandoProdutoNaoExistePorId() {
        // Arrange
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> produtoService.buscarPorId(1L));
        
        assertEquals("Produto não encontrado com ID: 1", exception.getMessage());
        verify(produtoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve buscar produtos por nome")
    void deveBuscarProdutosPorNome() {
        // Arrange
        List<Produto> produtos = Arrays.asList(
                criarProduto(1L, "Notebook Dell", new BigDecimal("3500.00")),
                criarProduto(2L, "Notebook HP", new BigDecimal("3200.00"))
        );
        when(produtoRepository.findByNomeContainingIgnoreCase("Notebook")).thenReturn(produtos);

        // Act
        List<Produto> resultado = produtoService.buscarPorNome("Notebook");

        // Assert
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(p -> p.getNome().contains("Notebook")));
        verify(produtoRepository).findByNomeContainingIgnoreCase("Notebook");
    }

    @Test
    @DisplayName("Deve buscar produtos por faixa de preço válida")
    void deveBuscarProdutosPorFaixaDePrecoValida() {
        // Arrange
        List<Produto> produtos = Arrays.asList(
                criarProduto(1L, "Produto 1", new BigDecimal("100.00")),
                criarProduto(2L, "Produto 2", new BigDecimal("200.00"))
        );
        when(produtoRepository.findByPrecoBetweenOrderByPrecoAsc(
                BigDecimal.valueOf(100.0), BigDecimal.valueOf(300.0))).thenReturn(produtos);

        // Act
        List<Produto> resultado = produtoService.buscarPorFaixaPreco(100.0, 300.0);

        // Assert
        assertEquals(2, resultado.size());
        verify(produtoRepository).findByPrecoBetweenOrderByPrecoAsc(
                BigDecimal.valueOf(100.0), BigDecimal.valueOf(300.0));
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar por faixa de preço com valores null")
    void deveLancarExcecaoAoBuscarPorFaixaDePrecoComValoresNull() {
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> produtoService.buscarPorFaixaPreco(null, 300.0));
        
        assertEquals("Preço mínimo e máximo são obrigatórios.", exception.getMessage());
        verify(produtoRepository, never()).findByPrecoBetweenOrderByPrecoAsc(any(), any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar por faixa de preço com valores negativos")
    void deveLancarExcecaoAoBuscarPorFaixaDePrecoComValoresNegativos() {
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> produtoService.buscarPorFaixaPreco(-100.0, 300.0));
        
        assertEquals("Preços não podem ser negativos.", exception.getMessage());
        verify(produtoRepository, never()).findByPrecoBetweenOrderByPrecoAsc(any(), any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar por faixa de preço com mínimo maior que máximo")
    void deveLancarExcecaoAoBuscarPorFaixaDePrecoComMinimoMaiorQueMaximo() {
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> produtoService.buscarPorFaixaPreco(500.0, 300.0));
        
        assertEquals("Preço mínimo não pode ser maior que o preço máximo.", exception.getMessage());
        verify(produtoRepository, never()).findByPrecoBetweenOrderByPrecoAsc(any(), any());
    }

    @Test
    @DisplayName("Deve salvar produto com sucesso")
    void deveSalvarProdutoComSucesso() {
        // Arrange
        ProdutoDTO produtoDTO = new ProdutoDTO("Notebook", "Descrição", new BigDecimal("3500.00"), 10, CategoriaProduto.ELETRONICOS);
        Produto produto = criarProduto(1L, "Notebook", new BigDecimal("3500.00"));
        
        when(modelMapper.map(produtoDTO, Produto.class)).thenReturn(produto);
        when(produtoRepository.save(produto)).thenReturn(produto);

        // Act
        Produto resultado = produtoService.salvar(produtoDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Notebook", resultado.getNome());
        verify(modelMapper).map(produtoDTO, Produto.class);
        verify(produtoRepository).save(produto);
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar produto com nome vazio")
    void deveLancarExcecaoAoSalvarProdutoComNomeVazio() {
        // Arrange
        ProdutoDTO produtoDTO = new ProdutoDTO("", "Descrição", new BigDecimal("3500.00"), 10, CategoriaProduto.ELETRONICOS);
        Produto produto = new Produto();
        produto.setNome("");
        
        when(modelMapper.map(produtoDTO, Produto.class)).thenReturn(produto);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> produtoService.salvar(produtoDTO));
        
        assertEquals("Nome do produto é obrigatório.", exception.getMessage());
        verify(modelMapper).map(produtoDTO, Produto.class);
        verify(produtoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar produto com preço zero")
    void deveLancarExcecaoAoSalvarProdutoComPrecoZero() {
        // Arrange
        ProdutoDTO produtoDTO = new ProdutoDTO("Notebook", "Descrição", new BigDecimal("0.00"), 10, CategoriaProduto.ELETRONICOS);
        Produto produto = new Produto();
        produto.setNome("Notebook");
        produto.setPreco(new BigDecimal("0.00"));
        
        when(modelMapper.map(produtoDTO, Produto.class)).thenReturn(produto);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> produtoService.salvar(produtoDTO));
        
        assertEquals("Preço do produto deve ser maior que zero.", exception.getMessage());
        verify(modelMapper).map(produtoDTO, Produto.class);
        verify(produtoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar produto com quantidade negativa")
    void deveLancarExcecaoAoSalvarProdutoComQuantidadeNegativa() {
        // Arrange
        ProdutoDTO produtoDTO = new ProdutoDTO("Notebook", "Descrição", new BigDecimal("3500.00"), -1, CategoriaProduto.ELETRONICOS);
        Produto produto = new Produto();
        produto.setNome("Notebook");
        produto.setPreco(new BigDecimal("3500.00"));
        produto.setQuantidadeEstoque(-1);
        
        when(modelMapper.map(produtoDTO, Produto.class)).thenReturn(produto);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> produtoService.salvar(produtoDTO));
        
        assertEquals("Quantidade em estoque não pode ser negativa.", exception.getMessage());
        verify(modelMapper).map(produtoDTO, Produto.class);
        verify(produtoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar produto com sucesso")
    void deveAtualizarProdutoComSucesso() {
        // Arrange
        ProdutoDTO produtoDTO = new ProdutoDTO("Notebook Atualizado", "Descrição", new BigDecimal("4000.00"), 15, CategoriaProduto.ELETRONICOS);
        Produto produto = criarProduto(1L, "Notebook Atualizado", new BigDecimal("4000.00"));
        
        when(produtoRepository.existsById(1L)).thenReturn(true);
        when(modelMapper.map(produtoDTO, Produto.class)).thenReturn(produto);
        when(produtoRepository.save(produto)).thenReturn(produto);

        // Act
        Produto resultado = produtoService.atualizar(1L, produtoDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Notebook Atualizado", resultado.getNome());
        assertEquals(1L, resultado.getId());
        verify(produtoRepository).existsById(1L);
        verify(modelMapper).map(produtoDTO, Produto.class);
        verify(produtoRepository).save(produto);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar produto inexistente")
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        // Arrange
        ProdutoDTO produtoDTO = new ProdutoDTO("Notebook", "Descrição", new BigDecimal("3500.00"), 10, CategoriaProduto.ELETRONICOS);
        when(produtoRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> produtoService.atualizar(1L, produtoDTO));
        
        assertEquals("Produto não encontrado com ID: 1", exception.getMessage());
        verify(produtoRepository).existsById(1L);
        verify(produtoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar produto com sucesso")
    void deveDeletarProdutoComSucesso() {
        // Arrange
        when(produtoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(produtoRepository).deleteById(1L);

        // Act
        assertDoesNotThrow(() -> produtoService.deletar(1L));

        // Assert
        verify(produtoRepository).existsById(1L);
        verify(produtoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar produto inexistente")
    void deveLancarExcecaoAoDeletarProdutoInexistente() {
        // Arrange
        when(produtoRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> produtoService.deletar(1L));
        
        assertEquals("Produto não encontrado com ID: 1", exception.getMessage());
        verify(produtoRepository).existsById(1L);
        verify(produtoRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve contar produtos")
    void deveContarProdutos() {
        // Arrange
        when(produtoRepository.count()).thenReturn(10L);

        // Act
        long resultado = produtoService.contarProdutos();

        // Assert
        assertEquals(10L, resultado);
        verify(produtoRepository).count();
    }

    @Test
    @DisplayName("Deve atualizar estoque com sucesso")
    void deveAtualizarEstoqueComSucesso() {
        // Arrange
        Produto produto = criarProduto(1L, "Notebook", new BigDecimal("3500.00"));
        produto.setQuantidadeEstoque(5);
        
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(produto)).thenReturn(produto);

        // Act
        Produto resultado = produtoService.atualizarEstoque(1L, 10);

        // Assert
        assertNotNull(resultado);
        assertEquals(10, resultado.getQuantidadeEstoque());
        verify(produtoRepository).findById(1L);
        verify(produtoRepository).save(produto);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar estoque de produto inexistente")
    void deveLancarExcecaoAoAtualizarEstoqueDeProdutoInexistente() {
        // Arrange
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> produtoService.atualizarEstoque(1L, 10));
        
        assertEquals("Produto não encontrado com ID: 1", exception.getMessage());
        verify(produtoRepository).findById(1L);
        verify(produtoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar estoque com quantidade negativa")
    void deveLancarExcecaoAoAtualizarEstoqueComQuantidadeNegativa() {
        // Arrange
        Produto produto = criarProduto(1L, "Notebook", new BigDecimal("3500.00"));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> produtoService.atualizarEstoque(1L, -1));
        
        assertEquals("A quantidade em estoque não pode ser negativa.", exception.getMessage());
        verify(produtoRepository).findById(1L);
        verify(produtoRepository, never()).save(any());
    }

    private Produto criarProduto(Long id, String nome, BigDecimal preco) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setDescricao("Descrição do produto");
        produto.setPreco(preco);
        produto.setQuantidadeEstoque(10);
        produto.setCategoria(CategoriaProduto.ELETRONICOS);
        return produto;
    }
} 