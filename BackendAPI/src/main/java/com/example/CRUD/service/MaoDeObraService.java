package com.example.CRUD.service;

import com.example.CRUD.dto.MaoDeObraDTO;
import com.example.CRUD.dto.ProdutoDTO;
import com.example.CRUD.entity.MaoDeObrEntity;
import com.example.CRUD.entity.ProdutoEntity;
import com.example.CRUD.repository.MaoDeObraRepository;
import com.example.CRUD.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MaoDeObraService {

    @Autowired
    private MaoDeObraRepository produtoRepository;


    public MaoDeObrEntity save(MaoDeObraDTO maoDeObraDTO) {

        MaoDeObrEntity produto = new MaoDeObrEntity();
        produto.setNome(maoDeObraDTO.getNome());
        produto.setDescricao(maoDeObraDTO.getDescricao());
        produto.setCategoria(maoDeObraDTO.getCategoria());
        produto.setQtdEstoque(maoDeObraDTO.getQtdEstoque());
        produto.setPrecoUnitario(maoDeObraDTO.getPrecoUnitario());
        produto.setFornecedor(maoDeObraDTO.getFornecedor());
        produto.setLocalizacao(maoDeObraDTO.getFornecedor());
        produto.setDataAtualizcao(maoDeObraDTO.getDataAtualizcao());

        return produtoRepository.save(produto);
    }

}
