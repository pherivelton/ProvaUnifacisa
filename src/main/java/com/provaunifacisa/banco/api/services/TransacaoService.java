package com.provaunifacisa.banco.api.services;

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
	
	public List<Transacao> buscaTransacoesPeloIdConta(Conta c){
		
		return transacaoRepository.findByidAccount(c);
	}
	
	public List<Transacao> buscaTransacoesPorPeriodo(long id, String dataInicial, String dataFinal){
		
		return transacaoRepository.buscaTransacaoesPorPeriodo(id, dataInicial, dataFinal);
	}
	
}
