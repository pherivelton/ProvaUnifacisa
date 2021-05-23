package com.provaunifacisa.banco.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.provaunifacisa.banco.api.exceptions.AccountNotFoundException;
import com.provaunifacisa.banco.api.exceptions.InvalidValueException;
import com.provaunifacisa.banco.api.models.Conta;
import com.provaunifacisa.banco.api.models.Transacao;
import com.provaunifacisa.banco.api.services.ContaService;
import com.provaunifacisa.banco.api.services.TransacaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Controlador de Transações Bancárias")
@RequestMapping(value = "v1/apiBanco/transacao")
public class TransacaoController {
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private TransacaoService transacaoService;
	
	@ApiOperation(value = "Lista todas as transações existente no banco.")
	@GetMapping("/transacoes")
	public List<Transacao> listaTransacoes(){	
		return transacaoService.listarTransacoes();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	@ApiOperation(value = "Busca uma transação específica.")
	public Transacao buscaTransacaoPorId(long id){
		return transacaoService.buscaTransacaoPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Cria uma transação.")
	@ResponseBody
	public Transacao criaTransacao(@RequestBody Transacao transacao) {
		return transacaoService.criaTransacao(transacao);
	}
	
	@PutMapping
	@ApiOperation(value = "Atualiza uma transação.")
	@ResponseBody
	public Transacao atualizaTransacao(@RequestBody Transacao transacao) {
		return transacaoService.atualizaTransacao(transacao);
	}
	
	@DeleteMapping
	@ApiOperation(value = "Deleta uma transação.")
	@ResponseBody
	public void excluiTransacao(@RequestBody Transacao transacao) {
		transacaoService.deletaTransacao(transacao);
	}
	
	@ApiOperation(value = "Realiza uma operação de saque na conta do usuário")
	@PutMapping("/{idConta}/sacar")
	@ResponseBody
	public ResponseEntity<String> sacaConta(@PathVariable(value="idConta") long idConta, @RequestBody Transacao transacao){
		
		Conta conta = contaService.buscarContaPorId(idConta);
		transacao.setConta(conta);
		
		if (conta == null){
			throw new AccountNotFoundException("Conta inexistente.");
		}
		
		double valorASacar = transacao.getValor();
		
		if (valorASacar <= 0) {
			throw new InvalidValueException("Valor inválido para realizar o saque.");
		}
		
		conta.sacaConta(transacao.getValor());
		
		transacaoService.criaTransacao(transacao);
		contaService.atualizaConta(conta);
		
		return ResponseEntity.status(HttpStatus.OK)
		        .body("Saque realizado com sucesso, seu novo saldo é " + conta.getSaldo());
	}
	
	@ApiOperation(value = "Realiza uma operação de depósito na conta do usuário")
	@PutMapping("/{idConta}/depositar/")
	@ResponseBody
	public ResponseEntity<String> depositaConta(@PathVariable(value="idConta") long idConta, @RequestBody Transacao transacao){
		
		Conta conta = contaService.buscarContaPorId(idConta);
		transacao.setConta(conta);
		
		if (conta == null){
			throw new AccountNotFoundException("Conta inexistente.");
		}
		
		double valorADepositar = transacao.getValor();
		
		if (valorADepositar <= 0) {
			throw new InvalidValueException("Valor inválido para realizar o depósito.");
		}
		conta.depositaConta(transacao.getValor());
		
		transacaoService.criaTransacao(transacao);
		contaService.atualizaConta(conta);
		
		return ResponseEntity.status(HttpStatus.OK)
		        .body("Depósito realizado com sucesso, seu novo saldo é " + conta.getSaldo());
	}
	
	@ApiOperation(value = "Realiza uma operação de transferência entre contas")
	@PutMapping("/transferir/{idOrigem}/{idDestino}")
	@ResponseBody
	public ResponseEntity<String> transferenciaEntreContas(@RequestBody Transacao transacaoOrigem, @PathVariable(value="idOrigem") long idOrigem, @PathVariable(value="idDestino") long idDestino){
		
		Conta contaOrigem = contaService.buscarContaPorId(idOrigem);
		Conta contaDestino = contaService.buscarContaPorId(idDestino);
		transacaoOrigem.setConta(contaOrigem);
		
		double valorDaTransacao = transacaoOrigem.getValor();
		
		if (contaOrigem == null || contaDestino == null){
			throw new AccountNotFoundException("Conta de origem ou destino inexistente.");
		}
		
		if (valorDaTransacao <= 0) {
			throw new InvalidValueException("Valor inválido para realizar a transação.");
		}
		
		contaOrigem.sacaConta(valorDaTransacao);
		transacaoService.criaTransacao(transacaoOrigem);
		
		Transacao transacaoDestino = new Transacao();
		transacaoDestino.setConta(contaDestino);
		transacaoDestino.setDataTransacao(transacaoOrigem.getDataTransacao());
		transacaoDestino.setValor(valorDaTransacao);
		
		contaDestino.depositaConta(transacaoOrigem.getValor());
		transacaoService.criaTransacao(transacaoDestino);
		
		contaService.atualizaConta(contaOrigem);
		contaService.atualizaConta(contaDestino);
		
		return ResponseEntity.status(HttpStatus.OK)
		        .body("Transferência realizada com sucesso. Saldo na conta origem: " + contaOrigem.getSaldo() +
		        	  "\nSaldo na conta destino: " + contaDestino.getSaldo());
	}
}
