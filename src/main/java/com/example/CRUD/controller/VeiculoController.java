package com.example.CRUD.controller;

import com.example.CRUD.dto.veiculo.VeiculoDTO;
import com.example.CRUD.entity.VeiculoEntity;
import com.example.CRUD.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<VeiculoEntity> createVeiculo(@RequestBody VeiculoDTO veiculoDTO) {
        VeiculoEntity veiculo = veiculoService.save(veiculoDTO);
        return ResponseEntity.created(null).body(veiculo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoEntity> getVeiculoById(@PathVariable Integer id) {
        VeiculoEntity veiculo = veiculoService.getById(id);
        return ResponseEntity.ok().body(veiculo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoEntity> updateVeiculo(@PathVariable Integer id, @RequestBody VeiculoDTO veiculoDTO) {
        VeiculoEntity veiculoAtualizado = veiculoService.atualizar(id, veiculoDTO);
        return ResponseEntity.ok().body(veiculoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeiculo(@PathVariable Integer id) {
        veiculoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
