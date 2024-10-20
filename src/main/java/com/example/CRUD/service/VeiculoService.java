package com.example.CRUD.service;

import com.example.CRUD.dto.veiculo.VeiculoDTO;
import com.example.CRUD.entity.VeiculoEntity;
import com.example.CRUD.exception.NaoEncontradoException;
import com.example.CRUD.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.example.CRUD.dto.veiculo.VeiculoMapper.toEntity;
import static com.example.CRUD.dto.veiculo.VeiculoMapper.toUpdateEntity;


@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoEntity save(VeiculoDTO veiculoDTO) {
        VeiculoEntity veiculo = toEntity(veiculoDTO);
        return veiculoRepository.save(veiculo);
    }

    public VeiculoEntity getById(Integer id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException(HttpStatus.NOT_FOUND, "Veículo não encontrado com id: " + id));
    }

    public VeiculoEntity atualizar(Integer id, VeiculoDTO veiculoDTO) {
        VeiculoEntity existingVeiculo = getById(id);
        toUpdateEntity(existingVeiculo, veiculoDTO);
        return veiculoRepository.save(existingVeiculo);
    }

    public void delete(Integer id) {
        VeiculoEntity veiculo = getById(id);
        veiculoRepository.delete(veiculo);
    }

}
