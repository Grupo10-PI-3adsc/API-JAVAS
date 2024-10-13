package com.example.CRUD.controller;
import com.example.CRUD.entity.ClienteEntity;
import com.example.CRUD.repository.ClienteRepository;
import com.example.CRUD.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteEntity>> listar() {
        List<ClienteEntity> cliente = clienteService.listarCliente();

        if(cliente.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(201).body(cliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> buscarPorIndice (@PathVariable int id) {
       return ResponseEntity.ok(clienteService.clientePorId(id));
    }

    @PostMapping
    public ResponseEntity<ClienteEntity> criar(@RequestBody ClienteEntity clienteNovo){
        return ResponseEntity.ok(clienteService.save(clienteNovo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteEntity> atualizar(@PathVariable Integer id, @RequestBody ClienteEntity clienteEntity){
        if(clienteRepository.existsById(id)) {
            clienteEntity.setIdCliente(id);
            return ResponseEntity.status(200).body(clienteRepository.save(clienteEntity));
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Integer id) {
        if(clienteRepository.existsById(id)) {
            clienteService.inativarCliente(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

}
