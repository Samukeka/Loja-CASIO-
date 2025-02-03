package com.don.don.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.don.don.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByEmail(String email);

    boolean existsByEmail(String email);
}
