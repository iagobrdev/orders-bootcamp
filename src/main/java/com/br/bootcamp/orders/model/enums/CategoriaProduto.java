package com.br.bootcamp.orders.model.enums;

import lombok.Getter;

/**
 * Enum que representa as categorias de produtos disponíveis no sistema.
 * 
 * <p>Este enum define as categorias principais que podem ser atribuídas
 * aos produtos, facilitando a organização e busca por tipo de produto.</p>
 * 
 * @author Bootcamp Architecture Software
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public enum CategoriaProduto {
    
    /**
     * Produtos eletrônicos e tecnológicos.
     */
    ELETRONICOS("Eletrônicos", "Produtos eletrônicos e tecnológicos"),
    
    /**
     * Produtos de vestuário e moda.
     */
    VESTUARIO("Vestuário", "Roupas, calçados e acessórios"),
    
    /**
     * Produtos para casa e decoração.
     */
    CASA_DECORACAO("Casa e Decoração", "Produtos para casa e decoração"),
    
    /**
     * Produtos de beleza e cuidados pessoais.
     */
    BELEZA("Beleza", "Produtos de beleza e cuidados pessoais"),
    
    /**
     * Produtos esportivos e fitness.
     */
    ESPORTES("Esportes", "Produtos esportivos e fitness"),
    
    /**
     * Produtos de informática e tecnologia.
     */
    INFORMATICA("Informática", "Produtos de informática e tecnologia"),
    
    /**
     * Produtos de alimentação e bebidas.
     */
    ALIMENTACAO("Alimentação", "Produtos de alimentação e bebidas"),
    
    /**
     * Produtos de saúde e bem-estar.
     */
    SAUDE("Saúde", "Produtos de saúde e bem-estar"),
    
    /**
     * Produtos infantis e brinquedos.
     */
    INFANTIL("Infantil", "Produtos infantis e brinquedos"),
    
    /**
     * Produtos automotivos.
     */
    AUTOMOTIVO("Automotivo", "Produtos automotivos"),
    
    /**
     * Produtos de jardinagem e agricultura.
     */
    JARDINAGEM("Jardinagem", "Produtos de jardinagem e agricultura"),
    
    /**
     * Produtos de livros e educação.
     */
    LIVROS("Livros", "Produtos de livros e educação"),
    
    /**
     * Produtos diversos ou sem categoria específica.
     */
    OUTROS("Outros", "Produtos diversos ou sem categoria específica");

    /**
     * -- GETTER --
     *  Retorna a descrição amigável da categoria.
     *
     */
    private final String descricao;
    /**
     * -- GETTER --
     *  Retorna os detalhes da categoria.
     *
     */
    private final String detalhes;
    
    /**
     * Construtor do enum.
     * 
     * @param descricao Descrição amigável da categoria
     * @param detalhes Detalhes adicionais sobre a categoria
     */
    CategoriaProduto(String descricao, String detalhes) {
        this.descricao = descricao;
        this.detalhes = detalhes;
    }

    /**
     * Verifica se a categoria é uma categoria principal.
     * 
     * @return true se é categoria principal, false caso contrário
     */
    public boolean isCategoriaPrincipal() {
        return this != OUTROS;
    }
    
    /**
     * Verifica se a categoria é relacionada a tecnologia.
     * 
     * @return true se é categoria de tecnologia, false caso contrário
     */
    public boolean isTecnologia() {
        return this == ELETRONICOS || this == INFORMATICA;
    }
    
    /**
     * Verifica se a categoria é relacionada a saúde e bem-estar.
     * 
     * @return true se é categoria de saúde, false caso contrário
     */
    public boolean isSaude() {
        return this == SAUDE || this == BELEZA;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
} 