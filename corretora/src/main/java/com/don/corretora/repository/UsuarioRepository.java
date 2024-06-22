package com.don.corretora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.don.corretora.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
