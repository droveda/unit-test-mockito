package br.com.empresa.leilao.service;

import java.time.LocalDate;

import br.com.empresa.leilao.dao.PagamentoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.empresa.leilao.model.Lance;
import br.com.empresa.leilao.model.Pagamento;

@Service
public class GeradorDePagamento {

	@Autowired
	private PagamentoDao pagamentos;

	public void gerarPagamento(Lance lanceVencedor) {
		LocalDate vencimento = LocalDate.now().plusDays(1);
		Pagamento pagamento = new Pagamento(lanceVencedor, vencimento);
		this.pagamentos.salvar(pagamento);
	}

}
