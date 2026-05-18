package io.github.paulooosf.relacionamentos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.paulooosf.relacionamentos.domain.Proprietario;
import io.github.paulooosf.relacionamentos.repository.ProprietarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@Tag(name = "Proprietário", description = "Cadastro de Proprietários")
@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

    @Autowired
    private ProprietarioRepository repository;

    @GetMapping
    @Operation(summary = "Lista todos os proprietários", description = "Retorna a lista completa de proprietários cadastrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = Proprietario.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<List<Proprietario>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    @Operation(summary = "Cadastra um proprietário", description = "Insere um novo proprietário no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Proprietário cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Proprietario> inserir(@RequestBody @Valid Proprietario proprietario) {
        return ResponseEntity.status(201).body(repository.save(proprietario));
    }

    @PostMapping("/lista")
    @Operation(summary = "Cadastra vários proprietários", description = "Insere uma lista de proprietários de uma vez.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Proprietários cadastrados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<List<Proprietario>> inserirVarios(@RequestBody List<Proprietario> lista) {
        return ResponseEntity.status(201).body(repository.saveAll(lista));
    }
}
