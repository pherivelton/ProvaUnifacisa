package com.provaunifacisa.banco.api.models;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.provaunifacisa.banco.api.exceptions.UserNotFoundException;

@Entity
@Table(name="pessoa")
public class Pessoa {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idPessoa;
	
	@Column(length = 100, nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String cpf;
	
	@Column(nullable = false)
	private Date dataNascimento;

	public long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public static void verificaExistenciaUsuario(Pessoa pessoa) {
		
		if (pessoa == null) {
			
			throw new UserNotFoundException("Usuário não encontrado.");
		}
	}
	
}
