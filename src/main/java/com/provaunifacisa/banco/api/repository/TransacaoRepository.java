package com.provaunifacisa.banco.api.repository;

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
	
	@Query(value = ("SELECT t FROM Transacao t WHERE t.conta = :id ORDER BY t.dataTransacao DESC"))
	List<Transacao> findByidAccount(@Param("id") Conta c);
	
	@Query(value = ("SELECT t FROM Transacao t WHERE t.conta = :id AND t.dataTransacao in (:dataInicial, :dataFinal) ORDER BY t.dataTransacao DESC"))
	List<Transacao> buscaTransacaoesPorPeriodo(@Param("id") long id, @Param("dataInicial") String dataInicial, @Param("dataFinal") String dataFinal);
}
