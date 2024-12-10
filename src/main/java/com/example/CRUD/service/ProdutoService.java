package com.example.CRUD.service;

import com.example.CRUD.entity.ProdutoEntity;
import com.example.CRUD.ordenacao.FilaObj;
import com.example.CRUD.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    private final FilaObj<List<ProdutoEntity>> filaPedidos = new FilaObj<>(100);

    public ProdutoEntity save(ProdutoEntity produto) {
        if(produto.getId() != null) {
            throw new IllegalArgumentException("O id deve ser nulo para ser salvo");
        }
        produto.setId(null);
        return produtoRepository.save(produto);
    }

    public List<ProdutoEntity> listarProduto() {
        List<ProdutoEntity> produto = produtoRepository.findAll();
        if(produto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não a produtos cadastrados");
        }
        return produto;
    }

    public ProdutoEntity atualizarProduto(ProdutoEntity produtoEntity, int id) {
        if(!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Não foi possivel alterar o Produto");
        }
        produtoEntity.setId(id);
        return produtoRepository.save(produtoEntity);
    }

    public Optional<ProdutoEntity> produtoPorId(int id) {
        Optional<ProdutoEntity> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            return produto;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
    }

    public List<ProdutoEntity> listarPorCategoria(String categoria) {
        List<ProdutoEntity> produtoPorCategoria = produtoRepository.findAllByCategoria(categoria);

        if(produtoPorCategoria.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não tem produto cadastrado nessa categoria");
        }
        return produtoPorCategoria;
    }

    public ResponseEntity<String> adicionarPedido(List<ProdutoEntity> carrinho) {
        if (carrinho == null || carrinho.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O carrinho está vazio. Não é possível adicionar um pedido.");
        }

        try {
            filaPedidos.insert(carrinho);
            String mensagem = "Pedido adicionado à fila com sucesso! Contém " + carrinho.size() + " produtos.";
            return ResponseEntity.ok(mensagem);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: Fila cheia. Tente novamente mais tarde.");
        }
    }

}
