package com.br.bootcamp.orders.controller;

import com.br.bootcamp.orders.model.Cliente;
import com.br.bootcamp.orders.model.dto.ClienteDTO;
import com.br.bootcamp.orders.model.dto.ErrorResponseDTO;
import com.br.bootcamp.orders.service.contracts.IClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class ClienteController {

    private final IClienteService clienteService;
    
    /**
     * GET /api/clientes - Lista todos os clientes
     */
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista com todos os clientes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * GET /api/clientes/{id} - Busca cliente por ID
     */
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico baseado no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(
            @Parameter(description = "ID do cliente", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id).get());
    }
    
    /**
     * GET /api/clientes/nome/{nome} - Busca clientes por nome
     */
    @Operation(summary = "Buscar clientes por nome", description = "Retorna clientes cujo nome contenha o termo fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso")
    })
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Cliente>> buscarPorNome(
            @Parameter(description = "Nome ou parte do nome do cliente", required = true) @PathVariable String nome) {
        List<Cliente> clientes = clienteService.buscarPorNome(nome);
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * GET /api/clientes/email/{email} - Busca cliente por email
     */
    @Operation(summary = "Buscar cliente por email", description = "Retorna um cliente específico baseado no email fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> buscarPorEmail(
            @Parameter(description = "Email do cliente", required = true) @PathVariable String email) {
        return ResponseEntity.ok(clienteService.buscarPorEmail(email));
    }
    
    /**
     * GET /api/clientes/contar - Conta total de clientes
     */
    @Operation(summary = "Contar total de clientes", description = "Retorna o número total de clientes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    })
    @GetMapping("/contar")
    public ResponseEntity<Long> contarClientes() {
        long total = clienteService.contarClientes();
        return ResponseEntity.ok(total);
    }
    
    /**
     * POST /api/clientes - Cria um novo cliente
     */
    @Operation(summary = "Criar novo cliente", description = "Cria um novo cliente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Email já cadastrado ou dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<Cliente> criar(
            @Parameter(description = "Dados do cliente", required = true) @RequestBody ClienteDTO clienteDTO) {
        Cliente clienteSalvo = clienteService.salvar(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }
    
    /**
     * PUT /api/clientes/{id} - Atualiza um cliente existente
     */
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(
            @Parameter(description = "ID do cliente", required = true) @PathVariable Long id,
            @Parameter(description = "Novos dados do cliente", required = true) @RequestBody ClienteDTO clienteDTO) {
        Cliente clienteAtualizado = clienteService.atualizar(id, clienteDTO);
        return ResponseEntity.ok(clienteAtualizado);
    }
    
    /**
     * DELETE /api/clientes/{id} - Deleta um cliente
     */
    @Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do cliente", required = true) @PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
} 