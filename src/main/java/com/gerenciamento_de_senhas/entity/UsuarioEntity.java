package com.gerenciamento_de_senhas.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "USUARIO")
public class UsuarioEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Integer id;
    
    @Column(name = "NOME")
    String nome;
    
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<GerenciamentoEntity> senhas;

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
    public List<GerenciamentoEntity> getSenhas() {
        return senhas;
    }

    public void setSenhas(List<GerenciamentoEntity> senhas) {
        this.senhas = senhas;
    }
}