package io.github.paulooosf.relacionamentos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.paulooosf.relacionamentos.dto.ProprietarioRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Proprietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do proprietário")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome completo do proprietário", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @Email(message = "Email inválido")
    @Schema(description = "Email do proprietário", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    // lado inverso — opcional, só pra navegação
    @OneToOne(mappedBy = "proprietario")
    @JsonIgnore
    private Veiculo veiculo;

    public Proprietario() {}
    
    public Proprietario(ProprietarioRequestDTO dto) {
    	this.nome = dto.nome();
    	this.email = dto.email();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
}
