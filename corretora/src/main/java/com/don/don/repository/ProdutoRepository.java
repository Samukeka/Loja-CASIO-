package com.don.don.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query("SELECT DISTINCT p.cor FROM Produto p WHERE p.cor IS NOT NULL")
    List<String> findDistinctCores();

    @Query("SELECT DISTINCT p.serie FROM Produto p WHERE p.serie IS NOT NULL")
    List<String> findDistinctSeries();

    @Query("SELECT DISTINCT p.marca FROM Produto p WHERE p.marca IS NOT NULL")
    List<String> findDistinctMarcas();

    @Query("SELECT DISTINCT p.colecao FROM Produto p WHERE p.colecao IS NOT NULL")
    List<String> findDistinctColecoes();

    @Query("SELECT DISTINCT p.estilo FROM Produto p WHERE p.estilo IS NOT NULL")
    List<String> findDistinctEstilos();

}
