package com.don.corretora.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "Produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal preco;

    private String marca;

    private String serie;

    private String colecao;

    private String cor;

    private String estilo;

    private int quantidade_estoque;

    private String descricao;

    private String status;

    @ElementCollection
    @CollectionTable(name = "imagens_produto", joinColumns = @JoinColumn(name = "id_produto"))
    @Column(name = "imagem")
    private List<String> imagens = new ArrayList<>();

    @Column(name = "imagem_principal")
    private String imagemPadrao;

    private Boolean destaque;

    private Boolean maisVendido;
    
    private Boolean desconto;

    private Boolean novidade;



    public Boolean getNovidade() {
        return novidade;
    }

    public void setNovidade(Boolean novidade) {
        this.novidade = novidade;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getImagens() {
        return imagens;
    }

    public void setImagens(List<String> imagens) {
        this.imagens = imagens;
    }

    public String getImagemPadrao() {
        return imagemPadrao;
    }

    public void setImagemPadrao(String imagemPadrao) {
        this.imagemPadrao = imagemPadrao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getColecao() {
        return colecao;
    }

    public void setColecao(String colecao) {
        this.colecao = colecao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getQuantidade_estoque() {
        return quantidade_estoque;
    }

    public void setQuantidade_estoque(int quantidade_estoque) {
        this.quantidade_estoque = quantidade_estoque;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getDestaque() {
        return destaque;
    }

    public void setDestaque(Boolean destaque) {
        this.destaque = destaque;
    }

    public Boolean getMaisVendido() {
        return maisVendido;
    }

    public void setMaisVendido(Boolean maisVendido) {
        this.maisVendido = maisVendido;
    }

    public Boolean getDesconto() {
        return desconto;
    }

    public void setDesconto(Boolean desconto) {
        this.desconto = desconto;
    }


    
}
