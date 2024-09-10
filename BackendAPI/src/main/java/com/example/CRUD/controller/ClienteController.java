package com.example.CRUD.controller;

import com.example.CRUD.dto.EnderecoDTO;
import com.example.CRUD.entity.ClienteEntity;
import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.repository.ClienteRepository;
import com.example.CRUD.repository.EnderecoRepository;
import com.example.CRUD.service.ClienteService;
import com.example.CRUD.dto.ClienteDTO;
import com.gtbr.ViaCepClient;
import com.gtbr.domain.Cep;
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
    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping
    public ResponseEntity<List<ClienteEntity>> listar() {
        List<ClienteEntity> cliente = clienteRepository.findAll();

        if(cliente.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(201).body(cliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> buscarPorIndice (@PathVariable int id) {
        Optional<ClienteEntity> clienteOpt = clienteRepository.findById(id);
        if(clienteOpt.isPresent()) {
            return ResponseEntity.status(201).body(clienteOpt.get());
        }
        return ResponseEntity.status(204).build();
    }
    
    @PostMapping
    public ResponseEntity<ClienteEntity> criar(@RequestBody ClienteDTO clienteNovo){
        clienteNovo.setId(null);
        return ResponseEntity.status(201).body(clienteService.save(clienteNovo));
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
            deletaEndereco(id);
            clienteRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

//    @DeleteMapping("/enderecos/{fkCliente}")
    public ResponseEntity<ResponseEntity<EnderecoEntity>> deletaEndereco(
            @PathVariable Integer fkCliente
    ) {
        List<EnderecoEntity> enderecos = enderecoRepository.findAllByFkCliente(fkCliente);
        if (!enderecos.isEmpty()){
            for (int i = 0; i < enderecos.size(); i++) {
                enderecoRepository.deleteById(enderecos.get(i).getId_endereco());
                return ResponseEntity.status(204).build();
            }
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/enderecos/{fkCliente}")
    public ResponseEntity<ResponseEntity<Void>> deletaEndereco(
            @RequestParam String cep,
            @PathVariable Integer fkCliente
    ) {
        Optional<EnderecoEntity> enderecos = enderecoRepository.findByFkClienteAndCep(fkCliente, cep);
        if (enderecos.isPresent()){
            enderecoRepository.deleteById(enderecos.get().getId_endereco());
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
//
    @PutMapping("/enderecos/{fkCliente}")
    public ResponseEntity<EnderecoEntity> atualizarEndereco(
            @PathVariable int fkCliente,
            @RequestBody EnderecoEntity enderecoAtualziado

    ) {
        enderecoAtualziado.setFkCliente(fkCliente);
        Optional<EnderecoEntity> enderecos = enderecoRepository.findByFkClienteAndCep(fkCliente, enderecoAtualziado.getCep());
        enderecoAtualziado.setId_endereco(enderecos.get().getId_endereco());
        if (enderecos.isPresent()){

            return ResponseEntity.status(200).body(enderecoRepository.save(enderecoAtualziado));
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping("/enderecos/{fkCliente}")
    public ResponseEntity<ResponseEntity<EnderecoEntity>> buscaEndereco(
            @RequestParam String cep,
            @PathVariable Integer fkCliente
    ) {
        Cep viaCep = ViaCepClient.findCep(cep);

        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setBairro(viaCep.getBairro());
        enderecoDTO.setCep(viaCep.getCep());
        enderecoDTO.setComplemento(viaCep.getComplemento());
        enderecoDTO.setDdd(viaCep.getDdd());
        enderecoDTO.setGia(viaCep.getGia());
        enderecoDTO.setIbge(viaCep.getIbge());
        enderecoDTO.setLocalidade(viaCep.getLocalidade());
        enderecoDTO.setLogradouro(viaCep.getLogradouro());
        enderecoDTO.setSiafi(viaCep.getSiafi());
        enderecoDTO.setUf(viaCep.getUf());
        enderecoDTO.setFkCliente(fkCliente);

        if (enderecoDTO.equals(null)) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(201).body(clienteService.save(enderecoDTO));
    }

    @GetMapping("/enderecos/ordenado")
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

//    @GetMapping("/enderecos")
//    public ResponseEntity<List<EnderecoEntity>> buscar() {
//
//        List<EnderecoEntity> vetor = enderecoRepository.buscaJoinEnderecoCliente();
//
//        if (!vetor.isEmpty()) {
//            return ResponseEntity.status(200).body(vetor);
//        }
//        return ResponseEntity.status(204).build();
//    }



}
