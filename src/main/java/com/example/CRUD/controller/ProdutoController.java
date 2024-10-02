package com.example.CRUD.controller;

import com.example.CRUD.dto.ProdutoDTO;
import com.example.CRUD.entity.ProdutoEntity;
import com.example.CRUD.repository.MaoDeObraRepository;
import com.example.CRUD.repository.ProdutoRepository;
import com.example.CRUD.service.MaoDeObraService;
import com.example.CRUD.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository pedidoProdutoRepository;
    @Autowired
    private ProdutoService pedidoProdutoService;


    @Autowired
    private MaoDeObraRepository maoDeObraRepository;
    @Autowired
    private MaoDeObraService maoDeObraService;
    @Autowired
    private ProdutoService produtoService;

    @GetMapping()
    public ResponseEntity<List<ProdutoEntity>> listar() {
        return ResponseEntity.ok(produtoService.listarProduto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProdutoEntity>> produtoPorId(@PathVariable int id) {
        return ResponseEntity.ok(produtoService.produtoPorId(id));
    }

    @PostMapping()
    public ResponseEntity<ProdutoEntity> criar(@RequestBody ProdutoEntity produtoNovo) {
        return ResponseEntity.status(201).body(pedidoProdutoService.save(produtoNovo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoEntity> atualizar(@PathVariable Integer id, @RequestBody ProdutoEntity produto) {
        return ResponseEntity.status(200).body(produtoService.atualizarProduto(produto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if(pedidoProdutoRepository.existsById(id)) {
            pedidoProdutoRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<String> listarPedidos(@PathVariable Integer id) {
        Optional<ProdutoEntity> produtoOpt = pedidoProdutoRepository.findById(id);
        if(produtoOpt.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body("O total de todos os pedidos de Produto: RS" + produtoOpt.get().calcularPedido());
    }

}