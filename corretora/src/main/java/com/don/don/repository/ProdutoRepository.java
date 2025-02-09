package com.don.don.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.don.don.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByNome(String nome);

    List<Produto> findByPreco(BigDecimal preco);

    List<Produto> findByCor(String cor);

    List<Produto> findBySerie(String serie);

    List<Produto> findByColecao(String colecao);

    List<Produto> findByMarca(String marca);

    List<Produto> findByEstilo(String estilo);

    List<Produto> findByDestaqueTrue();

    List<Produto> findByMaisVendidoTrue();

    List<Produto> findByDescontoTrue();

    List<Produto> findByNovidadeTrue();

    List<Produto> findBySessao(String sessao);

}
