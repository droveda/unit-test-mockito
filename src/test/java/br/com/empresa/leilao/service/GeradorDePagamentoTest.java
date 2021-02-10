package br.com.empresa.leilao.service;

import br.com.empresa.leilao.dao.PagamentoDao;
import br.com.empresa.leilao.model.Lance;
import br.com.empresa.leilao.model.Leilao;
import br.com.empresa.leilao.model.Pagamento;
import br.com.empresa.leilao.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class GeradorDePagamentoTest {

    @InjectMocks
    @Autowired
    private GeradorDePagamento geradorDePagamento;

    @Mock
    private PagamentoDao pagamentoDao;

    @Captor
    private ArgumentCaptor<Pagamento> captor;

    @Test
    public void deveriaCriarPagamentoParaVencedorDoLeilao() {
        Leilao leilao = getLeilao();
        Lance lanceVencedor = leilao.getLanceVencedor();
        geradorDePagamento.gerarPagamento(lanceVencedor);


        Mockito.verify(pagamentoDao, Mockito.atLeastOnce()).salvar(captor.capture());

        Pagamento pagamento = captor.getValue();
        Assert.assertNotNull(pagamento);
        Assert.assertEquals(LocalDate.now().plusDays(1), pagamento.getVencimento());
        Assert.assertEquals(lanceVencedor.getValor(), pagamento.getValor());
        Assert.assertFalse(pagamento.getPago());
        Assert.assertEquals(lanceVencedor.getUsuario(), pagamento.getUsuario());
        Assert.assertEquals(lanceVencedor.getLeilao(), leilao);
    }

    private Leilao getLeilao() {
        Usuario fulano = new Usuario("Fulano");
        Usuario ciclano = new Usuario("Ciclano");

        Leilao leilao = new Leilao("Celular", new BigDecimal(500),  fulano);
        Lance lance1 = new Lance(ciclano, new BigDecimal(550));
        leilao.propoe(lance1);
        leilao.setLanceVencedor(lance1);

        return leilao;
    }



}
