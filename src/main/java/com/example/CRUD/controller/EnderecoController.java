package com.example.CRUD.controller;

import com.example.CRUD.dto.EnderecoDTO;
import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.repository.EnderecoRepository;
import com.example.CRUD.service.EnderecoService;
import com.gtbr.ViaCepClient;
import com.gtbr.domain.Cep;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private EnderecoService enderecoService;

    @PostMapping()
    public ResponseEntity<EnderecoEntity> cadastrarEndereco(@RequestBody EnderecoEntity enderecoEntity) {
        return ResponseEntity.ok().body(enderecoService.save(enderecoEntity));
    }

    @GetMapping()
    public ResponseEntity<List<EnderecoEntity>> listarEnderecos() {
        List<EnderecoEntity> listAddress = enderecoService.listarEnderecos();
        if (listAddress.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listAddress);
    }

    @GetMapping("{id}")
    public ResponseEntity<EnderecoEntity> buscarPorId(@PathVariable int id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }
    

    @GetMapping("{fkCliente}")
    public ResponseEntity<Void> deletaEndereco(@PathVariable Integer fkCliente) {
        enderecoService.listEnderecoPorCliente(fkCliente);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<EnderecoEntity> desativarEnderecoPorId(@PathVariable int id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    @GetMapping("/ordenado")
    public ResponseEntity<List<EnderecoEntity>> InsertSort() {

        List<EnderecoEntity> vetor = enderecoRepository.findAll();

        if (!vetor.isEmpty()) {
            for (int i = 1; i < vetor.size(); i++) {
                EnderecoEntity x = vetor.get(i);
                int j = i - 1;

                // Aqui a comparação deve ser feita dentro do loop
                while (j >= 0 && vetor.get(j).getLocalidade().compareTo(x.getLocalidade()) > 0) {
                    vetor.set(j + 1, vetor.get(j));
                    j = j - 1;
                }
                vetor.set(j + 1, x);
            }

            return ResponseEntity.status(200).body(vetor);
        }
        return ResponseEntity.status(204).build();
    }



}
