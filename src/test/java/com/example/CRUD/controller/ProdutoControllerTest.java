package com.example.CRUD.controller;

import com.example.CRUD.dto.produto.ProdutoDTO;
import com.example.CRUD.entity.ProdutoEntity;
import com.example.CRUD.repository.ProdutoRepository;
import com.example.CRUD.service.ProdutoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoRepository pedidoProdutoRepository;

    @Mock
    private ProdutoService produtoService;

    public ProdutoControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve listar todos os produtos cadastrados com sucesso")
    void listar_DeveRetornarListaDeProdutos() {
        List<ProdutoEntity> produtosMock = List.of(new ProdutoEntity(), new ProdutoEntity());
        when(produtoService.listarProduto()).thenReturn(produtosMock);

        ResponseEntity<List<ProdutoEntity>> response = produtoController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtosMock, response.getBody());
        verify(produtoService, times(1)).listarProduto();
    }

    @Test
    @DisplayName("Deve retornar produto pelo ID com sucesso")
    void produtoPorId_DeveRetornarProdutoPorId() {
        int id = 1;
        ProdutoEntity produtoMock = new ProdutoEntity();
        when(produtoService.produtoPorId(id)).thenReturn(Optional.of(produtoMock));

        ResponseEntity<Optional<ProdutoEntity>> response = produtoController.produtoPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isPresent());
        assertEquals(produtoMock, response.getBody().get());
        verify(produtoService, times(1)).produtoPorId(id);
    }


    @Test
    @DisplayName("Deve atualizar um produto existente com sucesso")
    void atualizar_DeveAtualizarProduto() {
        int id = 1;
        ProdutoDTO produtoDTO = new ProdutoDTO();
        ProdutoEntity produtoMock = new ProdutoEntity();

        when(produtoService.atualizarProduto(any(ProdutoEntity.class), eq(id))).thenReturn(produtoMock);

        ResponseEntity<ProdutoEntity> response = produtoController.atualizar(id, produtoDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoMock, response.getBody());
        verify(produtoService, times(1)).atualizarProduto(any(ProdutoEntity.class), eq(id));
    }

    @Test
    @DisplayName("Deve deletar um produto com sucesso")
    void deletar_DeveDeletarProduto() {
        int id = 1;
        when(pedidoProdutoRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = produtoController.deletar(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pedidoProdutoRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve retornar erro ao criar pedido")
    void criarPedido_DeveRetornarErroInterno() {
        List<ProdutoEntity> carrinho = List.of(new ProdutoEntity());
        when(produtoService.adicionarPedido(carrinho)).thenThrow(new RuntimeException("Erro inesperado"));

        ResponseEntity<String> response = produtoController.criarPedido(carrinho);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Erro inesperado"));
        verify(produtoService, times(1)).adicionarPedido(carrinho);
    }
}
