package io.github.paulooosf.relacionamentos.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.paulooosf.relacionamentos.dto.ManutencaoRequestDTO;
import io.github.paulooosf.relacionamentos.dto.ManutencaoResponseDTO;
import io.github.paulooosf.relacionamentos.service.ManutencaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Manutenção", description = "Cadastro de Manutenções")
@RestController
@RequestMapping("/manutencoes")
public class ManutencaoController {

	@Autowired
    private ManutencaoService service;

    @GetMapping
    @Operation(summary = "Lista todas as manutenções")
    public ResponseEntity<List<ManutencaoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca manutenção por ID")
    public ResponseEntity<ManutencaoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }
    
    @PostMapping
    @Operation(summary = "Cadastra uma manutenção")
    public ResponseEntity<ManutencaoResponseDTO> inserir(@RequestBody ManutencaoRequestDTO dto) {
        ManutencaoResponseDTO response = service.inserir(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    /*
    @PostMapping
    @Operation(summary = "Cadastra uma manutenção", description = "Insere uma nova manutenção. Para vincular veículo e serviços, informar apenas os ids.")
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
    */
}
