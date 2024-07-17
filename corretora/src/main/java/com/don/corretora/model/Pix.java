package com.don.corretora.model;

import jakarta.persistence.Entity;

@Entity
public class Pix implements FormaDePagamento {
    
    private String chavePix;

    @Override
    public void pagar(){

    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }
    
}
