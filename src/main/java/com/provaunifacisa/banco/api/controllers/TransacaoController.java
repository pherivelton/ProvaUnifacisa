package com.provaunifacisa.banco.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.provaunifacisa.banco.api.models.Transacao;
import com.provaunifacisa.banco.api.repository.TransacaoRepository;

@RestController
@RequestMapping(value = "/apiBanco")
public class TransacaoController {
	
	@Autowired
	TransacaoRepository transacaoRepository;
	
	@GetMapping("/transacoes")
	public List<Transacao> listaTransacoes(){
		return transacaoRepository.findAll();
	}
	
	@GetMapping("/transacao/{id}")
	public List<Transacao> buscaTransacaoPorId(){
		return transacaoRepository.findAll();
	}
	
	@PostMapping("/transacao")
	public Transacao criaTransacao(@RequestBody Transacao transacao) {
		return transacaoRepository.save(transacao);
	}
}
