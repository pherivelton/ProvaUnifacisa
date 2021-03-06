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

import com.provaunifacisa.banco.api.models.Pessoa;
import com.provaunifacisa.banco.api.services.PessoaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Controlador de Usuários do banco")
@RequestMapping(value = "/v1/apiBanco/usuario"	)
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	@ApiOperation(value = "Cria um usuário no banco. (Formato da data YYYY-MM-DD)")
	@PostMapping
	@ResponseBody
	public ResponseEntity<Pessoa>criaUsuario(@RequestBody Pessoa pessoa){
		
		return new ResponseEntity<Pessoa>(pessoaService.criaUsuario(pessoa), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Atualiza os dados de um usuário no banco. (Formato da data YYYY-MM-DD)")
	@PutMapping
	@ResponseBody
	public ResponseEntity<Pessoa>atualizaUsuario(@RequestBody Pessoa pessoa){
		
		Pessoa.verificaExistenciaUsuario(pessoa);
		
		return new ResponseEntity<Pessoa>(pessoaService.atualizaUsuario(pessoa), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Deleta um usuário do banco.")
	@DeleteMapping
	@ResponseBody
	public ResponseEntity<String>deletaUsuario(@RequestBody Pessoa pessoa){
		
		Pessoa.verificaExistenciaUsuario(pessoa);
		pessoaService.deletaUsuario(pessoa);
		
		return ResponseEntity.status(HttpStatus.OK)
		        .body("Usuário(a) deletado(a) com sucesso.");
	}
	
	@ApiOperation(value = "Busca um usuário específico.")
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Pessoa> buscaUsuarioPorId(@PathVariable(value="id") long id){
		
		Pessoa pessoa = pessoaService.buscaUsuarioPorId(id);
		Pessoa.verificaExistenciaUsuario(pessoa);
		
		return new ResponseEntity<>(pessoa, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todos os usuários do banco.")
	@GetMapping("/lista")
	@ResponseBody
	public ResponseEntity<List<Pessoa>> listarUsuarios(){
		
		return new ResponseEntity<>(pessoaService.listarUsuarios(), HttpStatus.OK);
	}
	
	
}
