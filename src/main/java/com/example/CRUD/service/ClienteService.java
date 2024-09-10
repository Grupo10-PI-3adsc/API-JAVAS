package com.example.CRUD.service;
import com.example.CRUD.dto.ClienteDTO;
import com.example.CRUD.dto.EnderecoDTO;
import com.example.CRUD.entity.ClienteEntity;
import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.repository.ClienteRepository;
import com.example.CRUD.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public ClienteEntity save(ClienteDTO cleinteDTO) {

        ClienteEntity cliente = new ClienteEntity();

        cliente.setNome(cleinteDTO.getNome());
        cliente.setCpfCnpj(cleinteDTO.getCpfCnpj());
        cliente.setEmail(cleinteDTO.getEmail());
        cliente.setEndereco(cleinteDTO.getEndereco());
        cliente.setTelefone(cleinteDTO.getTelefone());
        cliente.setDataCadastro(cleinteDTO.getDataCadastro());

        return clienteRepository.save(cliente);
    }

    public ResponseEntity<EnderecoEntity> save(EnderecoDTO enderecoDTO) {

        EnderecoEntity enderecoEntity = new EnderecoEntity();
        Optional<ClienteEntity> clienteOpt = clienteRepository.findById(enderecoDTO.getFkCliente());

        if (!clienteOpt.isEmpty()){
            enderecoEntity.setBairro(enderecoDTO.getBairro());
            enderecoEntity.setCep(enderecoDTO.getCep());
            enderecoEntity.setComplemento(enderecoDTO.getComplemento());
            enderecoEntity.setDdd(enderecoDTO.getDdd());
            enderecoEntity.setGia(enderecoDTO.getGia());
            enderecoEntity.setIbge(enderecoDTO.getIbge());
            enderecoEntity.setLocalidade(enderecoDTO.getLocalidade());
            enderecoEntity.setLogradouro(enderecoDTO.getLogradouro());
            enderecoEntity.setSiafi(enderecoDTO.getSiafi());
            enderecoEntity.setUf(enderecoDTO.getUf());
            enderecoEntity.setFkCliente(enderecoDTO.getFkCliente());

            return ResponseEntity.status(201).body(enderecoRepository.save(enderecoEntity));
        }

        return ResponseEntity.status(404).build();
    }


}
