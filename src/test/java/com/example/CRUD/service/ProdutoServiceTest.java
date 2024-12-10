package com.example.CRUD.service;

import com.example.CRUD.entity.ProdutoEntity;
import com.example.CRUD.ordenacao.FilaObj;
import com.example.CRUD.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve salvar um novo produto com sucesso")
    void save_DeveSalvarNovoProduto() {
        // Arrange
        ProdutoEntity produtoMock = new ProdutoEntity();
        produtoMock.setNome("Produto Teste");
        ProdutoEntity produtoSalvo = new ProdutoEntity();
        produtoSalvo.setId(1);
        produtoSalvo.setNome("Produto Teste");

        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoSalvo);


        ProdutoEntity resultado = produtoService.save(produtoMock);


        assertNotNull(resultado.getId());
        assertEquals("Produto Teste", resultado.getNome());
        verify(produtoRepository, times(1)).save(produtoMock);
    }

    @Test
    @DisplayName("Deve listar todos os produtos")
    void listarProduto_DeveRetornarTodosOsProdutos() {

        List<ProdutoEntity> mockProdutos = List.of(new ProdutoEntity(), new ProdutoEntity());
        when(produtoRepository.findAll()).thenReturn(mockProdutos);


        List<ProdutoEntity> resultado = produtoService.listarProduto();


        assertEquals(2, resultado.size());
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve lançar exceção ao listar produtos se nenhum estiver cadastrado")
    void listarProduto_DeveLancarExcecaoSeNaoHouverProdutos() {

        when(produtoRepository.findAll()).thenReturn(List.of());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            produtoService.listarProduto();
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar produto por ID com sucesso")
    void produtoPorId_DeveRetornarProdutoPorId() {

        int idProduto = 1;
        ProdutoEntity produtoMock = new ProdutoEntity();
        produtoMock.setId(idProduto);
        produtoMock.setNome("Produto Teste");

        when(produtoRepository.findById(idProduto)).thenReturn(Optional.of(produtoMock));


        Optional<ProdutoEntity> resultado = produtoService.produtoPorId(idProduto);


        assertTrue(resultado.isPresent());
        assertEquals("Produto Teste", resultado.get().getNome());
        verify(produtoRepository, times(1)).findById(idProduto);
    }

    @Test
    @DisplayName("Deve adicionar um pedido na fila com sucesso")
    void adicionarPedido_DeveAdicionarPedidoNaFila() {

        ProdutoEntity produtoMock = new ProdutoEntity();
        produtoMock.setNome("Produto Teste");
        List<ProdutoEntity> carrinho = List.of(produtoMock);


        ResponseEntity<String> resultado = produtoService.adicionarPedido(carrinho);


        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertTrue(resultado.getBody().contains("Pedido adicionado à fila com sucesso!"));
    }
}
