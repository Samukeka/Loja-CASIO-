package com.don.corretora.model;

import jakarta.persistence.*;

@Entity
public class CartaoDeCredito extends FormaDePagamento{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroCartao;
    private String codigoVerificador;
    private String nomeCompleto;
    private String dataVencimento;
    private int qunatidadeParcelas;

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getCodigoVerificador() {
        return codigoVerificador;
    }

    public void setCodigoVerificador(String codigoVerificador) {
        this.codigoVerificador = codigoVerificador;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public int getQunatidadeParcelas() {
        return qunatidadeParcelas;
    }

    public void setQunatidadeParcelas(int qunatidadeParcelas) {
        this.qunatidadeParcelas = qunatidadeParcelas;
    }

    
}
