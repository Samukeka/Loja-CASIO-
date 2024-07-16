package com.don.corretora.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class ProdutoDto {

    private Long id;
    
    @NotEmpty(message = "O campo de nome não pode estar vazio")
    @Size(max = 200, message = "Você excedeu 200 caracteres")
    private String nome;

    @NotNull(message = "Defina um valor")
    private BigDecimal preco;

    @NotEmpty(message = "O campo de marca não pode estar vazio")
    @Size(max = 200, message = "Você excedeu 200 caracteres")
    private String marca;

    @NotEmpty(message = "O campo de serie não pode estar vazio")
    @Size(max = 200, message = "Você excedeu 200 caracteres")
    private String serie;

    @NotEmpty(message = "O campo de colecao não pode estar vazio")
    @Size(max = 200, message = "Você excedeu 200 caracteres")
    private String colecao;
    
    @NotEmpty(message = "O campo de cor não pode estar vazio")
    private String cor;

    @NotEmpty(message = "O campo de nome não pode estar vazio")
    @Size(max = 200, message = "Você excedeu 200 caracteres")
    private String estilo;

    @NotNull(message = "Defina uma quantidade para o estoque")
    private int quantidade_estoque;

    @NotNull(message = "Descrição nao pode ser nula")
    private String descricao;

    private String status;

    @NotNull(message = "Seleciona pelos uma imagem")
    @Size(min = 1, message = "Selecione pelo menos uma imagem")
    private List<MultipartFile> imagens;

    public List<MultipartFile> getImagens() {
        return imagens;
    }

    @Column(name = "caminho_imagem_padrao")
    private String caminhoImagemPadrao;
    
    @Getter
    private List<String> imagensRemovidas;
    private String imagemPadrao;

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
    public String getEstilo() {
        return estilo;
    }
    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }
    public int getQuantidade_estoque() {
        return quantidade_estoque;
    }
    public void setQuantidade_estoque(int quantidade_estoque) {
        this.quantidade_estoque = quantidade_estoque;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setImagens(List<MultipartFile> imagens) {
        this.imagens = imagens;
    }
    public String getCaminhoImagemPadrao() {
        return caminhoImagemPadrao;
    }
    public void setCaminhoImagemPadrao(String caminhoImagemPadrao) {
        this.caminhoImagemPadrao = caminhoImagemPadrao;
    }
    public void setImagensRemovidas(List<String> imagensRemovidas) {
        this.imagensRemovidas = imagensRemovidas;
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

    
}
