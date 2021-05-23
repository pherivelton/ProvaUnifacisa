package com.provaunifacisa.banco.api.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.provaunifacisa.banco.api.exceptions.InvalidDateException;
import com.provaunifacisa.banco.api.exceptions.InvalidValueException;

@Entity
@Table(name="Transacao")
public class Transacao implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idTransacao;
	
	@ManyToOne
	@JoinColumn(name="id_Conta")
	private Conta conta;

	@Column(nullable = false)
	private double valor;
	
	@Column(nullable = false)
	private LocalDate dataTransacao;
	
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

	public LocalDate getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(LocalDate dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
	
	public long getIdTransacao() {
		return idTransacao;
	}

	public void setIdTransacao(long idTransacao) {
		this.idTransacao = idTransacao;
	}
	
	public static void verificaValorValido(double valor) {
		
		if (valor <= 0) {
			throw new InvalidValueException("Valor inválido para realizar o depósito.");
		}
	}
	
	public static void checaDataValida(String dataInicial, String dataFinal) throws ParseException {
		
		DateFormat df = new SimpleDateFormat ("YYYY-MM-DD");
		
		try {
			df.parse(dataInicial);
			df.parse(dataFinal);
		}catch (InvalidDateException e) {
			System.out.println("Data inválida, deve ser no formato AAAA-MM-DD");
		}
		
	}
}
