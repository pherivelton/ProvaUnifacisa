package com.provaunifacisa.banco.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.provaunifacisa.banco.api.models.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>{

		Conta findById(long id);
}
