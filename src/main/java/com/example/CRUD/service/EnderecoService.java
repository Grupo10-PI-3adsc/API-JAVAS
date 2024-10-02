package com.example.CRUD.service;

import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.RepositoryCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public EnderecoEntity save(EnderecoEntity novoEndereco) {
        if (enderecoRepository.existsById(novoEndereco.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Endereço já criado");
        }
        novoEndereco.setId(null);
        return enderecoRepository.save(novoEndereco);
    }

    public List<EnderecoEntity> listarEnderecos() {
        List<EnderecoEntity> listAddress = enderecoRepository.findAll();

        if (listAddress.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não a endereços cadastrados");
        }
        return listAddress;
    }

    public EnderecoEntity buscarPorId(int id) {
        Optional<EnderecoEntity> entityOptional = enderecoRepository.findById(id);

        if(entityOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Endereço não encontrado!");
        }
        return entityOptional.get();
    }

    public List<EnderecoEntity> listEnderecoPorCliente(int id) {
        List<EnderecoEntity> listAddress = enderecoRepository.findAllByFkCliente(id);
        if (listAddress.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse cliente não tem endereço cadastrado");
        }
        return listAddress;
    }

    public Optional<EnderecoEntity> delAddress(int id) {
        Optional<EnderecoEntity> listAddress = enderecoRepository.findById(id);
        if(listAddress.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }
        enderecoRepository.deleteById(id);
        return listAddress;
    }





}
