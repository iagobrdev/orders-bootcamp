package com.br.bootcamp.orders.model.dto;

import com.br.bootcamp.orders.model.enums.CategoriaProduto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes para ProdutoDTO")
class ProdutoDTOTest {

    @Test
    @DisplayName("Deve criar ProdutoDTO com todos os campos")
    void deveCriarProdutoDTOComTodosOsCampos() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Notebook Dell Inspiron");
        produtoDTO.setDescricao("Notebook Dell Inspiron 15 polegadas com processador Intel i5");
        produtoDTO.setPreco(new BigDecimal("3500.00"));
        produtoDTO.setQuantidadeEstoque(10);
        produtoDTO.setCategoria(CategoriaProduto.ELETRONICOS);

        assertEquals("Notebook Dell Inspiron", produtoDTO.getNome());
        assertEquals("Notebook Dell Inspiron 15 polegadas com processador Intel i5", produtoDTO.getDescricao());
        assertEquals(new BigDecimal("3500.00"), produtoDTO.getPreco());
        assertEquals(10, produtoDTO.getQuantidadeEstoque());
        assertEquals(CategoriaProduto.ELETRONICOS, produtoDTO.getCategoria());
    }

    @Test
    @DisplayName("Deve criar ProdutoDTO com construtor")
    void deveCriarProdutoDTOComConstrutor() {
        ProdutoDTO produtoDTO = new ProdutoDTO(
                "Smartphone Samsung",
                "Smartphone Samsung Galaxy S21",
                new BigDecimal("2500.00"),
                15,
                CategoriaProduto.ELETRONICOS
        );

        assertEquals("Smartphone Samsung", produtoDTO.getNome());
        assertEquals("Smartphone Samsung Galaxy S21", produtoDTO.getDescricao());
        assertEquals(new BigDecimal("2500.00"), produtoDTO.getPreco());
        assertEquals(15, produtoDTO.getQuantidadeEstoque());
        assertEquals(CategoriaProduto.ELETRONICOS, produtoDTO.getCategoria());
    }

    @Test
    @DisplayName("Deve criar ProdutoDTO vazio")
    void deveCriarProdutoDTOVazio() {
        ProdutoDTO produtoDTO = new ProdutoDTO();

        assertNull(produtoDTO.getNome());
        assertNull(produtoDTO.getDescricao());
        assertNull(produtoDTO.getPreco());
        assertNull(produtoDTO.getQuantidadeEstoque());
        assertNull(produtoDTO.getCategoria());
    }

    @Test
    @DisplayName("Deve ser igual quando todos os campos são iguais")
    void deveSerIgualQuandoTodosOsCamposSaoIguais() {
        ProdutoDTO produto1 = new ProdutoDTO("Produto", "Descrição", new BigDecimal("100.00"), 5, CategoriaProduto.ELETRONICOS);
        ProdutoDTO produto2 = new ProdutoDTO("Produto", "Descrição", new BigDecimal("100.00"), 5, CategoriaProduto.ELETRONICOS);

        assertEquals(produto1, produto2);
        assertEquals(produto1.hashCode(), produto2.hashCode());
    }

    @Test
    @DisplayName("Deve ser diferente quando campos são diferentes")
    void deveSerDiferenteQuandoCamposSaoDiferentes() {
        ProdutoDTO produto1 = new ProdutoDTO("Produto A", "Descrição A", new BigDecimal("100.00"), 5, CategoriaProduto.ELETRONICOS);
        ProdutoDTO produto2 = new ProdutoDTO("Produto B", "Descrição B", new BigDecimal("200.00"), 10, CategoriaProduto.VESTUARIO);

        assertNotEquals(produto1, produto2);
        assertNotEquals(produto1.hashCode(), produto2.hashCode());
    }

    @Test
    @DisplayName("Deve retornar toString com todos os campos")
    void deveRetornarToStringComTodosOsCampos() {
        ProdutoDTO produtoDTO = new ProdutoDTO("Produto", "Descrição", new BigDecimal("100.00"), 5, CategoriaProduto.ELETRONICOS);
        String toString = produtoDTO.toString();

        assertTrue(toString.contains("Produto"));
        assertTrue(toString.contains("Descrição"));
        assertTrue(toString.contains("100.00"));
        assertTrue(toString.contains("5"));
    }

    @Test
    @DisplayName("Deve ser igual a si mesmo")
    void deveSerIgualASiMesmo() {
        ProdutoDTO produtoDTO = new ProdutoDTO("Produto", "Descrição", new BigDecimal("100.00"), 5, CategoriaProduto.ELETRONICOS);
        assertEquals(produtoDTO, produtoDTO);
    }

    @Test
    @DisplayName("Deve ser diferente de null")
    void deveSerDiferenteDeNull() {
        ProdutoDTO produtoDTO = new ProdutoDTO("Produto", "Descrição", new BigDecimal("100.00"), 5, CategoriaProduto.ELETRONICOS);
        assertNotEquals(null, produtoDTO);
    }

    @Test
    @DisplayName("Deve ser diferente de objeto de outro tipo")
    void deveSerDiferenteDeObjetoDeOutroTipo() {
        ProdutoDTO produtoDTO = new ProdutoDTO("Produto", "Descrição", new BigDecimal("100.00"), 5, CategoriaProduto.ELETRONICOS);
        String string = "teste";
        assertNotEquals(produtoDTO, string);
    }

    @Test
    @DisplayName("Deve lidar com preços decimais corretamente")
    void deveLidarComPrecosDecimaisCorretamente() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setPreco(new BigDecimal("99.99"));

        assertEquals(new BigDecimal("99.99"), produtoDTO.getPreco());
    }

    @Test
    @DisplayName("Deve lidar com quantidade zero")
    void deveLidarComQuantidadeZero() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setQuantidadeEstoque(0);

        assertEquals(0, produtoDTO.getQuantidadeEstoque());
    }

    @Test
    @DisplayName("Deve lidar com categoria null")
    void deveLidarComCategoriaNull() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setCategoria(null);

        assertNull(produtoDTO.getCategoria());
    }
} 