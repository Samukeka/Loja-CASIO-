package com.don.corretora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.don.corretora.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByNome(String nome);

    boolean existsByNome(String nome);
}
