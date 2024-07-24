package com.don.corretora.model;


import jakarta.persistence.*;

@Entity
public class Boleto extends FormaDePagamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo_de_barras;



    public String getCodigo_de_barras() {
        return codigo_de_barras;
    }

    public void setCodigo_de_barras(String codigo_de_barras) {
        this.codigo_de_barras = codigo_de_barras;
    }

    
}
