package com.example.CRUD.controller;

import com.example.CRUD.dto.MaoDeObraDTO;
import com.example.CRUD.entity.ClienteEntity;
import com.example.CRUD.entity.MaoDeObrEntity;
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
        List<MaoDeObrEntity> maoDeObraOPT = maoDeObraRepository.findAll();

        if(maoDeObraOPT.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(maoDeObraOPT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaoDeObrEntity> atualizarMaoDeObra(@PathVariable int id) {
        Optional<MaoDeObrEntity> maoDeObra = maoDeObraRepository.findById(id);

        if(maoDeObra.isPresent()) {
            return ResponseEntity.status(200).body(maoDeObra.get());
        }

        return ResponseEntity.status(404).build();
    }

    @PostMapping()
    public ResponseEntity<MaoDeObrEntity> criarMaoDeObra(@RequestBody MaoDeObraDTO maoDeObraNovo) {
        maoDeObraNovo.setId(null);
        return ResponseEntity.status(201).body(maoDeObraService.save(maoDeObraNovo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaoDeObrEntity> atualizar(@PathVariable Integer id, @RequestBody MaoDeObrEntity maoDeObra) {
        if(maoDeObraRepository.existsById(id)) {
            maoDeObra.setId(id);
            return ResponseEntity.status(200).body(maoDeObraRepository.save(maoDeObra));
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMaoDeObra(@PathVariable Integer id) {
        if(maoDeObraRepository.existsById(id)) {
            maoDeObraRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/pedidos")
    public ResponseEntity<String> pedidoMaoDeObra() {

        List<MaoDeObrEntity> maoDeObraOPT = maoDeObraRepository.findAll();

        Double total = 0.0;
        if (!maoDeObraOPT.isEmpty()) {
            for (int i = 0; i < maoDeObraOPT.size(); i++) {
                total += maoDeObraOPT.get(i).calcularPreco();
            }
            return ResponseEntity.status(200).body("Preço total de todos os pedidos de Mão de Obra: R$" + total);
        }
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<String> pedidoIdMaoDeObra(@PathVariable Integer id) {

        Optional<MaoDeObrEntity> maoDeObraOPT = maoDeObraRepository.findById(id);

        if (maoDeObraOPT.isPresent()) {
            return ResponseEntity.status(200).body("Preço total de todos os pedidos de Mão de Obra: R$" + maoDeObraOPT.get().calcularPreco());
        }
        return ResponseEntity.status(204).build();
    }

}
