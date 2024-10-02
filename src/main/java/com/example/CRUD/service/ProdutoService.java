package com.example.CRUD.service;

import com.example.CRUD.Pedido;
import com.example.CRUD.entity.ProdutoEntity;
import com.example.CRUD.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public ProdutoEntity save(ProdutoEntity produto) {
        if(produtoRepository.existsById(produto.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um produto nesse id");
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

    public Long quantidadeDeProdEmEstoque() {
        return produtoRepository.sumQuantidade();
    }

}
