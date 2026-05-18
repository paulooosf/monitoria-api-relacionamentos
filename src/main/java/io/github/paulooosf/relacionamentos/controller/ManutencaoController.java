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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Manutenção", description = "Cadastro de Manutenções")
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
    @Operation(summary = "Lista todas as manutenções", description = "Retorna a lista completa de manutenções cadastradas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = Manutencao.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<List<Manutencao>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca manutenção por ID", description = "Retorna uma manutenção pelo seu identificador.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Manutenção encontrada"),
        @ApiResponse(responseCode = "404", description = "Manutenção não encontrada"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Manutencao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastra uma manutenção", description = "Insere uma nova manutenção. Para vincular veículo e serviços, informar apenas os ids.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Manutenção cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
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
