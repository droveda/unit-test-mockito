package br.com.empresa.leilao.service;

import br.com.empresa.leilao.dao.LeilaoDao;
import br.com.empresa.leilao.dao.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.empresa.leilao.dao.LanceDao;
import br.com.empresa.leilao.dto.NovoLanceDto;
import br.com.empresa.leilao.model.Lance;
import br.com.empresa.leilao.model.Leilao;
import br.com.empresa.leilao.model.Usuario;

@Service
public class LanceService {

	@Autowired
	private LanceDao lances;

	@Autowired
	private UsuarioDao usuarios;

	@Autowired
	private LeilaoDao leiloes;

	public boolean propoeLance(NovoLanceDto lanceDto, String nomeUsuario) {

		Usuario usuario = usuarios.buscarPorUsername(nomeUsuario);
		Lance lance = lanceDto.toLance(usuario);

		Leilao leilao = this.getLeilao(lanceDto.getLeilaoId());

		if (leilao.propoe(lance)) {
			lances.salvar(lance);
			return true;
		}

		return false;
	}

	public Leilao getLeilao(Long leilaoId) {
		return leiloes.buscarPorId(leilaoId);
	}

}