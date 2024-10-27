package com.example.CRUD.service;

import com.example.CRUD.dto.veiculo.VeiculoDTO;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.entity.VeiculoEntity;
import com.example.CRUD.exception.NaoEncontradoException;
import com.example.CRUD.repository.UserRepository;
import com.example.CRUD.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final UserService userService;
    private final VeiculoRepository veiculoRepository;

    public VeiculoEntity cadastrar(VeiculoEntity veiculoEntity, int userId) {
        UserEntity user = userService.userPorId(userId);
        if (veiculoEntity.getId() != null) {
            throw new IllegalArgumentException("O id deve ser nulo para ser salvo");
        }
        veiculoEntity.setId(null);
        veiculoEntity.setFkUser(user);
        return veiculoRepository.save(veiculoEntity);
    }

    public List<VeiculoEntity> listar() {
        return veiculoRepository.findAll();
    }

    public VeiculoEntity getById(Integer id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException(HttpStatus.NOT_FOUND, "Veículo não encontrado com id: " + id));
    }

    public void delete(Integer id) {
        VeiculoEntity veiculo = getById(id);
        veiculoRepository.delete(veiculo);
    }

}
