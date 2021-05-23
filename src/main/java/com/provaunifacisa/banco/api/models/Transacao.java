package com.provaunifacisa.banco.api.models;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Transacao")
public class Transacao implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idTransacao;
	
	@ManyToOne
	@JoinColumn(name="conta_idConta")
	private Conta conta;

	@Column(nullable = false)
	private double valor;
	
	@Column(nullable = false)
	private Date dataTransacao;
	
	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
	
	public long getIdTransacao() {
		return idTransacao;
	}

	public void setIdTransacao(long idTransacao) {
		this.idTransacao = idTransacao;
	}
}
