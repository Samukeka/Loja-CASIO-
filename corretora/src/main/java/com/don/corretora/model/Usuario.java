package com.don.corretora.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String genero;
    private Date dataNascimento;
    
    @OneToMany(mappedBy = "usuario", cascade =  CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "id_endereco_padrao")
    private Endereco enderecoPadrao;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Endereco> todosEnderecos = new ArrayList<>();


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Endereco getEnderecoPadrao() {
        return enderecoPadrao;
    }

    public void setEnderecoPadrao(Endereco enderecoPadrao) {
        this.enderecoPadrao = enderecoPadrao;
    }

    public List<Endereco> getTodosEnderecos() {
        return todosEnderecos;
    }

    public void setTodosEnderecos(List<Endereco> todosEnderecos) {
        this.todosEnderecos = todosEnderecos;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    

}
