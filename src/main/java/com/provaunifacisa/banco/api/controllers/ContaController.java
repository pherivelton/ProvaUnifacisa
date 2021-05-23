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

import com.provaunifacisa.banco.api.models.Conta;
import com.provaunifacisa.banco.api.models.Transacao;
import com.provaunifacisa.banco.api.services.ContaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;

@RestController
@Api(value = "Controlador de Contas Bancárias")
@RequestMapping(value = "v1/apiBanco/conta")
public class ContaController {
	
	@Autowired
	private ContaService contaService;
	
	@ApiOperation(value = "Lista todas as contas existente no banco.") 
	@GetMapping("/contas")
	@ResponseBody
	public ResponseEntity<List<Conta>> listaContas(){
		return new ResponseEntity<>(contaService.listarContas(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Cria uma conta no banco.")
	@PostMapping
	@ResponseBody
	public ResponseEntity<Conta> criaConta(@RequestBody Conta conta) {
		return new ResponseEntity<>(contaService.criaConta(conta), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Deleta uma conta no banco.")
	@DeleteMapping
	@ResponseBody
	public ResponseEntity<String> deletaConta(@RequestBody Conta conta) {
		
		Conta.verificaExistenciaConta(conta);
		
		contaService.deletaConta(conta);
		
		return ResponseEntity.status(HttpStatus.OK)
		        .body("Conta deletada com sucesso.");
		
	}
	
	@ApiOperation(value = "Atualiza os dados de uma conta no banco.") 
	@PutMapping
	@ResponseBody
	public ResponseEntity<Conta> atualizaConta(@RequestBody Conta conta) {
		
		Conta.verificaExistenciaConta(conta);
		contaService.atualizaConta(conta);
		
		return new ResponseEntity<>(conta, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Busca uma conta a partir do id do usuario.") 
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Conta> listaContaPorId(@PathVariable(value="id") long id){
		
		Conta conta = contaService.buscarContaPorId(id);
		Conta.verificaExistenciaConta(conta);
		
		return new ResponseEntity<>(conta, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna o saldo da conta do usuario.") 
	@GetMapping("/{id}/saldo")
	@ResponseBody
	public ResponseEntity<String> consultaSaldoConta(@PathVariable(value="id") long id){
		
		Conta conta = contaService.buscarContaPorId(id);
		Conta.verificaExistenciaConta(conta);
		
		return ResponseEntity.status(HttpStatus.OK)
		        .body("Seu saldo é: " + conta.getSaldo());
	}
	
	@ApiOperation(value = "Bloqueia a conta de um usuário.")
	@PutMapping("/{id}/bloquear")
	@ResponseBody
	public ResponseEntity<String> bloqueaConta(@PathVariable(value="id") long id){
		
		Conta conta = contaService.buscarContaPorId(id);
		Conta.verificaExistenciaConta(conta);
		
		conta.setFlagAtivo(0);
		contaService.atualizaConta(conta);
		
		return ResponseEntity.status(HttpStatus.OK)
		        .body("Sua conta foi bloqueada com sucesso.") ;
	}
	
	@ApiOperation(value = "Consulta o extrato de um usuário.")
	@GetMapping("/conta/extrato/{id}")
	@ResponseBody
	public ResponseEntity<List<Transacao>> buscaExtratoCliente(@PathVariable(value="id") long id){
		
		Conta conta = contaService.buscarContaPorId(id);
		Conta.verificaExistenciaConta(conta);
		
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
		
		Conta.verificaExistenciaConta(conta);
		
		Transacao.checaDataValida(dataInicial, dataFinal);
		
		List<Transacao> extratoConta = contaService.buscaExtradoPorPeriodo(id, dataInicial, dataFinal);
		
		return new ResponseEntity<>(extratoConta, HttpStatus.OK);
	}
		
}
