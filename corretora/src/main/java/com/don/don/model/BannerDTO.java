package com.don.don.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class BannerDTO {

    private Long id;
    @NotEmpty(message = "O campo de nome não pode estar vazio")
    @Size(max = 200, message = "Você excedeu 200 caracteres")
    private String titulo;
    private MultipartFile imagem;

    @NotEmpty(message = "O campo de cor não pode estar vazio")
    private String tipoAtributo;

    @NotEmpty(message = "O campo de cor não pode estar vazio")
    private String valorAtributo;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSessao() {
        return sessao;
    }

    public void setSessao(String sessao) {
        this.sessao = sessao;
    }

    private String status;

    private String sessao;

    public BannerDTO() {
    }

    public BannerDTO(Long id, String titulo, MultipartFile imagem, String tipoAtributo, String valorAtributo) {
        this.id = id;
        this.titulo = titulo;
        this.imagem = imagem;
        this.tipoAtributo = tipoAtributo;
        this.valorAtributo = valorAtributo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public MultipartFile getImagem() {
        return imagem;
    }

    public void setImagem(MultipartFile imagem) {
        this.imagem = imagem;
    }

    public String getTipoAtributo() {
        return tipoAtributo;
    }

    public void setTipoAtributo(String tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    public String getValorAtributo() {
        return valorAtributo;
    }

    public void setValorAtributo(String valorAtributo) {
        this.valorAtributo = valorAtributo;
    }
}
