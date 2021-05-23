package com.provaunifacisa.banco.api.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.provaunifacisa.banco.api.models.Conta;
import com.provaunifacisa.banco.api.models.Transacao;
import com.provaunifacisa.banco.api.repository.TransacaoRepository;

@Service
public class TransacaoService {
		
	private TransacaoRepository transacaoRepository;
	
	public TransacaoService(TransacaoRepository transacaoRepository) {
		
		this.transacaoRepository = transacaoRepository;
	}
	
	public List<Transacao> listarTransacoes(){
		
		return transacaoRepository.findAll();
	}
	
	public Transacao buscaTransacaoPorId(long id) {
		
		return transacaoRepository.findById(id);
	}
	
	public Transacao criaTransacao(Transacao transacao) {
		
		return transacaoRepository.save(transacao);
	}
	
	public Transacao atualizaTransacao(Transacao transacao) {
		
		return transacaoRepository.save(transacao);
	}
	
	public void deletaTransacao(Transacao transacao) {
		
		transacaoRepository.delete(transacao);
	}
	
	public List<Transacao> buscaTransacoesPeloIdConta(Conta conta){
		
		return transacaoRepository.findByidAccount(conta);
	}
	
	public List<Transacao> buscaTransacoesPorPeriodo(Conta conta, LocalDate dataInicial, LocalDate dataFinal){
		
		return transacaoRepository.buscaTransacaoesPorPeriodo(conta, dataInicial, dataFinal);
	}
	
}
