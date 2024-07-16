package com.don.corretora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.don.corretora.model.Produto;

public interface ProdutoRepository  extends JpaRepository<Produto, Long>{
    
}
