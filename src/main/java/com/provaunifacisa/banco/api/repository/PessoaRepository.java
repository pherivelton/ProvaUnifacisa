package com.provaunifacisa.banco.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.provaunifacisa.banco.api.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

		Pessoa findById(long id);
}
