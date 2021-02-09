package br.com.empresa.leilao;

import br.com.empresa.leilao.dao.LeilaoDao;
import br.com.empresa.leilao.model.Leilao;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

public class HelloWorldMockito {

    @Test
    public void hello() {
        LeilaoDao leilaoDaoMock = Mockito.mock(LeilaoDao.class);
        List<Leilao> leiloes = leilaoDaoMock.buscarTodos();
        Assert.assertTrue(leiloes.isEmpty());
    }

}
