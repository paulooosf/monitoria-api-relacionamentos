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

import io.github.paulooosf.relacionamentos.dto.ProprietarioRequestDTO;
import io.github.paulooosf.relacionamentos.dto.ProprietarioResponseDTO;
import io.github.paulooosf.relacionamentos.service.ProprietarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Proprietário", description = "Cadastro de Proprietários")
@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

	@Autowired
    private ProprietarioService service;

    @GetMapping
    @Operation(summary = "Lista todos os proprietários", description = "Retorna a lista completa de proprietários cadastrados.")
    public ResponseEntity<List<ProprietarioResponseDTO>> listar() {
    	return ResponseEntity.ok(service.listar());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Busca proprietário por ID")
    public ResponseEntity<ProprietarioResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra um proprietário", description = "Insere um novo proprietário no sistema.")
    public ResponseEntity<ProprietarioResponseDTO> inserir(@RequestBody @Valid ProprietarioRequestDTO dto) {
    	ProprietarioResponseDTO response = service.inserir(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um proprietário")
    public ResponseEntity<ProprietarioResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ProprietarioRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um proprietário")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
