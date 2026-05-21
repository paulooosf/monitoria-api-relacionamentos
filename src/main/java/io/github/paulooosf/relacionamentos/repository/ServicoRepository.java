package io.github.paulooosf.relacionamentos.repository;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.paulooosf.relacionamentos.domain.Servico;
import io.github.paulooosf.relacionamentos.dto.ServicoResumoDTO;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
	
	// JPQL — busca por faixa de valor
    @Query("SELECT s FROM Servico s WHERE s.valor >= :valorMinimo AND s.valor <= :valorMaximo")
    Page<Servico> buscarPorValor(
            @Param("valorMinimo") BigDecimal valorMinimo,
            @Param("valorMaximo") BigDecimal valorMaximo,
            Pageable pageable);

    // JPQL — busca por trecho do nome (case insensitive)
    @Query("SELECT s FROM Servico s WHERE UPPER(s.descricao) LIKE UPPER(CONCAT('%', :descricao, '%'))")
    Page<Servico> buscarPorDescricao(
            @Param("descricao") String descricao,
            Pageable pageable);

    // Query Method — Spring gera o SQL automaticamente
    Page<Servico> findByValorBetween(BigDecimal valorMinimo, BigDecimal valorMaximo, Pageable pageable);
    Page<Servico> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);

    // Query Nativa — relatório de serviços por faixa de valor
    @Query(value = """
            SELECT
                CASE
                    WHEN valor < 100  THEN 'Até R$100'
                    WHEN valor < 300  THEN 'R$100 a R$300'
                    ELSE 'Acima de R$300'
                END as faixa,
                COUNT(*) as totalServicos,
                AVG(valor) as mediaValor,
                MIN(valor) as menorValor,
                MAX(valor) as maiorValor
            FROM servico
            GROUP BY faixa
            ORDER BY menorValor
            """, nativeQuery = true)
    List<ServicoResumoDTO> buscarResumoValores();
}
