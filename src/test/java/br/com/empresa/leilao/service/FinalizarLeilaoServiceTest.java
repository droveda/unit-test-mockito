package br.com.empresa.leilao.service;

import br.com.empresa.leilao.dao.LeilaoDao;
import br.com.empresa.leilao.model.Lance;
import br.com.empresa.leilao.model.Leilao;
import br.com.empresa.leilao.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FinalizarLeilaoServiceTest {

    private FinalizarLeilaoService service;

    @Mock
    private EnviadorDeEmails enviadorDeEmails;

    @Mock
    private LeilaoDao leilaoDaoMock;

    @Test
    public void deveriaFinalizarUmLeilaoTest() {
        List<Leilao> leiloes = criaListaLeiloes();
        Mockito.when(leilaoDaoMock.buscarLeiloesExpirados()).thenReturn(leiloes);

        service = new FinalizarLeilaoService(leilaoDaoMock, enviadorDeEmails);
        service.finalizarLeiloesExpirados();

        Leilao leilao = leiloes.get(0);

        Assert.assertTrue(leilao.getFechado());
        Assert.assertEquals(new BigDecimal(600), leilao.getLanceVencedor().getValor());
        Mockito.verify(leilaoDaoMock, Mockito.atLeastOnce()).salvar(leilao);
    }


    @Test
    public void deveriaEnviarEmailParaVencedorDoLeilao() {
        List<Leilao> leiloes = criaListaLeiloes();
        Mockito.when(leilaoDaoMock.buscarLeiloesExpirados()).thenReturn(leiloes);

        service = new FinalizarLeilaoService(leilaoDaoMock, enviadorDeEmails);
        service.finalizarLeiloesExpirados();

        Leilao leilao = leiloes.get(0);
        Lance lanceVencedor = leilao.getLanceVencedor();

        Mockito.verify(enviadorDeEmails, Mockito.atLeastOnce()).enviarEmailVencedorLeilao(lanceVencedor);
    }

    @Test
    public void naoDeveriaEnviarEmailEmCasoDeErroAoSalvarLeilao() {
        List<Leilao> leiloes = criaListaLeiloes();
        Mockito.when(leilaoDaoMock.buscarLeiloesExpirados()).thenReturn(leiloes);

        Mockito.when(leilaoDaoMock.salvar(Mockito.any())).thenThrow(RuntimeException.class);

        service = new FinalizarLeilaoService(leilaoDaoMock, enviadorDeEmails);

        try {
            service.finalizarLeiloesExpirados();
            Mockito.verifyNoInteractions(enviadorDeEmails);
        } catch (Exception ex) {

        }
    }

    private List<Leilao> criaListaLeiloes() {

        Usuario fulano = new Usuario("Fulano");
        Usuario ciclano = new Usuario("Ciclano");

        List<Leilao> list = new ArrayList();
        Leilao leilao = new Leilao("Celular", new BigDecimal(500),  fulano);

        Lance lance1 = new Lance(ciclano, new BigDecimal(550));
        Lance lance2 = new Lance(fulano, new BigDecimal(600));

        leilao.propoe(lance1);
        leilao.propoe(lance2);

        list.add(leilao);

        return list;
    }

}
