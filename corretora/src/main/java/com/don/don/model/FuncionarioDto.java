package com.don.don.model;

import jakarta.validation.constraints.NotEmpty;

public class FuncionarioDto {

    @NotEmpty(message = "O email não pode estar vazio")
    private String email;
    @NotEmpty(message = "a senha não pode estar vazio")
    private String senha;
    @NotEmpty(message = "O nome não pode estar vazio")
    private String nome;
    @NotEmpty(message = "O cargo não pode estar vazio")
    private String cargo;

    private String status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
