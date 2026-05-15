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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

    @Autowired
    private ProprietarioRepository repository;

    @GetMapping
    public ResponseEntity<List<Proprietario>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Proprietario> inserir(@RequestBody @Valid Proprietario proprietario) {
        return ResponseEntity.status(201).body(repository.save(proprietario));
    }

    @PostMapping("/lista")
    public ResponseEntity<List<Proprietario>> inserirVarios(@RequestBody List<Proprietario> lista) {
        return ResponseEntity.status(201).body(repository.saveAll(lista));
    }
}
