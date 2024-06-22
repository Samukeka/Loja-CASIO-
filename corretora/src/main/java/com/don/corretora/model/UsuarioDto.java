package com.don.corretora.model;
import jakarta.validation.constraints.NotEmpty;


public class UsuarioDto {
    

    @NotEmpty(message = "O campo de nome não pode estar vazio")
    private String nome;

    @NotEmpty(message = "O campo de senha não pode estar vazio")
    private String senha;

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
