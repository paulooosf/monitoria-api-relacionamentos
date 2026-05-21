package io.github.paulooosf.relacionamentos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.paulooosf.relacionamentos.domain.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
	
	// busca por marca (case insensitive)
    Page<Veiculo> findByMarcaContainingIgnoreCase(String marca, Pageable pageable);

    // busca por modelo
    Page<Veiculo> findByModeloContainingIgnoreCase(String modelo, Pageable pageable);

    // JPQL — busca veículos por nome do proprietário
    @Query("SELECT v FROM Veiculo v WHERE UPPER(v.proprietario.nome) LIKE UPPER(CONCAT('%', :nome, '%'))")
    Page<Veiculo> buscarPorNomeProprietario(@Param("nome") String nome, Pageable pageable);
}
