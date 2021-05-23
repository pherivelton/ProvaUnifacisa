package com.provaunifacisa.banco.api.controllers;

import java.sql.Date;
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
import com.provaunifacisa.banco.api.exceptions.InvalidDateException;
import com.provaunifacisa.banco.api.exceptions.InvalidValueException;
import com.provaunifacisa.banco.api.models.Conta;
import com.provaunifacisa.banco.api.models.Transacao;
import com.provaunifacisa.banco.api.services.ContaService;
import com.provaunifacisa.banco.api.services.TransacaoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;

@RestController
@Api(value = "Controlador de Contas Bancárias")
@RequestMapping(value = "v1/apiBanco/conta")
public class ContaController {
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private TransacaoService transacaoService;
	
	@ApiOperation(value = "Lista todas as contas existente no banco.") // Rota OK
	@GetMapping("/contas")
	@ResponseBody
	public ResponseEntity<List<Conta>> listaContas(){
		return new ResponseEntity<>(contaService.listarContas(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Cria uma conta no banco.") // Rota OK
	@PostMapping
	@ResponseBody
	public ResponseEntity<Conta> criaConta(@RequestBody Conta conta) {
		return new ResponseEntity<>(contaService.criaConta(conta), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Deleta uma conta no banco.") // Rota OK
	@DeleteMapping
	@ResponseBody
	public ResponseEntity<String> deletaConta(@RequestBody Conta conta) {
		
		if (conta == null){
			throw new AccountNotFoundException("Conta inexistente.");
		}
		
		contaService.deletaConta(conta);
		
		return ResponseEntity.status(HttpStatus.OK)
		        .body("Conta deletada com sucesso.");
		
	}
	
	@ApiOperation(value = "Atualiza os dados de uma conta no banco.") //Rota OK
	@PutMapping
	@ResponseBody
	public ResponseEntity<Conta> atualizaConta(@RequestBody Conta conta) {
		
		if (conta == null){
			throw new AccountNotFoundException("Conta inexistente.");
		}
		
		contaService.atualizaConta(conta);
		
		return new ResponseEntity<>(conta, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Busca uma conta a partir do id do usuario.") //Rota OK
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Conta> listaContaPorId(@PathVariable(value="id") long id){
		Conta conta = contaService.buscarContaPorId(id);
		
		if (conta == null){
			throw new AccountNotFoundException("Conta inexistente.");
		}
		return new ResponseEntity<>(conta, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna o saldo da conta do usuario.") // Rota OK
	@GetMapping("/{id}/saldo")
	@ResponseBody
	public ResponseEntity<String> consultaSaldoConta(@PathVariable(value="id") long id){
		
		Conta conta = contaService.buscarContaPorId(id);
		
		if (conta == null){
			throw new AccountNotFoundException("Conta inexistente.");
		}
		
		return ResponseEntity.status(HttpStatus.OK)
		        .body("Seu saldo é: " + conta.getSaldo());
	}
	
	@ApiOperation(value = "Bloqueia a conta de um usuário.")
	@PutMapping("/{id}/bloquear")
	@ResponseBody
	public ResponseEntity<String> bloqueaConta(@PathVariable(value="id") long id){
		
		Conta conta = contaService.buscarContaPorId(id);
		
		if (conta == null){
			throw new AccountNotFoundException("Conta inexistente.");
		}
		
		conta.setFlagAtivo(0);
		contaService.atualizaConta(conta);
		
		return ResponseEntity.status(HttpStatus.OK)
		        .body("Sua conta foi bloqueada com sucesso.") ;
	}
	
	@ApiOperation(value = "Realiza uma operação de saque na conta do usuário")
	@PutMapping("/{id}/sacar")
	@ResponseBody
	public ResponseEntity<String> sacaConta(@PathVariable(value="id") long id, @RequestBody Transacao transacao){
		
		Conta conta = contaService.buscarContaPorId(id);
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
	@PutMapping("/{id}/depositar/")
	@ResponseBody
	public ResponseEntity<String> depositaConta(@PathVariable(value="id") long id, @RequestBody Transacao transacao){
		
		Conta conta = contaService.buscarContaPorId(id);
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
	
	@ApiOperation(value = "Consulta o extrato de um usuário.")
	@GetMapping("/conta/extrato/{id}")
	@ResponseBody
	public ResponseEntity<List<Transacao>> buscaExtratoCliente(@PathVariable(value="id") long id){
		
		Conta conta = contaService.buscarContaPorId(id);
		
		if (conta == null){
			throw new AccountNotFoundException("Conta inexistente.");
		}
		
		List<Transacao> extratoConta = contaService.buscaExtratoPorId(conta);
		
		return new ResponseEntity<>(extratoConta, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Consulta o extrato de um usuário por período. Datas devem ser no formato AAAA-MM-DD")
	@GetMapping("/conta/extrato/{id}/{dataInicial}/dataFincial")
	@ResponseBody
	public ResponseEntity<List<Transacao>> buscaExtratoPorPeriodo(@PathVariable(value="id") long id,
																  @PathVariable(value="dataInicial") String dataInicial, 
																  @PathVariable(value="dataFinal") String dataFinal){
		
		Conta conta = contaService.buscarContaPorId(id);
		
		if (conta == null){
			throw new AccountNotFoundException("Conta inexistente.");
		}
		
		if (Date.valueOf(dataInicial) == null || Date.valueOf(dataFinal) == null) {
			
			throw new InvalidDateException("Data inválida, deve ser no formato AAAA-MM-DD");
		}
		
		List<Transacao> extratoConta = contaService.buscaExtradoPorPeriodo(id, dataInicial, dataFinal);
		
		return new ResponseEntity<>(extratoConta, HttpStatus.OK);
	}
	
	
}
