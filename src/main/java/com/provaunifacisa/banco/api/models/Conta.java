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
import com.provaunifacisa.banco.api.exceptions.BlockedAccountException;

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
	
	/**
	 * Atributo que diz se uma conta é ativa ou não
	 * 0 é conta bloqueada e 1 conta ativa.
	 */
	
	@Column(nullable = false)
	@Value("${flagAtivo:1}")
	private int flagAtivo=1;
	
	/**
	 * Atributo que diz qual tipo da conta do cliente
	 * 1 é conta corrente.
	 * 2 é conta poupança.
	 * 3 é conta salário.
	 * 4 é conta universitária.
	 */
	
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
	
	/**
	 * Método que checa se uma conta existe ou não
	 * @return Excecao de Conta não existente
	 */
	
	public static void verificaExistenciaConta(Conta conta) {
		
		if (conta == null){
			throw new AccountNotFoundException("Conta inexistente.");
		}
	}
	
	/**
	 * Método que checa se uma conta está bloqueada ou não
	 * Caso esteja bloqueada, impede o usuário de realizar movimentações como saque, depósito e transferência
	 * 
	 * @return Excecao de conta bloqueada
	 */
	
	public static void checaContaBloqueada(int flagAtivo) {
		
		if (flagAtivo == 0) {
			
			throw new BlockedAccountException("Conta bloqueada, não pode fazer movimentações.");
		}
	}

}
