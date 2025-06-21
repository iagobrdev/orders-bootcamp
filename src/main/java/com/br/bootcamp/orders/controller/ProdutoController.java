package com.br.bootcamp.orders.controller;

import com.br.bootcamp.orders.model.Produto;
import com.br.bootcamp.orders.model.dto.ErrorResponseDTO;
import com.br.bootcamp.orders.model.dto.ProdutoDTO;
import com.br.bootcamp.orders.service.ProdutoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public class ProdutoController {
    private final ProdutoServiceImpl produtoService;

    public ProdutoController(ProdutoServiceImpl produtoService) {
        this.produtoService = produtoService;
    }
    
    /**
     * GET /api/produtos - Lista todos os produtos
     */
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista com todos os produtos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Produto.class)))
    })
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }
    
    /**
     * GET /api/produtos/{id} - Busca produto por ID
     */
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico baseado no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(
            @Parameter(description = "ID do produto", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id).get());
    }
    
    /**
     * GET /api/produtos/nome/{nome} - Busca produtos por nome
     */
    @Operation(summary = "Buscar produtos por nome", description = "Retorna produtos cujo nome contenha o termo fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados com sucesso")
    })
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produto>> buscarPorNome(
            @Parameter(description = "Nome ou parte do nome do produto", required = true) @PathVariable String nome) {
        return ResponseEntity.ok(produtoService.buscarPorNome(nome));
    }
    
    /**
     * GET /api/produtos/preco - Busca produtos por faixa de preço
     */
    @Operation(summary = "Buscar produtos por faixa de preço", description = "Retorna produtos dentro de uma faixa de preço específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros de preço inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/preco")
    public ResponseEntity<List<Produto>> buscarPorFaixaPreco(
            @Parameter(description = "Preço mínimo", required = true) @RequestParam Double precoMinimo,
            @Parameter(description = "Preço máximo", required = true) @RequestParam Double precoMaximo) {
        return ResponseEntity.ok(produtoService.buscarPorFaixaPreco(precoMinimo, precoMaximo));
    }
    
    /**
     * GET /api/produtos/contar - Conta total de produtos
     */
    @Operation(summary = "Contar total de produtos", description = "Retorna o número total de produtos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    })
    @GetMapping("/contar")
    public ResponseEntity<Long> contarProdutos() {
        return ResponseEntity.ok(produtoService.contarProdutos());
    }
    
    /**
     * POST /api/produtos - Cria um novo produto
     */
    @Operation(summary = "Criar novo produto", description = "Cria um novo produto no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<Produto> criar(
            @Parameter(description = "Dados do produto", required = true) @RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(produtoDTO));
    }
    
    /**
     * PUT /api/produtos/{id} - Atualiza um produto existente
     */
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(
            @Parameter(description = "ID do produto", required = true) @PathVariable Long id,
            @Parameter(description = "Novos dados do produto", required = true) @RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.ok(produtoService.atualizar(id, produtoDTO));
    }
    
    /**
     * PUT /api/produtos/{id}/estoque - Atualiza o estoque de um produto
     */
    @Operation(summary = "Atualizar estoque do produto", description = "Atualiza a quantidade em estoque de um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Quantidade inválida",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}/estoque")
    public ResponseEntity<Produto> atualizarEstoque(
            @Parameter(description = "ID do produto", required = true) @PathVariable Long id,
            @Parameter(description = "Nova quantidade em estoque", required = true) @RequestParam Integer quantidade) {
        return ResponseEntity.ok(produtoService.atualizarEstoque(id, quantidade));
    }
    
    /**
     * DELETE /api/produtos/{id} - Deleta um produto
     */
    @Operation(summary = "Deletar produto", description = "Remove um produto do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do produto", required = true) @PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
} 