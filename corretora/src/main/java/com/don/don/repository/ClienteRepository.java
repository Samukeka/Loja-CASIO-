package com.don.don.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.don.don.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByNome(String nome);

    Optional<Cliente> findByEmail(String email);

    boolean existsByNome(String nome);
}
