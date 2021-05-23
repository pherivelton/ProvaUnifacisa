package com.provaunifacisa.banco.api;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.provaunifacisa.banco.api.models.Conta;
import com.provaunifacisa.banco.api.services.ContaService;

@RunWith(SpringRunner.class)
public class ContaServiceTest {

		@Autowired
		ContaService contaService;
		
		@Test
		public void buscaContaPorId() {
			long id = 1; 
			Conta conta = contaService.buscarContaPorId(id);
			Assertions.assertEquals(conta.getSaldo(), 200.0);
		}
}
