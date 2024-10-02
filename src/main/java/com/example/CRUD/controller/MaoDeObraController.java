package com.example.CRUD.controller;

import com.example.CRUD.dto.MaoDeObraDTO;
import com.example.CRUD.entity.MaoDeObrEntity;
import com.example.CRUD.entity.ProdutoEntity;
import com.example.CRUD.repository.MaoDeObraRepository;
import com.example.CRUD.service.MaoDeObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mao-de-obra")
public class MaoDeObraController {


    @Autowired
    private MaoDeObraRepository maoDeObraRepository;
    @Autowired
    private MaoDeObraService maoDeObraService;

    @GetMapping()
    public ResponseEntity<List<MaoDeObrEntity>> listarMaoDeObra() {
        return ResponseEntity.ok().body(maoDeObraService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaoDeObrEntity> buscarPorPorId(@PathVariable int id) {
        return ResponseEntity.ok().body(maoDeObraService.servicoPorId(id));
    }

    @GetMapping("/servico-por-cliente/{id}")
    public ResponseEntity<List<MaoDeObrEntity>> buscarPorCliente(@PathVariable int id) {
        return ResponseEntity.ok().body(maoDeObraService.pesquisarPorCliente(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaoDeObrEntity> atualizarMaoDeObra(@RequestParam MaoDeObrEntity maoDeObrEntity, @PathVariable int id) {
        return ResponseEntity.ok(maoDeObraService.atualizarServico(maoDeObrEntity, id));
    }

    @PostMapping()
    public ResponseEntity<MaoDeObrEntity> criarMaoDeObra(@RequestBody MaoDeObrEntity maoDeObraNovo, @PathVariable int id) {
        return ResponseEntity.ok(maoDeObraService.adicionarServico(maoDeObraNovo, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarServico(@PathVariable Integer id) {
        if(maoDeObraRepository.existsById(id)) {
            maoDeObraService.cancelarServico(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}