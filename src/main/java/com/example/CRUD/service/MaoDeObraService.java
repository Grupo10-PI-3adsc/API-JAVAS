package com.example.CRUD.service;

import com.example.CRUD.dto.maoDeObra.MaoDeObraDTO;
import com.example.CRUD.dto.maoDeObra.MaoDeObraMapper;
import com.example.CRUD.dto.veiculo.VeiculoDTO;
import com.example.CRUD.entity.MaoDeObrEntity;
import com.example.CRUD.entity.VeiculoEntity;
import com.example.CRUD.exception.NaoEncontradoException;
import com.example.CRUD.repository.MaoDeObraRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MaoDeObraService {

    @Autowired
    private MaoDeObraRepository maoDeObraRepository;
    @Autowired
    private VeiculoService veiculoService;


    public List<MaoDeObrEntity> listar() {
        return maoDeObraRepository.findAll();
    }

    public List<MaoDeObrEntity> pesquisarPorCliente(int id) {
        List<MaoDeObrEntity> listarPorCliente = maoDeObraRepository.findAllByfkUser(id);
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

    public MaoDeObrEntity adicionarServico(MaoDeObrEntity maoDeObrEntity, Integer id) {
        VeiculoEntity veiculo = veiculoService.getById(id);
        if (maoDeObrEntity.getId() != null) {
            throw new IllegalArgumentException("O id deve ser nulo para ser salvo");
        }
        maoDeObrEntity.setId(null);
        maoDeObrEntity.setFkVeiculo(veiculo);
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

        Optional<MaoDeObrEntity> servico = maoDeObraRepository.findByfkUser(fkCliente);
        if(servico.isPresent()) {
            servico.get().setStatus(cancelarServico);
            maoDeObraRepository.save(servico.get());
            return servico.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado");
    }




}
