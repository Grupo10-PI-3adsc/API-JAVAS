package com.example.CRUD.controller;

import com.example.CRUD.Pedido;
import com.example.CRUD.dto.produto.ProdutoDTO;
import com.example.CRUD.dto.produto.ProdutoMapper;
import com.example.CRUD.dto.produto.ProdutoResponseDto;
import com.example.CRUD.entity.PedidosEntity;
import com.example.CRUD.entity.ProdutoEntity;
import com.example.CRUD.repository.PedidoRespository;
import com.example.CRUD.repository.ProdutoRepository;
import com.example.CRUD.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Autowired
    private PedidoRespository pedidoRespository;

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
    public ResponseEntity<String> criarPedido(@RequestBody List<Integer> carrinho, @PathVariable Integer id) {
        try {
            return produtoService.adicionarPedido(carrinho, id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao processar o pedido: " + e.getMessage());
        }
    }

    @GetMapping("/pedidos")
    public ResponseEntity<List<PedidosEntity>> listarPedidos() {
        return ResponseEntity.ok(produtoService.listarPedido());
    }
}
