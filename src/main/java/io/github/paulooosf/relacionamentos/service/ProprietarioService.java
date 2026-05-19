package io.github.paulooosf.relacionamentos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.paulooosf.relacionamentos.domain.Proprietario;
import io.github.paulooosf.relacionamentos.dto.ProprietarioRequestDTO;
import io.github.paulooosf.relacionamentos.dto.ProprietarioResponseDTO;
import io.github.paulooosf.relacionamentos.repository.ProprietarioRepository;

@Service
public class ProprietarioService {

    @Autowired
    private ProprietarioRepository repository;

    public List<ProprietarioResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(ProprietarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ProprietarioResponseDTO buscar(Long id) {
        Proprietario proprietario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proprietário " + id + " não encontrado"));
        return new ProprietarioResponseDTO(proprietario);
    }

    public ProprietarioResponseDTO inserir(ProprietarioRequestDTO dto) {
        Proprietario proprietario = new Proprietario(dto);
        return new ProprietarioResponseDTO(repository.save(proprietario));
    }

    public ProprietarioResponseDTO atualizar(Long id, ProprietarioRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Proprietário " + id + " não encontrado");
        }
        Proprietario proprietario = new Proprietario();
        proprietario.setId(id);
        proprietario.setNome(dto.nome());
        proprietario.setEmail(dto.email());
        return new ProprietarioResponseDTO(repository.save(proprietario));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Proprietário " + id + " não encontrado");
        }
        repository.deleteById(id);
    }
}
