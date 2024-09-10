package com.example.BackendAPI;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProfissaoService {

    @Autowired
    private ProfissaoRepository profissaoRepository;

    public ProfissaoEntity save(ProfissaoDTO profissaoDTO) {

        ProfissaoEntity profissao = new ProfissaoEntity();

//        profissao.setId(profissaoDTO.getId());
        profissao.setNome(profissaoDTO.getNome());
        profissao.setCargaHoraria(profissaoDTO.getCargaHoraria());
        profissao.setSalario(profissaoDTO.getSalario());

        return profissaoRepository.save(profissao);
    }

//    ProfissaoEntity findById(Integer id);
//    List<ProfissaoEntity> findAll();
//    ProfissaoEntity create(ProfissaoDTO obj);
//    ProfissaoEntity update(ProfissaoDTO obj);
//    ProfissaoEntity delete(Integer id);


}
