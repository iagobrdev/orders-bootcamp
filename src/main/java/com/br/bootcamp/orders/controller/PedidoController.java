package com.br.bootcamp.orders.controller;

import com.br.bootcamp.orders.model.Pedido;
import com.br.bootcamp.orders.model.dto.ErrorResponseDTO;
import com.br.bootcamp.orders.model.dto.PedidoDTO;
import com.br.bootcamp.orders.model.enums.StatusPedido;
import com.br.bootcamp.orders.service.contracts.IPedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public class PedidoController {

    private final IPedidoService pedidoService;

    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista com todos os pedidos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Pedido.class)))
    })
    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }
    
    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico baseado no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id).get());
    }
    
    @Operation(summary = "Buscar pedidos por cliente", description = "Retorna todos os pedidos de um cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos do cliente encontrados com sucesso")
    })
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> buscarPorCliente(
            @Parameter(description = "ID do cliente", required = true) @PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.buscarPorCliente(clienteId));
    }
    
    @Operation(summary = "Buscar pedidos por status", description = "Retorna pedidos com um status específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status inválido fornecido",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/status")
    public ResponseEntity<List<Pedido>> buscarPorStatus(
            @Parameter(description = "Status do pedido (ex: Pendente, Aprovado).", required = true) 
            @RequestParam StatusPedido status) {
        return ResponseEntity.ok(pedidoService.buscarPorStatus(status));
    }
    
    @Operation(summary = "Buscar pedidos por data", description = "Retorna pedidos de uma data específica (formato: dd/MM/yyyy)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos da data encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato de data inválido",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/data")
    public ResponseEntity<List<Pedido>> buscarPorData(
            @Parameter(description = "Data dos pedidos (formato: dd/MM/yyyy)", required = true) 
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate data) {
        return ResponseEntity.ok(pedidoService.buscarPorData(data));
    }
    
    @Operation(summary = "Buscar pedidos por período", description = "Retorna pedidos dentro de um período específico (formato: dd/MM/yyyy)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos do período encontrados com sucesso")
    })
    @GetMapping("/periodo")
    public ResponseEntity<List<Pedido>> buscarPorPeriodo(
            @Parameter(description = "Data de início do período (formato: dd/MM/yyyy)", required = true) 
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio,
            @Parameter(description = "Data de fim do período (formato: dd/MM/yyyy)", required = true) 
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFim) {
        return ResponseEntity.ok(pedidoService.buscarPorPeriodo(dataInicio, dataFim));
    }
    
    @Operation(summary = "Contar total de pedidos", description = "Retorna o número total de pedidos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    })
    @GetMapping("/contar")
    public ResponseEntity<Long> contarPedidos() {
        return ResponseEntity.ok(pedidoService.contarPedidos());
    }
    
    @Operation(summary = "Calcular valor total do pedido", description = "Calcula e retorna o valor total de um pedido existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valor total calculado com sucesso",
                    content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}/valor-total")
    public ResponseEntity<Double> calcularValorTotal(
            @Parameter(description = "ID do pedido para cálculo do valor total", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.calcularValorTotal(id));
    }
    
    @Operation(summary = "Criar novo pedido", description = "Cria um novo pedido no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos (ex: cliente/produto inexistente, estoque insuficiente)",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<Pedido> criar(
            @Parameter(description = "Dados do pedido", required = true) @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedidoSalvo = pedidoService.salvar(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
    }
    
    @Operation(summary = "Atualizar pedido", description = "Atualiza os dados de um pedido existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id,
            @Parameter(description = "Novos dados do pedido", required = true) @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedidoAtualizado = pedidoService.atualizar(id, pedidoDTO);
        return ResponseEntity.ok(pedidoAtualizado);
    }
    
    @Operation(summary = "Atualizar status do pedido", description = "Atualiza o status de um pedido específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id,
            @Parameter(description = "Novo status para o pedido", required = true) @RequestParam StatusPedido novoStatus) {
        Pedido pedidoAtualizado = pedidoService.atualizarStatus(id, novoStatus);
        return ResponseEntity.ok(pedidoAtualizado);
    }
    
    @Operation(summary = "Deletar pedido", description = "Remove um pedido do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id) {
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
} 