package com.example.CRUD.service;

import com.example.CRUD.dto.produto.ProdutoDTO;
import com.example.CRUD.entity.ItensEntity;
import com.example.CRUD.entity.PedidosEntity;
import com.example.CRUD.entity.ProdutoEntity;
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

    public ResponseEntity<String> adicionarPedido(List<Integer> carrinho, Boolean instalacao, Integer fkUser) {
        if (carrinho == null || carrinho.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O carrinho está vazio. Não é possível adicionar um pedido.");
        }

        List<ProdutoEntity> produtosCarrinho = new ArrayList<>();
        for (int i = 0; i < carrinho.size(); i++) {
            Optional<ProdutoEntity> sla = this.produtoPorId(carrinho.get(i));
            produtosCarrinho.add(sla.orElse(null));
        }

        try {
            PedidosEntity pedido = new PedidosEntity();
//            pedido.setDataPedido(LocalDateTime.now());
            pedido.setFkUsuario(userService.userPorId(fkUser));
            pedido.setTotal(
                    produtosCarrinho.stream()
                            .mapToDouble(ProdutoEntity::getPreco)
                            .sum()
            );
            pedido.setInstalacao(instalacao);
            pedido = pedidoRespository.save(pedido);

            Map<Integer, Long> produtoQuantidadeMap = produtosCarrinho.stream()
                    .collect(Collectors.groupingBy(ProdutoEntity::getId, Collectors.counting()));

            for (Map.Entry<Integer, Long> entry : produtoQuantidadeMap.entrySet()) {
                Integer produtoId = entry.getKey();
                Integer quantidade = Math.toIntExact(entry.getValue());

                ProdutoEntity produto = produtosCarrinho.stream()
                        .filter(p -> p.getId().equals(produtoId))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado no carrinho"));

                ItensEntity item = new ItensEntity();
                item.setFkPedido(pedido);
                item.setFkProduto(produto);
                item.setQuantidadeProdutos(quantidade.intValue());
                itensRepository.save(item);
            }

            String mensagem = "Pedido criado com sucesso! Contém " + carrinho.size() + " itens.";
            return ResponseEntity.ok(mensagem);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o pedido: " + e.getMessage());
        }
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

    public ResponseEntity<PedidosEntity> finalizarPedido(Integer id) {
        PedidosEntity pedido = pedidoRespository.findById(id).get();

        if(pedido.getId_pedido() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        pedido.setStatus("finalizado");
        pedidoRespository.save(pedido);
        return ResponseEntity.ok().body(pedido);
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
