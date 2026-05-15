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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository repository;

    @GetMapping
    public ResponseEntity<List<Servico>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Servico> inserir(@RequestBody @Valid Servico servico) {
        return ResponseEntity.status(201).body(repository.save(servico));
    }

    @PostMapping("/lista")
    public ResponseEntity<List<Servico>> inserirVarios(@RequestBody List<Servico> lista) {
        return ResponseEntity.status(201).body(repository.saveAll(lista));
    }
}
