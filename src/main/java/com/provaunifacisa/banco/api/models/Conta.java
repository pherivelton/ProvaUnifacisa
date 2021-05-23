package com.provaunifacisa.banco.api.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;

import com.provaunifacisa.banco.api.exceptions.AccountNotFoundException;

@Entity
@Table(name="conta_corrente")
public class Conta implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idConta;
	
	@Column(nullable = false)
	private int idPessoa;
	
	@Column(nullable = false)
	private double saldo;
	
	@Column(nullable = false)
	private double limitesaquediario;
	
	@Column(nullable = false)
	@Value("${flagAtivo:1}")
	private int flagAtivo;
	
	@Column(nullable = false)
	private int tipoConta;
	
	@Column(nullable = false)
	private Date dataCriacao;

	public long getIdConta() {
		return idConta;
	}

	public void setIdConta(long idConta) {
		this.idConta = idConta;
	}

	public int getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public double getLimitesaquediario() {
		return limitesaquediario;
	}

	public void setLimitesaquediario(double limitesaquediario) {
		this.limitesaquediario = limitesaquediario;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(int tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public void sacaConta(double valor) {
		
		double valorAposSaque = getSaldo() - valor;
		setSaldo(valorAposSaque);
	}
	
	public void depositaConta(double valor) {
		
		double valorAposDeposito = getSaldo() + valor;
		setSaldo(valorAposDeposito);
	}
	
	public static void verificaExistenciaConta(Conta conta) {
		
		if (conta == null){
			throw new AccountNotFoundException("Conta inexistente.");
		}
	}

}
