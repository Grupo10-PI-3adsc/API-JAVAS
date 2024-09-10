package com.example.CRUD.service;

import com.example.CRUD.dto.ProdutoDTO;
import com.example.CRUD.entity.ProdutoEntity;
import com.example.CRUD.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;


    public ProdutoEntity save(ProdutoDTO produtoDTO) {

        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setCategoria(produtoDTO.getCategoria());
        produto.setQtdEstoque(produtoDTO.getQtdEstoque());
        produto.setPrecoUnitario(produtoDTO.getPrecoUnitario());
        produto.setFornecedor(produtoDTO.getFornecedor());
        produto.setLocalizacao(produtoDTO.getFornecedor());
        produto.setDataAtualizcao(produtoDTO.getDataAtualizcao());

        return produtoRepository.save(produto);
    }

}
