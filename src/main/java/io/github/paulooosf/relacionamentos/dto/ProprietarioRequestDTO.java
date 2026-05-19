package io.github.paulooosf.relacionamentos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProprietarioRequestDTO(

	    @NotBlank(message = "Nome é obrigatório")
	    String nome,

	    @Email(message = "Email inválido")
	    String email
) {}
