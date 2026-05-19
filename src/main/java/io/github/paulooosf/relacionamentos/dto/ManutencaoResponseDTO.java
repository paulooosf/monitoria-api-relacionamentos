package io.github.paulooosf.relacionamentos.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import io.github.paulooosf.relacionamentos.domain.Manutencao;
import io.github.paulooosf.relacionamentos.domain.Servico;

public record ManutencaoResponseDTO(
	    Long id,
	    LocalDate dataEntrada,
	    LocalDate dataSaida,
	    String obs,
	    String placaVeiculo,
	    List<String> servicos
	) {
	    public ManutencaoResponseDTO(Manutencao m) {
	        this(
	            m.getId(),
	            m.getDataEntrada(),
	            m.getDataSaida(),
	            m.getObs(),
	            m.getVeiculo() != null ? m.getVeiculo().getPlaca() : null,
	            m.getServicos() != null
	                ? m.getServicos().stream().map(Servico::getDescricao).collect(Collectors.toList())
	                : List.of()
	        );
	    }
	}
