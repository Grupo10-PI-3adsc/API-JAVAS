package com.example.CRUD.controller;

import com.example.CRUD.dto.maoDeObra.MaoDeObraDTO;
import com.example.CRUD.dto.maoDeObra.MaoDeObraMapper;
import com.example.CRUD.dto.maoDeObra.MaoDeObraResponseDto;
import com.example.CRUD.dto.veiculo.VeiculoDTO;
import com.example.CRUD.entity.MaoDeObrEntity;
import com.example.CRUD.repository.MaoDeObraRepository;
import com.example.CRUD.service.MaoDeObraService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@RestController
@RequestMapping("/api/mao-de-obra")
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
    public ResponseEntity<MaoDeObraResponseDto> cadastrar(@RequestBody @Valid MaoDeObraDTO maoDeObraDto) {
        MaoDeObrEntity maoDeObra = MaoDeObraMapper.toEntity(maoDeObraDto);
        MaoDeObrEntity novaMaoDeObra = maoDeObraService.adicionarServico(maoDeObra, maoDeObraDto.getFkVeiculo());
        MaoDeObraResponseDto responseDto = MaoDeObraMapper.toDto(novaMaoDeObra);
        return ResponseEntity.created(null).body(responseDto);
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