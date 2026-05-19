package io.github.paulooosf.relacionamentos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.paulooosf.relacionamentos.domain.Veiculo;
import io.github.paulooosf.relacionamentos.dto.VeiculoRequestDTO;
import io.github.paulooosf.relacionamentos.dto.VeiculoResponseDTO;
import io.github.paulooosf.relacionamentos.repository.ProprietarioRepository;
import io.github.paulooosf.relacionamentos.repository.VeiculoRepository;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    public List<VeiculoResponseDTO> listar() {
        return veiculoRepository.findAll()
                .stream()
                .map(VeiculoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public VeiculoResponseDTO buscar(Long id) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo " + id + " não encontrado"));
        return new VeiculoResponseDTO(veiculo);
    }

    public VeiculoResponseDTO inserir(VeiculoRequestDTO dto) {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(dto.placa());
        veiculo.setMarca(dto.marca());
        veiculo.setModelo(dto.modelo());
        veiculo.setCaracteristica(dto.caracteristica());

        if (dto.idProprietario() != null) {
            proprietarioRepository.findById(dto.idProprietario())
                    .ifPresent(veiculo::setProprietario);
        }

        return new VeiculoResponseDTO(veiculoRepository.save(veiculo));
    }

    public VeiculoResponseDTO atualizar(Long id, VeiculoRequestDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo " + id + " não encontrado"));
        veiculo.setPlaca(dto.placa());
        veiculo.setMarca(dto.marca());
        veiculo.setModelo(dto.modelo());
        veiculo.setCaracteristica(dto.caracteristica());

        if (dto.idProprietario() != null) {
            proprietarioRepository.findById(dto.idProprietario())
                    .ifPresent(veiculo::setProprietario);
        }

        return new VeiculoResponseDTO(veiculoRepository.save(veiculo));
    }

    public void deletar(Long id) {
        if (!veiculoRepository.existsById(id)) {
            throw new RuntimeException("Veículo " + id + " não encontrado");
        }
        veiculoRepository.deleteById(id);
    }
}
