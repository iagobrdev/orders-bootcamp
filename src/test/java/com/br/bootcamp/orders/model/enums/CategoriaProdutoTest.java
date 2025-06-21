package com.br.bootcamp.orders.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes para CategoriaProduto")
class CategoriaProdutoTest {

    @Test
    @DisplayName("Deve retornar descrição correta para cada categoria")
    void deveRetornarDescricaoCorreta() {
        assertEquals("Eletrônicos", CategoriaProduto.ELETRONICOS.getDescricao());
        assertEquals("Vestuário", CategoriaProduto.VESTUARIO.getDescricao());
        assertEquals("Casa e Decoração", CategoriaProduto.CASA_DECORACAO.getDescricao());
        assertEquals("Beleza", CategoriaProduto.BELEZA.getDescricao());
        assertEquals("Esportes", CategoriaProduto.ESPORTES.getDescricao());
        assertEquals("Informática", CategoriaProduto.INFORMATICA.getDescricao());
        assertEquals("Alimentação", CategoriaProduto.ALIMENTACAO.getDescricao());
        assertEquals("Saúde", CategoriaProduto.SAUDE.getDescricao());
        assertEquals("Infantil", CategoriaProduto.INFANTIL.getDescricao());
        assertEquals("Automotivo", CategoriaProduto.AUTOMOTIVO.getDescricao());
        assertEquals("Jardinagem", CategoriaProduto.JARDINAGEM.getDescricao());
        assertEquals("Livros", CategoriaProduto.LIVROS.getDescricao());
        assertEquals("Outros", CategoriaProduto.OUTROS.getDescricao());
    }

    @Test
    @DisplayName("Deve retornar detalhes corretos para cada categoria")
    void deveRetornarDetalhesCorretos() {
        assertEquals("Produtos eletrônicos e tecnológicos", CategoriaProduto.ELETRONICOS.getDetalhes());
        assertEquals("Roupas, calçados e acessórios", CategoriaProduto.VESTUARIO.getDetalhes());
        assertEquals("Produtos para casa e decoração", CategoriaProduto.CASA_DECORACAO.getDetalhes());
        assertEquals("Produtos de beleza e cuidados pessoais", CategoriaProduto.BELEZA.getDetalhes());
        assertEquals("Produtos esportivos e fitness", CategoriaProduto.ESPORTES.getDetalhes());
        assertEquals("Produtos de informática e tecnologia", CategoriaProduto.INFORMATICA.getDetalhes());
        assertEquals("Produtos de alimentação e bebidas", CategoriaProduto.ALIMENTACAO.getDetalhes());
        assertEquals("Produtos de saúde e bem-estar", CategoriaProduto.SAUDE.getDetalhes());
        assertEquals("Produtos infantis e brinquedos", CategoriaProduto.INFANTIL.getDetalhes());
        assertEquals("Produtos automotivos", CategoriaProduto.AUTOMOTIVO.getDetalhes());
        assertEquals("Produtos de jardinagem e agricultura", CategoriaProduto.JARDINAGEM.getDetalhes());
        assertEquals("Produtos de livros e educação", CategoriaProduto.LIVROS.getDetalhes());
        assertEquals("Produtos diversos ou sem categoria específica", CategoriaProduto.OUTROS.getDetalhes());
    }

    @Test
    @DisplayName("Deve identificar categoria principal corretamente")
    void deveIdentificarCategoriaPrincipalCorretamente() {
        assertTrue(CategoriaProduto.ELETRONICOS.isCategoriaPrincipal());
        assertTrue(CategoriaProduto.VESTUARIO.isCategoriaPrincipal());
        assertTrue(CategoriaProduto.CASA_DECORACAO.isCategoriaPrincipal());
        assertTrue(CategoriaProduto.BELEZA.isCategoriaPrincipal());
        assertTrue(CategoriaProduto.ESPORTES.isCategoriaPrincipal());
        assertTrue(CategoriaProduto.INFORMATICA.isCategoriaPrincipal());
        assertTrue(CategoriaProduto.ALIMENTACAO.isCategoriaPrincipal());
        assertTrue(CategoriaProduto.SAUDE.isCategoriaPrincipal());
        assertTrue(CategoriaProduto.INFANTIL.isCategoriaPrincipal());
        assertTrue(CategoriaProduto.AUTOMOTIVO.isCategoriaPrincipal());
        assertTrue(CategoriaProduto.JARDINAGEM.isCategoriaPrincipal());
        assertTrue(CategoriaProduto.LIVROS.isCategoriaPrincipal());
        assertFalse(CategoriaProduto.OUTROS.isCategoriaPrincipal());
    }

    @Test
    @DisplayName("Deve identificar categoria de tecnologia corretamente")
    void deveIdentificarCategoriaTecnologiaCorretamente() {
        assertTrue(CategoriaProduto.ELETRONICOS.isTecnologia());
        assertTrue(CategoriaProduto.INFORMATICA.isTecnologia());
        assertFalse(CategoriaProduto.VESTUARIO.isTecnologia());
        assertFalse(CategoriaProduto.CASA_DECORACAO.isTecnologia());
        assertFalse(CategoriaProduto.BELEZA.isTecnologia());
        assertFalse(CategoriaProduto.ESPORTES.isTecnologia());
        assertFalse(CategoriaProduto.ALIMENTACAO.isTecnologia());
        assertFalse(CategoriaProduto.SAUDE.isTecnologia());
        assertFalse(CategoriaProduto.INFANTIL.isTecnologia());
        assertFalse(CategoriaProduto.AUTOMOTIVO.isTecnologia());
        assertFalse(CategoriaProduto.JARDINAGEM.isTecnologia());
        assertFalse(CategoriaProduto.LIVROS.isTecnologia());
        assertFalse(CategoriaProduto.OUTROS.isTecnologia());
    }

    @Test
    @DisplayName("Deve identificar categoria de saúde corretamente")
    void deveIdentificarCategoriaSaudeCorretamente() {
        assertTrue(CategoriaProduto.SAUDE.isSaude());
        assertTrue(CategoriaProduto.BELEZA.isSaude());
        assertFalse(CategoriaProduto.ELETRONICOS.isSaude());
        assertFalse(CategoriaProduto.VESTUARIO.isSaude());
        assertFalse(CategoriaProduto.CASA_DECORACAO.isSaude());
        assertFalse(CategoriaProduto.ESPORTES.isSaude());
        assertFalse(CategoriaProduto.INFORMATICA.isSaude());
        assertFalse(CategoriaProduto.ALIMENTACAO.isSaude());
        assertFalse(CategoriaProduto.INFANTIL.isSaude());
        assertFalse(CategoriaProduto.AUTOMOTIVO.isSaude());
        assertFalse(CategoriaProduto.JARDINAGEM.isSaude());
        assertFalse(CategoriaProduto.LIVROS.isSaude());
        assertFalse(CategoriaProduto.OUTROS.isSaude());
    }

    @Test
    @DisplayName("Deve retornar descrição no toString")
    void deveRetornarDescricaoNoToString() {
        assertEquals("Eletrônicos", CategoriaProduto.ELETRONICOS.toString());
        assertEquals("Vestuário", CategoriaProduto.VESTUARIO.toString());
        assertEquals("Casa e Decoração", CategoriaProduto.CASA_DECORACAO.toString());
        assertEquals("Beleza", CategoriaProduto.BELEZA.toString());
        assertEquals("Esportes", CategoriaProduto.ESPORTES.toString());
        assertEquals("Informática", CategoriaProduto.INFORMATICA.toString());
        assertEquals("Alimentação", CategoriaProduto.ALIMENTACAO.toString());
        assertEquals("Saúde", CategoriaProduto.SAUDE.toString());
        assertEquals("Infantil", CategoriaProduto.INFANTIL.toString());
        assertEquals("Automotivo", CategoriaProduto.AUTOMOTIVO.toString());
        assertEquals("Jardinagem", CategoriaProduto.JARDINAGEM.toString());
        assertEquals("Livros", CategoriaProduto.LIVROS.toString());
        assertEquals("Outros", CategoriaProduto.OUTROS.toString());
    }

    @Test
    @DisplayName("Deve ter valores únicos para cada categoria")
    void deveTerValoresUnicosParaCadaCategoria() {
        assertEquals(13, CategoriaProduto.values().length);
        
        // Verifica se todos os valores são únicos
        long count = java.util.Arrays.stream(CategoriaProduto.values())
                .map(CategoriaProduto::getDescricao)
                .distinct()
                .count();
        assertEquals(13, count);
    }
} 