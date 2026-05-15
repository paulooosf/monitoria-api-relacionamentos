package io.github.paulooosf.relacionamentos.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.paulooosf.relacionamentos.domain.Manutencao;
import io.github.paulooosf.relacionamentos.domain.Servico;
import io.github.paulooosf.relacionamentos.domain.Veiculo;
import io.github.paulooosf.relacionamentos.repository.ManutencaoRepository;
import io.github.paulooosf.relacionamentos.repository.ServicoRepository;
import io.github.paulooosf.relacionamentos.repository.VeiculoRepository;

@RestController
@RequestMapping("/manutencoes")
public class ManutencaoController {

    @Autowired
    private ManutencaoRepository repository;
    
    @Autowired
    private VeiculoRepository veiculoRepository;
    
    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping
    public ResponseEntity<List<Manutencao>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manutencao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Manutencao> inserir(@RequestBody Manutencao manutencao) {
    	
    	if (manutencao.getVeiculo() != null && manutencao.getVeiculo().getId() != null) {
            Veiculo veiculo = veiculoRepository.findById(manutencao.getVeiculo().getId())
                    .orElse(null);
            manutencao.setVeiculo(veiculo);
        }
    	
    	if (manutencao.getServicos() != null) {
            List<Servico> servicosCompletos = manutencao.getServicos().stream()
                    .filter(s -> s.getId() != null)
                    .map(s -> servicoRepository.findById(s.getId()).orElse(null))
                    .filter(s -> s != null)
                    .collect(Collectors.toList());
            manutencao.setServicos(servicosCompletos);
        }
    	
        return ResponseEntity.status(201).body(repository.save(manutencao));
    }
}
