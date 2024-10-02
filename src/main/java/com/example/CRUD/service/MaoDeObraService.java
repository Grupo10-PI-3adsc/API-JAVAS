package com.example.CRUD.service;

import com.example.CRUD.dto.MaoDeObraDTO;
import com.example.CRUD.dto.ProdutoDTO;
import com.example.CRUD.entity.MaoDeObrEntity;
import com.example.CRUD.entity.ProdutoEntity;
import com.example.CRUD.repository.MaoDeObraRepository;
import com.example.CRUD.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.lang.model.element.NestingKind;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MaoDeObraService {

    @Autowired
    private MaoDeObraRepository maoDeObraRepository;


    public List<MaoDeObrEntity> listar() {
        return maoDeObraRepository.findAll();
    }

    public List<MaoDeObrEntity> pesquisarPorCliente(int id) {
        List<MaoDeObrEntity> listarPorCliente = maoDeObraRepository.findAllByFkCliente(id);
        if (listarPorCliente.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse cliente não serviços");
        }
        return listarPorCliente;
    }

    public MaoDeObrEntity servicoPorId(int id) {
        Optional<MaoDeObrEntity> serviceList = maoDeObraRepository.findById(id);
        if(serviceList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado");
        }
        return serviceList.get();
    }

    public MaoDeObrEntity adicionarServico(MaoDeObrEntity maoDeObrEntity, int id) {
        if (maoDeObraRepository.existsById(maoDeObrEntity.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conflito");
        }
        maoDeObrEntity.setId(null);
        maoDeObrEntity.setFkCliente(id);
        return maoDeObraRepository.save(maoDeObrEntity);
    }

    public MaoDeObrEntity atualizarServico(MaoDeObrEntity maoDeObrEntity,int id) {
        if(maoDeObraRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Esse cliente não possui serviços");
        }
        return maoDeObraRepository.save(maoDeObrEntity);
    }

    public MaoDeObrEntity cancelarServico(int fkCliente) {
        String cancelarServico = "Cancelado";

        Optional<MaoDeObrEntity> servico = maoDeObraRepository.findByFkCliente(fkCliente);
        if(servico.isPresent()) {
            servico.get().setStatus(cancelarServico);
            maoDeObraRepository.save(servico.get());
            return servico.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado");
    }




}
