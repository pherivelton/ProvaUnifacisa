package com.provaunifacisa.banco.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.provaunifacisa.banco.api.models.Conta;
import com.provaunifacisa.banco.api.models.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

	Transacao findById(long id);
	
	@Query(value = ("SELECT t FROM Transacao t WHERE t.conta = :conta ORDER BY t.dataTransacao DESC"))
	List<Transacao> findByidAccount(@Param("conta") Conta conta);
	
	@Query(value = ("SELECT t FROM Transacao t WHERE t.conta = :conta AND t.dataTransacao in (:dataInicial, :dataFinal) ORDER BY t.dataTransacao DESC"))
	List<Transacao> buscaTransacaoesPorPeriodo(@Param("conta") Conta conta, @Param("dataInicial") LocalDate inicio1, @Param("dataFinal") LocalDate fim1);
}
