package com.example.CRUD.service;
import com.example.CRUD.entity.ClienteEntity;
import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.repository.ClienteRepository;
import com.example.CRUD.repository.EnderecoRepository;
//import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public ClienteEntity save(ClienteEntity cliente) {
        if(clienteRepository.existsById(cliente.getIdCliente())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cliente já cadastrado!");
        }
        cliente.setIdCliente(null);
        return clienteRepository.save(cliente);
    }

    public List<ClienteEntity> listarCliente() {
        return clienteRepository.findAll();
    }

    public ClienteEntity clientePorId(int id) {
        Optional<ClienteEntity> clienteEntityOptional = clienteRepository.findById(id);

        if(clienteEntityOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return clienteEntityOptional.get();
    }


    public List<ClienteEntity> clientePorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public ClienteEntity atualizarCliente(ClienteEntity cliente, int id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Cliente não encontrado");
        }
        return clienteRepository.save(cliente);
    }


    public ClienteEntity inativarCliente(int id) {
        Boolean ativo = false;
        Optional<ClienteEntity> cliente = clienteRepository.findById(id);
        Optional<EnderecoEntity> endereco = enderecoRepository.findByFkCliente(id);

        if (cliente.isPresent()) {
            endereco.ifPresent(enderecoEntity -> enderecoEntity.setIsActive(ativo));
            cliente.get().setIsActive(ativo);
            clienteRepository.save(cliente.get());
            endereco.get().setIsActive(ativo);
            return cliente.get();
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Cliente não encontrado ou Endereço");

    }
//
//    public ClienteEntity login(String email, String senha) {
////        Key key = Keys.secretKeyFor();
//    }


}
