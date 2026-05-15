package io.github.paulooosf.relacionamentos.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Preencha a placa")
    @Size(max = 7, message = "Placa máximo {max} caracteres")
    @Column(nullable = false, length = 7)
    private String placa;

    @NotBlank(message = "Preencha a marca")
    @Column(nullable = false, length = 30)
    private String marca;

    @NotBlank(message = "Preencha o modelo")
    @Column(nullable = false, length = 40)
    private String modelo;

    @Embedded
    @Valid
    private Caracteristica caracteristica;

    // OneToOne — Veiculo tem a FK id_proprietario
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proprietario")
    private Proprietario proprietario;

    // OneToMany — um veiculo tem várias manutencoes
    @OneToMany(mappedBy = "veiculo")
    @JsonIgnore
    private List<Manutencao> manutencoes = new ArrayList<>();

    public Veiculo() {}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPlaca() {
		return placa;
	}


	public void setPlaca(String placa) {
		this.placa = placa;
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}


	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public Caracteristica getCaracteristica() {
		return caracteristica;
	}


	public void setCaracteristica(Caracteristica caracteristica) {
		this.caracteristica = caracteristica;
	}


	public Proprietario getProprietario() {
		return proprietario;
	}


	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}


	public List<Manutencao> getManutencoes() {
		return manutencoes;
	}


	public void setManutencoes(List<Manutencao> manutencoes) {
		this.manutencoes = manutencoes;
	}
}
