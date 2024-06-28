package com.don.corretora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.don.corretora.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
    
    Optional<Funcionario> findByEmail(String email);
    
}
