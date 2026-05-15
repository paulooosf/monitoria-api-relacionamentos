package io.github.paulooosf.relacionamentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.paulooosf.relacionamentos.domain.Proprietario;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {}
