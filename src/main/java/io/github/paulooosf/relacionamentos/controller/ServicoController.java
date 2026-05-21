package io.github.paulooosf.relacionamentos.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.paulooosf.relacionamentos.domain.Servico;
import io.github.paulooosf.relacionamentos.dto.ServicoResumoDTO;
import io.github.paulooosf.relacionamentos.repository.ServicoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Serviço", description = "Cadastro de Serviços")
@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository repository;
    
 // GET simples — sem paginação
    @GetMapping
    @Operation(summary = "Lista todos os serviços")
    public ResponseEntity<List<Servico>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    // GET paginado — com valores default
    @GetMapping("/pagina")
    @Operation(summary = "Lista serviços paginados")
    public ResponseEntity<Page<Servico>> listarPaginado(
            @PageableDefault(sort = "descricao", direction = Sort.Direction.ASC, page = 0, size = 5)
            Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    // GET por faixa de valor — JPQL + paginação
    @GetMapping("/valor")
    @Operation(summary = "Filtra serviços por faixa de valor")
    public ResponseEntity<Page<Servico>> listarPorValor(
            @RequestParam(defaultValue = "0") BigDecimal valorMinimo,
            @RequestParam(defaultValue = "99999") BigDecimal valorMaximo,
            Pageable pageable) {
        return ResponseEntity.ok(repository.findByValorBetween(valorMinimo, valorMaximo, pageable));
    }

    // GET por descrição — JPQL LIKE + paginação
    @GetMapping("/buscar")
    @Operation(summary = "Busca serviços por descrição")
    public ResponseEntity<Page<Servico>> buscarPorDescricao(
            @RequestParam(defaultValue = "") String descricao,
            Pageable pageable) {
        return ResponseEntity.ok(repository.findByDescricaoContainingIgnoreCase(descricao, pageable));
    }

    // GET resumo por faixa — query nativa + interface DTO
    @GetMapping("/resumo")
    @Operation(summary = "Resumo de serviços agrupados por faixa de valor")
    public ResponseEntity<List<ServicoResumoDTO>> resumoPorFaixa() {
        return ResponseEntity.ok(repository.buscarResumoValores());
    }

    @PostMapping
    @Operation(summary = "Cadastra um serviço")
    public ResponseEntity<Servico> inserir(@RequestBody @Valid Servico servico) {
        return ResponseEntity.status(201).body(repository.save(servico));
    }

    @PostMapping("/lista")
    @Operation(summary = "Cadastra vários serviços")
    public ResponseEntity<List<Servico>> inserirVarios(@RequestBody List<Servico> lista) {
        return ResponseEntity.status(201).body(repository.saveAll(lista));
    }
}
