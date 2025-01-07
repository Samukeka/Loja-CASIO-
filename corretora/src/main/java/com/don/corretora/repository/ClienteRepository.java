package com.don.corretora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.don.corretora.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByNome(String nome);

    boolean existsByNome(String nome);
}
