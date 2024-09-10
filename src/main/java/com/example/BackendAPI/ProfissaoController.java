package com.example.BackendAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profissoes")
public class ProfissaoController {

    @Autowired
    private ProfissaoRepository profissaoRepository;
    @Autowired
    private ProfissaoService profissaoService;


    @GetMapping
    public List<ProfissaoEntity> listar() {
        return profissaoRepository.findAll();
    }
    @GetMapping("/{id}")
    public ProfissaoEntity buscarPorIndice (@PathVariable int id) {
        Optional<ProfissaoEntity> profissaoOpt = profissaoRepository.findById(id);
        return profissaoRepository.findById(id).orElse(null);
    }
    
    @PostMapping
    public ProfissaoEntity criar(@RequestBody ProfissaoDTO profissaoDTO){

        ProfissaoEntity profissaoEntity = profissaoService.save(profissaoDTO);
        return profissaoRepository.save(profissaoEntity);
    }
//    @PostMapping()
//    public ProfissaoEntity criar(@RequestBody ProfissaoEntity profissao)
//    {
//        return profissaoRepository.save(profissao);
//    }

    @PutMapping("/{id}")
    public ProfissaoEntity atualizar(@PathVariable Integer id, @RequestBody ProfissaoEntity profissaoEntity){
        if(profissaoRepository.existsById(id)) {
            profissaoEntity.setId(id);
            return profissaoEntity;
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public String deletarMusica(@PathVariable Integer id) {
        if(profissaoRepository.existsById(id)) {
            profissaoRepository.deleteById(id);
            return "Deletado com sucesso";
        }
        return "Não encontrado";
    }



}
