package io.github.paulooosf.relacionamentos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.paulooosf.relacionamentos.domain.Proprietario;
import io.github.paulooosf.relacionamentos.domain.Veiculo;
import io.github.paulooosf.relacionamentos.repository.ProprietarioRepository;
import io.github.paulooosf.relacionamentos.repository.VeiculoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Veículo", description = "Cadastro de Veículos")
@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository repository;
    
    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @GetMapping
    @Operation(summary = "Lista todos os veículos", description = "Retorna a lista completa de veículos cadastrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = Veiculo.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<List<Veiculo>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca veículo por ID", description = "Retorna um veículo pelo seu identificador.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo encontrado",
            content = @Content(schema = @Schema(implementation = Veiculo.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Veiculo> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastra um veículo", description = "Insere um novo veículo. Para vincular proprietário, informar apenas o id.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Veículo cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Veiculo> inserir(@RequestBody @Valid Veiculo veiculo) {
    	if (veiculo.getProprietario() != null && veiculo.getProprietario().getId() != null) {
            Proprietario prop = proprietarioRepository.findById(veiculo.getProprietario().getId())
                    .orElse(null);
            veiculo.setProprietario(prop);
        }
        return ResponseEntity.status(201).body(repository.save(veiculo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um veículo", description = "Atualiza os dados de um veículo existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Veiculo> atualizar(
            @PathVariable Long id, @RequestBody @Valid Veiculo veiculo) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        veiculo.setId(id);
        return ResponseEntity.ok(repository.save(veiculo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um veículo", description = "Remove um veículo pelo seu identificador.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Veículo removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
