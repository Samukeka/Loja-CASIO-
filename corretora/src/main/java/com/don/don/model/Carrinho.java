package com.don.don.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Carrinho {
    private List<ItemPedido> itens;
    private BigDecimal total;
    private BigDecimal frete;
    private FormaDePagamento formadepagamento;
    private String cupomDesconto;

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }

    public FormaDePagamento getFormadepagamento() {
        return formadepagamento;
    }

    public void setFormadepagamento(FormaDePagamento formadepagamento) {
        this.formadepagamento = formadepagamento;
    }

    public String getCupomDesconto() {
        return cupomDesconto;
    }

    public void setCupomDesconto(String cupomDesconto) {
        this.cupomDesconto = cupomDesconto;
    }

}
