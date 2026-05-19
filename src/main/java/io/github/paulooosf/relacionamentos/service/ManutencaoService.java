package io.github.paulooosf.relacionamentos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.paulooosf.relacionamentos.domain.Manutencao;
import io.github.paulooosf.relacionamentos.domain.Servico;
import io.github.paulooosf.relacionamentos.dto.ManutencaoRequestDTO;
import io.github.paulooosf.relacionamentos.dto.ManutencaoResponseDTO;
import io.github.paulooosf.relacionamentos.repository.ManutencaoRepository;
import io.github.paulooosf.relacionamentos.repository.ServicoRepository;
import io.github.paulooosf.relacionamentos.repository.VeiculoRepository;

@Service
public class ManutencaoService {

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    public List<ManutencaoResponseDTO> listar() {
        return manutencaoRepository.findAll()
                .stream()
                .map(ManutencaoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ManutencaoResponseDTO buscar(Long id) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manutenção " + id + " não encontrada"));
        return new ManutencaoResponseDTO(manutencao);
    }

    public ManutencaoResponseDTO inserir(ManutencaoRequestDTO dto) {
        Manutencao manutencao = new Manutencao();
        manutencao.setDataEntrada(dto.dataEntrada());
        manutencao.setDataSaida(dto.dataSaida());
        manutencao.setObs(dto.obs());

        if (dto.idVeiculo() != null) {
            veiculoRepository.findById(dto.idVeiculo())
                    .ifPresent(manutencao::setVeiculo);
        }

        if (dto.idsServicos() != null) {
            List<Servico> servicos = servicoRepository.findAllById(dto.idsServicos());
            manutencao.setServicos(servicos);
        }

        return new ManutencaoResponseDTO(manutencaoRepository.save(manutencao));
    }
}