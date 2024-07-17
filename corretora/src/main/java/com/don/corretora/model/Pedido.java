package com.don.corretora.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    private BigDecimal valor_total;
    private String status;
    private String codigo_do_pedido;

    @ManyToOne
    @JoinColumn(name = "id_endereco")
    private Endereco endereco_de_entrega;

    @ManyToOne
    @JoinColumn(name = "forma_de_pagamento")
    private FormaDePagamento formaDePagamento;

    private LocalDate data_do_pedido;
    private BigDecimal frete;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public List<ItemPedido> getItens() {
        return itens;
    }
    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }
    public BigDecimal getValor_total() {
        return valor_total;
    }
    public void setValor_total(BigDecimal valor_total) {
        this.valor_total = valor_total;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCodigo_do_pedido() {
        return codigo_do_pedido;
    }
    public void setCodigo_do_pedido(String codigo_do_pedido) {
        this.codigo_do_pedido = codigo_do_pedido;
    }
    public Endereco getEndereco_de_entrega() {
        return endereco_de_entrega;
    }
    public void setEndereco_de_entrega(Endereco endereco_de_entrega) {
        this.endereco_de_entrega = endereco_de_entrega;
    }
    public FormasDePagamento getFormaDePagamento() {
        return formaDePagamento;
    }
    public void setFormaDePagamento(FormasDePagamento formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }
    public LocalDate getData_do_pedido() {
        return data_do_pedido;
    }
    public void setData_do_pedido(LocalDate data_do_pedido) {
        this.data_do_pedido = data_do_pedido;
    }
    public BigDecimal getFrete() {
        return frete;
    }
    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }

    

}
