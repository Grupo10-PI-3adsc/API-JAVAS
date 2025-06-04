package com.example.CRUD.service;

import com.example.CRUD.dto.produto.ProdutoDTO;
import com.example.CRUD.entity.ItensEntity;
import com.example.CRUD.entity.PedidosEntity;
import com.example.CRUD.entity.ProdutoEntity;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.ordenacao.FilaObj;
import com.example.CRUD.repository.ItensRepository;
import com.example.CRUD.repository.PedidoRespository;
import com.example.CRUD.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoService {

    @Autowired
    private  UserService userService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRespository pedidoRespository;

    @Autowired
    private ItensRepository itensRepository;

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

    public List<ProdutoEntity> buscarPorNome(String nome) {
        if (nome != null && nome.length() >= 3 && !nome.isBlank()) {
            List<ProdutoEntity> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
            if (!produtos.isEmpty()) {
                return produtos;
            }
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nenhum produto encontrado com esse nome");
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

    public PedidosEntity adicionarPedido(List<Integer> carrinho, Boolean instalacao, Integer fkUser) {
        if (carrinho == null || carrinho.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O carrinho está vazio. Não é possível adicionar um pedido.");
        }
        if (instalacao == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi expecificado a intação.");
        }

        List<ProdutoEntity> produtosCarrinho = new ArrayList<>();
        for (int i = 0; i < carrinho.size(); i++) {
            Optional<ProdutoEntity> sla = this.produtoPorId(carrinho.get(i));
            produtosCarrinho.add(sla.orElse(null));
        }

        PedidosEntity pedido = new PedidosEntity();
//            pedido.setDataPedido(LocalDateTime.now());
        pedido.setFkUsuario(userService.userPorId(fkUser));
        pedido.setTotal(
                produtosCarrinho.stream()
                        .mapToDouble(ProdutoEntity::getPreco)
                        .sum()
        );

        pedido.setInstalacao(instalacao);
        pedido.setStatus("Aguardando");
        pedido = pedidoRespository.save(pedido);
        try{
            for (ProdutoEntity produto : produtosCarrinho) {
                ItensEntity item = new ItensEntity();
                item.setFkPedido(pedido);
                item.setFkProduto(produto);
                item.setQuantidadeProdutos(1); // Use the 'quantidade' for this specific product
                itensRepository.save(item);
            }


        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao processar o pedido: " + e.getMessage());
        }

        String mensagem = "Pedido criado com sucesso! Contém " + carrinho.size() + " itens.";
        return pedido;

    }

    public List<PedidosEntity> listarPedido() {
        List<PedidosEntity> pedidos = pedidoRespository.findAll();
        if(pedidos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não a pedidos cadastrados");
        }
        return pedidos;
    }

    public PedidosEntity listarPedidoPorId(Integer id) {
        Optional<PedidosEntity> pedidos = pedidoRespository.findById(id);
        if(pedidos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não a pedidos cadastrados");
        }
        return pedidos.get();
    }

    public PedidosEntity finalizarPedido(Integer id) {
        PedidosEntity pedido = listarPedidoPorId(id);

        pedido.setStatus("Concluído");
        pedidoRespository.save(pedido);
        return pedido;
    }

    public PedidosEntity pagarPedido(Integer id) {
        PedidosEntity pedido = listarPedidoPorId(id);

        pedido.setStatus("Pago");
        pedidoRespository.save(pedido);
        return pedido;
    }

    public Double obterSomaPedidosFinalizados() {
        return pedidoRespository.somarPedidosFinalizados(LocalDate.now().withDayOfMonth(1));
    }

    public Integer obterTotalItensEmEstoque() {
        return produtoRepository.totalItensEmEstoque();
    }

    public Integer obterQuantidadeVendasRealizadas() {
        return pedidoRespository.contarVendasRealizadas(LocalDate.now().withDayOfMonth(1));
    }

    public List<ProdutoEntity> listarProdutoPorIdPedido(Integer idPedido) {
        PedidosEntity pedido = listarPedidoPorId(idPedido);
        List<ItensEntity> itens = itensRepository.findAllByFkPedido(pedido);
        List<ProdutoEntity> produtos = new ArrayList<>();
        for (ItensEntity p : itens){
            produtos.add(produtoPorId(p.getFkProduto().getId()).get());
        }
        return produtos;
    }

    public List<PedidosEntity> listarPedidoUsuario(Integer id) {
        List<PedidosEntity> pedidos = pedidoRespository.findAllByFkUsuario_Id(id);
        if(pedidos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não a pedidos cadastrados");
        }
        return pedidos;
    }
}
