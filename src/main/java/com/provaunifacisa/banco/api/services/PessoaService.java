package com.provaunifacisa.banco.api.services;

import java.util.List;

import com.provaunifacisa.banco.api.models.Pessoa;
import com.provaunifacisa.banco.api.repository.PessoaRepository;

public class PessoaService {

		private PessoaRepository pessoaRepository;
		
		public PessoaService(PessoaRepository pessoaRepository) {
			
			this.pessoaRepository = pessoaRepository;
		}
		
		public List<Pessoa> listarUsuarios(){
			
			return pessoaRepository.findAll();
		}
		
		public Pessoa buscaUsuarioPorId(long id) {
			
			return pessoaRepository.findById(id);
		}
		
		public Pessoa criaUsuario(Pessoa pessoa) {
			
			return pessoaRepository.save(pessoa);
		}
		
		public Pessoa atualizaUsuario(Pessoa pessoa) {
			
			return pessoaRepository.save(pessoa);
		}
		
		public void deletaTransacao(Pessoa pessoa) {
			
			pessoaRepository.delete(pessoa);
		}
}
