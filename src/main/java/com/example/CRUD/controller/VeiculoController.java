package com.example.CRUD.controller;

import com.example.CRUD.dto.veiculo.VeiculoDTO;
import com.example.CRUD.dto.veiculo.VeiculoMapper;
import com.example.CRUD.dto.veiculo.VeiculoResponseDto;
import com.example.CRUD.entity.VeiculoEntity;
import com.example.CRUD.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    @Operation(description = "Cadastra um novo veículo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Veículo cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro")
    })
    @PostMapping
    public ResponseEntity<VeiculoResponseDto> cadastrar(@RequestBody @Valid VeiculoDTO veiculoDTO) {
        VeiculoEntity veiculo = VeiculoMapper.toEntity(veiculoDTO);
        VeiculoEntity novoVeiculo = veiculoService.cadastrar(veiculo, veiculoDTO.getFkUser());
        VeiculoResponseDto responseDto = VeiculoMapper.toDTO(novoVeiculo);
        return ResponseEntity.created(null).body(responseDto);
    }

    @Operation(description = "Busca um veículo pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículo encontrado"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoEntity> getVeiculoById(@PathVariable Integer id) {
        VeiculoEntity veiculo = veiculoService.getById(id);
        if (veiculo != null) {
            return ResponseEntity.ok().body(veiculo);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(description = "Lista todos os veículos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de veículos retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum veículo cadastrado")
    })
    @GetMapping
    public ResponseEntity<List<VeiculoResponseDto>> listar() {
        List<VeiculoEntity> veiculoEntityList = veiculoService.listar();
        if (veiculoEntityList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<VeiculoResponseDto> responseDtos = veiculoEntityList.stream()
                .map(VeiculoMapper::toDTO)
                .toList();

        return ResponseEntity.ok(responseDtos);
    }

    @Operation(description = "Exclui um veículo pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeiculo(@PathVariable Integer id) {
        if (veiculoService.getById(id) != null) {
            veiculoService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}
