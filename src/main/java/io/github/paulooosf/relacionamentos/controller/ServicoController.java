package io.github.paulooosf.relacionamentos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.paulooosf.relacionamentos.domain.Servico;
import io.github.paulooosf.relacionamentos.repository.ServicoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Serviço", description = "Cadastro de Serviços")
@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository repository;
    
    @GetMapping
    @Operation(summary = "Lista todos os serviços", description = "Retorna a lista completa de serviços disponíveis.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = Servico.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<List<Servico>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    @Operation(summary = "Cadastra um serviço", description = "Insere um novo serviço disponível para manutenções.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Servico> inserir(@RequestBody @Valid Servico servico) {
        return ResponseEntity.status(201).body(repository.save(servico));
    }

    @PostMapping("/lista")
    @Operation(summary = "Cadastra vários serviços", description = "Insere uma lista de serviços de uma vez.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Serviços cadastrados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<List<Servico>> inserirVarios(@RequestBody List<Servico> lista) {
        return ResponseEntity.status(201).body(repository.saveAll(lista));
    }
}
