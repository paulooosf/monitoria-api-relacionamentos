package io.github.paulooosf.relacionamentos.dto;

import io.github.paulooosf.relacionamentos.domain.Proprietario;

public record ProprietarioResponseDTO(
	    Long id,
	    String nome,
	    String email
	) {
	    // construtor pra converter: Proprietario -> DTO
	    public ProprietarioResponseDTO(Proprietario p) {
	        this(p.getId(), p.getNome(), p.getEmail());
	    }
	}
