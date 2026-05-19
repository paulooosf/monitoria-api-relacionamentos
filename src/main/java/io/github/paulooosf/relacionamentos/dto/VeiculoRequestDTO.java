package io.github.paulooosf.relacionamentos.dto;

import io.github.paulooosf.relacionamentos.domain.Caracteristica;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VeiculoRequestDTO(

	    @NotBlank(message = "Preencha a placa")
	    @Size(max = 7, message = "Placa máximo {max} caracteres")
	    String placa,

	    @NotBlank(message = "Preencha a marca")
	    String marca,

	    @NotBlank(message = "Preencha o modelo")
	    String modelo,

	    Caracteristica caracteristica,
	    Long idProprietario

	) {}
