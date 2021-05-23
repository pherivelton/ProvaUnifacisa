package com.provaunifacisa.banco.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.provaunifacisa.banco.api.models.Conta;
import com.provaunifacisa.banco.api.models.Transacao;
import com.provaunifacisa.banco.api.repository.ContaRepository;
import com.provaunifacisa.banco.api.repository.TransacaoRepository;

@Service
public class ContaService {
	
	private ContaRepository contaRepository;
	private TransacaoRepository transacaoRepository;
	
	public ContaService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
		
		this.contaRepository = contaRepository;
		this.transacaoRepository = transacaoRepository;
	}
	
	public List<Conta> listarContas(){
		return contaRepository.findAll();
	}
	
	public Conta buscarContaPorId(long id){
		return contaRepository.findById(id);
	}
	
	public Conta criaConta(Conta conta){
		return contaRepository.save(conta);
	}
	
	public Conta atualizaConta(Conta conta){
		return contaRepository.save(conta);
	}
	
	public void deletaConta(Conta conta){
		contaRepository.delete(conta);
	}
	
	public List<Transacao>buscaExtratoPorId(Conta c){
		return transacaoRepository.findByidAccount(c);
	}
	
	public List<Transacao>buscaExtradoPorPeriodo(long id, String dataInicial, String dataFinal){
		return transacaoRepository.buscaTransacaoesPorPeriodo(id, dataInicial, dataFinal);
	}
}
