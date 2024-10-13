package com.example.CRUD.service;

import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.repository.EnderecoRepository;
import com.example.CRUD.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.RepositoryCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UserRepository userRepository;

    public EnderecoEntity save(EnderecoEntity novoEndereco, Integer idUser) {
        Optional<EnderecoEntity> enderecoOpt = enderecoRepository.findByCep(novoEndereco.getCep());

        if (enderecoOpt.isPresent()) {

            Optional<UserEntity> user = userRepository.findById(idUser);
            if(user.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            if (user.get().getFkEndereco().getId() == enderecoOpt.get().getId()) {

                throw new ResponseStatusException(HttpStatus.CONFLICT, ("Usuario já possui esse endereço"));

            }

            user.get().setFkEndereco(enderecoOpt.get());

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

//    public List<EnderecoEntity> listEnderecoPorCliente(int id) {
//        List<EnderecoEntity> listAddress = enderecoRepository.findAllByFkUser(id);
//        if (listAddress.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse cliente não tem endereço cadastrado");
//        }
//        return listAddress;
//    }

    public Optional<EnderecoEntity> delAddress(int id) {
        Optional<EnderecoEntity> listAddress = enderecoRepository.findById(id);
        if(listAddress.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }
        enderecoRepository.deleteById(id);
        return listAddress;
    }





}
