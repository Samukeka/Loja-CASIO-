package com.don.corretora.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class ClienteDto {

    private Integer id;

    @Pattern(regexp = "^[a-zA-Z]{3,} [a-zA-Z]{3,}$", message = "Insira um nome válido.")
    @NotEmpty(message = "O campo de nome não pode estar vazio")
    private String nome;

    @Past(message = "A data de nascimento inválida.")
    @NotNull(message = "Data de nascimento é obrigatória.")
    private Date dataNascimento;

    private String genero;

    @NotEmpty(message = "Necessário inserir o CPF.   ")
    @Size(min = 11, message = "Insira um CPF Valido. ")
    @Size(max = 11, message = "Insira um CPF Valido. ")
    private String cpf;

    @NotEmpty(message = "O campo de email não pode estar vazio")
    @Pattern(regexp = ".+@.+\\..+", message = "Insira um email válido, por favor")
    @Email
    private String email;

    @NotEmpty(message = "A senha não pode estar vazia")
    @Size(min = 6, message = "A senha deve ter mais de 6 caracteres")
    private String senha;

    @NotEmpty(message = "A confirmação de senha não pode estar vazia")
    @Size(min = 6, message = "A senha deve ter mais de 6 caracteres")
    private String confirmaSenha;

    @NotBlank(message = "O CEP não pode estar vazio")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "Formato de CEP inválido. Deve seguir o padrão 'XXXXX-XXX ou XXXXXXXX'.")
    private String cep;

    @NotBlank(message = "O logradouro não pode estar vazio")
    private String logradouro;

    @NotBlank(message = "O número não pode estar vazio")
    @Size(min = 1, max = 10, message = "O número deve conter entre 1 e 10 caracteres")
    private String numero;

    private String complemento;

    @NotBlank(message = "O bairro não pode estar vazio")
    private String bairro;

    @NotBlank(message = "A cidade não pode estar vazia")
    private String cidade;

    @Pattern(regexp = "^[A-Za-z]+$", message = "A UF não pode estar vazia")
    @Size(min = 2, max = 2, message = "Insira uma UF válida")
    private String uf;

    private Endereco enderecoPadrao;
    // Endereço

    private List<Endereco> enderecos = new ArrayList<>();

    public Endereco getEnderecoPadrao() {
        return enderecoPadrao;
    }

    public void setEnderecoPadrao(Endereco enderecoPadrao) {
        this.enderecoPadrao = enderecoPadrao;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

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

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

}