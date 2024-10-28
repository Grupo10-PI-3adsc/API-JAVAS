package com.example.CRUD.controller;

import com.example.CRUD.dto.endereco.EnderecoMapper;
import com.example.CRUD.dto.endereco.EnderecoResponseDto;
import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.repository.EnderecoRepository;
import com.example.CRUD.service.EnderecoService;
import com.gtbr.ViaCepClient;
import com.gtbr.domain.Cep;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Operation(description = "Cadastra um novo endereço usando o CEP e ID do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "CEP não encontrado")
    })
    @PostMapping("/{id}")
    public ResponseEntity<EnderecoResponseDto> cadastrarEndereco(
            @RequestParam String cep,
            @RequestParam Integer numero
    ) {
        Cep viaCep = ViaCepClient.findCep(cep);
        EnderecoEntity endereco = EnderecoMapper.toEntity(viaCep);
        EnderecoEntity novoEndereco = enderecoService.save(endereco, numero);
        EnderecoResponseDto responseDto = EnderecoMapper.toDto(novoEndereco);
        return ResponseEntity.created(null).body(responseDto);
    }

    @Operation(description = "Lista todos os endereços cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum endereço cadastrado")
    })
    @GetMapping()
    public ResponseEntity<List<EnderecoEntity>> listarEnderecos() {
        List<EnderecoEntity> listAddress = enderecoService.listarEnderecos();
        if (listAddress.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listAddress);
    }

    @Operation(description = "Busca um endereço por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoEntity> buscarPorId(@PathVariable int id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    @Operation(description = "Desativa um endereço pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @PutMapping("/inativar-endereco/{id}")
    public ResponseEntity<EnderecoEntity> desativarEnderecoPorId(@PathVariable int id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    @Operation(description = "Ordena e lista os endereços por localidade em ordem alfabética")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de endereços ordenada retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum endereço cadastrado")
    })
    @GetMapping("/ordenado")
    public ResponseEntity<List<EnderecoEntity>> InsertSort() {
        List<EnderecoEntity> vetor = enderecoRepository.findAll();

        if (!vetor.isEmpty()) {
            for (int i = 1; i < vetor.size(); i++) {
                EnderecoEntity x = vetor.get(i);
                int j = i - 1;

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
}
