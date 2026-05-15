package io.github.paulooosf.relacionamentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.paulooosf.relacionamentos.domain.Manutencao;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {}
