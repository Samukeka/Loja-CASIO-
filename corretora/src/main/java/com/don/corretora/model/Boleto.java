package com.don.corretora.model;

import jakarta.persistence.Entity;

@Entity
public class Boleto implements FormaDePagamento {
    
    private String codigo_de_barras;


    @Override
    public void pagar(){

    }

    public String getCodigo_de_barras() {
        return codigo_de_barras;
    }

    public void setCodigo_de_barras(String codigo_de_barras) {
        this.codigo_de_barras = codigo_de_barras;
    }

    
}
