package com.br.bootcamp.orders.controller;

import com.br.bootcamp.orders.model.Produto;
import com.br.bootcamp.orders.model.dto.ProdutoDTO;
import com.br.bootcamp.orders.model.enums.CategoriaProduto;
import com.br.bootcamp.orders.service.contracts.IProdutoService;
import com.br.bootcamp.orders.service.exception.BusinessException;
import com.br.bootcamp.orders.service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Testes para ProdutoController")
class ProdutoControllerTest {

    @Mock
    private IProdutoService produtoService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private Produto produto;
    private ProdutoDTO produtoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new ProdutoController(produtoService)).build();
        objectMapper = new ObjectMapper();

        // Setup dados de teste
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Notebook Dell Inspiron");
        produto.setDescricao("Notebook Dell Inspiron 15 polegadas com processador Intel i5");
        produto.setPreco(new BigDecimal("3500.00"));
        produto.setQuantidadeEstoque(10);
        produto.setCategoria(CategoriaProduto.ELETRONICOS);

        produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Notebook Dell Inspiron");
        produtoDTO.setDescricao("Notebook Dell Inspiron 15 polegadas com processador Intel i5");
        produtoDTO.setPreco(new BigDecimal("3500.00"));
        produtoDTO.setQuantidadeEstoque(10);
        produtoDTO.setCategoria(CategoriaProduto.ELETRONICOS);
    }

    @Test
    @DisplayName("Deve listar todos os produtos com sucesso")
    void deveListarTodosOsProdutosComSucesso() throws Exception {
        // Arrange
        List<Produto> produtos = List.of(produto);
        when(produtoService.listarTodos()).thenReturn(produtos);

        // Act & Assert
        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Notebook Dell Inspiron"))
                .andExpect(jsonPath("$[0].categoria").value("ELETRONICOS"));

        verify(produtoService).listarTodos();
    }

    @Test
    @DisplayName("Deve buscar produto por ID com sucesso")
    void deveBuscarProdutoPorIdComSucesso() throws Exception {
        // Arrange
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.of(produto));

        // Act & Assert
        mockMvc.perform(get("/api/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Notebook Dell Inspiron"))
                .andExpect(jsonPath("$.preco").value(3500.00));

        verify(produtoService).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 quando produto não encontrado por ID")
    void deveRetornar404QuandoProdutoNaoEncontradoPorId() throws Exception {
        // Arrange
        when(produtoService.buscarPorId(999L)).thenThrow(new ResourceNotFoundException("Produto não encontrado"));

        // Act & Assert
        mockMvc.perform(get("/api/produtos/999"))
                .andExpect(status().isNotFound());

        verify(produtoService).buscarPorId(999L);
    }

    @Test
    @DisplayName("Deve buscar produtos por nome com sucesso")
    void deveBuscarProdutosPorNomeComSucesso() throws Exception {
        // Arrange
        List<Produto> produtos = List.of(produto);
        when(produtoService.buscarPorNome("Notebook")).thenReturn(produtos);

        // Act & Assert
        mockMvc.perform(get("/api/produtos/nome/Notebook"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Notebook Dell Inspiron"));

        verify(produtoService).buscarPorNome("Notebook");
    }

    @Test
    @DisplayName("Deve buscar produtos por faixa de preço com sucesso")
    void deveBuscarProdutosPorFaixaPrecoComSucesso() throws Exception {
        // Arrange
        List<Produto> produtos = List.of(produto);
        when(produtoService.buscarPorFaixaPreco(1000.0, 5000.0)).thenReturn(produtos);

        // Act & Assert
        mockMvc.perform(get("/api/produtos/preco")
                .param("precoMinimo", "1000.0")
                .param("precoMaximo", "5000.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Notebook Dell Inspiron"));

        verify(produtoService).buscarPorFaixaPreco(1000.0, 5000.0);
    }

    @Test
    @DisplayName("Deve retornar 400 quando parâmetros de preço inválidos")
    void deveRetornar400QuandoParametrosDePrecoInvalidos() throws Exception {
        // Arrange
        when(produtoService.buscarPorFaixaPreco(-100.0, 5000.0))
                .thenThrow(new BusinessException("Preço mínimo não pode ser negativo"));

        // Act & Assert
        mockMvc.perform(get("/api/produtos/preco")
                .param("precoMinimo", "-100.0")
                .param("precoMaximo", "5000.0"))
                .andExpect(status().isBadRequest());

        verify(produtoService).buscarPorFaixaPreco(-100.0, 5000.0);
    }

    @Test
    @DisplayName("Deve contar total de produtos com sucesso")
    void deveContarTotalDeProdutosComSucesso() throws Exception {
        // Arrange
        when(produtoService.contarProdutos()).thenReturn(15L);

        // Act & Assert
        mockMvc.perform(get("/api/produtos/contar"))
                .andExpect(status().isOk())
                .andExpect(content().string("15"));

        verify(produtoService).contarProdutos();
    }

    @Test
    @DisplayName("Deve criar novo produto com sucesso")
    void deveCriarNovoProdutoComSucesso() throws Exception {
        // Arrange
        when(produtoService.salvar(produtoDTO)).thenReturn(produto);

        // Act & Assert
        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Notebook Dell Inspiron"))
                .andExpect(jsonPath("$.categoria").value("ELETRONICOS"));

        verify(produtoService).salvar(produtoDTO);
    }

    @Test
    @DisplayName("Deve retornar 400 quando dados inválidos para criar produto")
    void deveRetornar400QuandoDadosInvalidosParaCriarProduto() throws Exception {
        // Arrange
        when(produtoService.salvar(produtoDTO)).thenThrow(new BusinessException("Nome do produto é obrigatório"));

        // Act & Assert
        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(status().isBadRequest());

        verify(produtoService).salvar(produtoDTO);
    }

    @Test
    @DisplayName("Deve atualizar produto com sucesso")
    void deveAtualizarProdutoComSucesso() throws Exception {
        // Arrange
        when(produtoService.atualizar(1L, produtoDTO)).thenReturn(produto);

        // Act & Assert
        mockMvc.perform(put("/api/produtos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Notebook Dell Inspiron"));

        verify(produtoService).atualizar(1L, produtoDTO);
    }

    @Test
    @DisplayName("Deve retornar 404 quando produto não encontrado para atualização")
    void deveRetornar404QuandoProdutoNaoEncontradoParaAtualizacao() throws Exception {
        // Arrange
        when(produtoService.atualizar(999L, produtoDTO))
                .thenThrow(new ResourceNotFoundException("Produto não encontrado"));

        // Act & Assert
        mockMvc.perform(put("/api/produtos/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(status().isNotFound());

        verify(produtoService).atualizar(999L, produtoDTO);
    }

    @Test
    @DisplayName("Deve retornar 400 quando dados inválidos para atualização")
    void deveRetornar400QuandoDadosInvalidosParaAtualizacao() throws Exception {
        // Arrange
        when(produtoService.atualizar(1L, produtoDTO))
                .thenThrow(new BusinessException("Preço não pode ser negativo"));

        // Act & Assert
        mockMvc.perform(put("/api/produtos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(status().isBadRequest());

        verify(produtoService).atualizar(1L, produtoDTO);
    }

    @Test
    @DisplayName("Deve atualizar estoque do produto com sucesso")
    void deveAtualizarEstoqueDoProdutoComSucesso() throws Exception {
        // Arrange
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(1L);
        produtoAtualizado.setNome("Notebook Dell Inspiron");
        produtoAtualizado.setQuantidadeEstoque(20);
        when(produtoService.atualizarEstoque(1L, 20)).thenReturn(produtoAtualizado);

        // Act & Assert
        mockMvc.perform(put("/api/produtos/1/estoque")
                .param("quantidade", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantidadeEstoque").value(20));

        verify(produtoService).atualizarEstoque(1L, 20);
    }

    @Test
    @DisplayName("Deve retornar 404 quando produto não encontrado para atualização de estoque")
    void deveRetornar404QuandoProdutoNaoEncontradoParaAtualizacaoDeEstoque() throws Exception {
        // Arrange
        when(produtoService.atualizarEstoque(999L, 20))
                .thenThrow(new ResourceNotFoundException("Produto não encontrado"));

        // Act & Assert
        mockMvc.perform(put("/api/produtos/999/estoque")
                .param("quantidade", "20"))
                .andExpect(status().isNotFound());

        verify(produtoService).atualizarEstoque(999L, 20);
    }

    @Test
    @DisplayName("Deve retornar 400 quando quantidade inválida para atualização de estoque")
    void deveRetornar400QuandoQuantidadeInvalidaParaAtualizacaoDeEstoque() throws Exception {
        // Arrange
        when(produtoService.atualizarEstoque(1L, -5))
                .thenThrow(new BusinessException("Quantidade não pode ser negativa"));

        // Act & Assert
        mockMvc.perform(put("/api/produtos/1/estoque")
                .param("quantidade", "-5"))
                .andExpect(status().isBadRequest());

        verify(produtoService).atualizarEstoque(1L, -5);
    }

    @Test
    @DisplayName("Deve deletar produto com sucesso")
    void deveDeletarProdutoComSucesso() throws Exception {
        // Arrange
        doNothing().when(produtoService).deletar(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/produtos/1"))
                .andExpect(status().isNoContent());

        verify(produtoService).deletar(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 quando produto não encontrado para deleção")
    void deveRetornar404QuandoProdutoNaoEncontradoParaDelecao() throws Exception {
        // Arrange
        doThrow(new ResourceNotFoundException("Produto não encontrado")).when(produtoService).deletar(999L);

        // Act & Assert
        mockMvc.perform(delete("/api/produtos/999"))
                .andExpect(status().isNotFound());

        verify(produtoService).deletar(999L);
    }
} 