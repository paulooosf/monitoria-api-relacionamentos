package io.github.paulooosf.relacionamentos.dto;

import io.github.paulooosf.relacionamentos.domain.Caracteristica;
import io.github.paulooosf.relacionamentos.domain.Veiculo;

public record VeiculoResponseDTO(
	    Long id,
	    String placa,
	    String marca,
	    String modelo,
	    Caracteristica caracteristica,
	    String nomeProprietario
	) {
	    public VeiculoResponseDTO(Veiculo v) {
	        this(
	            v.getId(),
	            v.getPlaca(),
	            v.getMarca(),
	            v.getModelo(),
	            v.getCaracteristica(),
	            v.getProprietario() != null ? v.getProprietario().getNome() : null
	        );
	    }
}
