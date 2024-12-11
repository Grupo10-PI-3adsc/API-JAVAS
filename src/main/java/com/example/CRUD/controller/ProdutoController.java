package com.example.CRUD.controller;

import com.example.CRUD.dto.produto.ProdutoDTO;
import com.example.CRUD.dto.produto.ProdutoMapper;
import com.example.CRUD.dto.produto.ProdutoResponseDto;
import com.example.CRUD.entity.PedidosEntity;
import com.example.CRUD.entity.ProdutoEntity;
import com.example.CRUD.repository.PedidoRespository;
import com.example.CRUD.repository.ProdutoRepository;
import com.example.CRUD.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository pedidoProdutoRepository;
    @Autowired
    private ProdutoService produtoService;


    @Operation(description = "Lista todos os produtos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado")
    })
    @GetMapping("/listar-produtos")
    public ResponseEntity<List<ProdutoEntity>> listar() {
        return ResponseEntity.ok(produtoService.listarProduto());
    }

    @Operation(description = "Busca um produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProdutoEntity>> produtoPorId(@PathVariable int id) {
        return ResponseEntity.ok(produtoService.produtoPorId(id));
    }

    @Operation(description = "Cria um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na criação do produto")
    })
    @PostMapping()
    public ResponseEntity<ProdutoResponseDto> criar(@RequestBody @Valid ProdutoDTO produtoNovo) {
        ProdutoEntity produto = ProdutoMapper.toEntity(produtoNovo);
        ProdutoEntity novoProduto = produtoService.save(produto);
        ProdutoResponseDto responseDto = ProdutoMapper.toDto(novoProduto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User: " + authentication.getName());
        System.out.println("Authorities: " + authentication.getAuthorities());
        return ResponseEntity.created(null).body(responseDto);
    }

    @Operation(description = "Atualiza um produto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoEntity> atualizar(@PathVariable Integer id, @RequestBody ProdutoDTO produto) {
        return ResponseEntity.status(200).body(produtoService.atualizarProduto(ProdutoMapper.toEntity(produto), id));
    }

    @Operation(description = "Deleta um produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if(pedidoProdutoRepository.existsById(id)) {
            pedidoProdutoRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @Operation(description = "Lista os pedidos relacionados a um produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total de pedidos do produto"),
            @ApiResponse(responseCode = "204", description = "Produto não encontrado")
    })
    @GetMapping("/pedidos/{id}")
    public ResponseEntity<String> pedidosPorid(@PathVariable Integer id) {
        Optional<ProdutoEntity> produtoOpt = pedidoProdutoRepository.findById(id);
        if(produtoOpt.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body("O total de todos os pedidos de Produto: RS" + produtoOpt.get().calcularPedido());
    }


    @PostMapping("/pedidos/{id}")
    @Operation(summary = "Criar um novo pedido", description = "Este endpoint cria um novo pedido a partir de um carrinho de produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro inesperado ao processar o pedido")
    })
    public ResponseEntity<String> criarPedido(
            @Parameter(description = "Lista de IDs dos produtos no carrinho") @RequestBody List<Integer> carrinho,
            @Parameter(description = "ID do cliente que está criando o pedido") @PathVariable Integer id) {

        try {
            return produtoService.adicionarPedido(carrinho, id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao processar o pedido: " + e.getMessage());
        }
    }

    @GetMapping("/pedidos")
    @Operation(summary = "Listar todos os pedidos", description = "Este endpoint retorna todos os pedidos existentes")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<PedidosEntity>> listarPedidos() {
        return ResponseEntity.ok(produtoService.listarPedido());
    }

    @PutMapping("/pedidos/finalizar/{id}")
    @Operation(summary = "Finalizar um pedido", description = "Este endpoint finaliza um pedido com o ID especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido finalizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<PedidosEntity> finalizarPedido(@PathVariable Integer id) {
        return Optional.ofNullable(produtoService.finalizarPedido(id))
                .map(pedido -> ResponseEntity.ok(pedido))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()).getBody();
    }

    @GetMapping("/pedidos/soma-finalizados")
    @Operation(summary = "Somar total de vendas no mes", description = "Este endpoint retorna o total vendido no mês")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O valor total dos pedidos finalizados"),
            @ApiResponse(responseCode = "404", description = "Não há vendas finalizadas")
    })
    public ResponseEntity<String> somarPedidosFinalizados() {
        Double soma = produtoService.obterSomaPedidosFinalizados();
        if (soma != null) {
            return ResponseEntity.ok("O valor total dos pedidos finalizados é R$ " + String.format("%.2f", soma));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não há pedidos finalizados para calcular a soma.");
        }
    }

    @Operation(summary = "Obter o total de itens no estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total de itens no estoque retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não há itens cadastrados no estoque")
    })
    @GetMapping("/total-estoque")
    public ResponseEntity<String> totalItensEmEstoque() {
        Integer total = produtoService.obterTotalItensEmEstoque();
        if (total != null) {
            return ResponseEntity.ok("O total de itens no estoque é " + total);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não há itens cadastrados no estoque.");
        }
    }

    @Operation(summary = "Obter a quantidade de vendas realizadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade de vendas realizadas retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não há vendas realizadas para contar")
    })
    @GetMapping("/pedidos/quantidade-realizadas")
    public ResponseEntity<String> quantidadeVendasRealizadas() {
        Integer quantidade = produtoService.obterQuantidadeVendasRealizadas();
        if (quantidade != null) {
            return ResponseEntity.ok("O número total de vendas realizadas é " + quantidade);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não há vendas realizadas para contar.");
        }
    }
}
