package io.github.paulooosf.relacionamentos.controller;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.paulooosf.relacionamentos.dto.VeiculoRequestDTO;
import io.github.paulooosf.relacionamentos.dto.VeiculoResponseDTO;
import io.github.paulooosf.relacionamentos.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Veículo", description = "Cadastro de Veículos")
@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService service;

    @GetMapping
    @Operation(summary = "Lista todos os veículos", description = "Retorna a lista completa de veículos cadastrados.")
    public ResponseEntity<List<VeiculoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca veículo por ID")
    public ResponseEntity<VeiculoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra um veículo", description = "Recebe um VeiculoRequestDTO. Para vincular proprietário, informar idProprietario.")
    public ResponseEntity<VeiculoResponseDTO> inserir(@RequestBody @Valid VeiculoRequestDTO dto) {
        VeiculoResponseDTO response = service.inserir(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um veículo")
    public ResponseEntity<VeiculoResponseDTO> atualizar(
            @PathVariable Long id, @RequestBody @Valid VeiculoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um veículo")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
