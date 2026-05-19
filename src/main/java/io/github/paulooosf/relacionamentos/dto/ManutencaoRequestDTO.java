package io.github.paulooosf.relacionamentos.dto;

import java.time.LocalDate;
import java.util.List;

public record ManutencaoRequestDTO(
	    LocalDate dataEntrada,
	    LocalDate dataSaida,
	    String obs,
	    Long idVeiculo,
	    List<Long> idsServicos
	) {}
