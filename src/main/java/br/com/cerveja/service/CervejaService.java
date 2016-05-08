package br.com.cerveja.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import br.com.cerveja.controller.VO.PesquisaCervejaVO;
import br.com.cerveja.exception.RNException;
import br.com.cerveja.model.cerveja.Cerveja;

public interface CervejaService {
	Cerveja buscarPorId(Long id);
	Set<PesquisaCervejaVO> pesquisar(String q);
	List<Cerveja> pesquisar(String q,Cerveja cerveja);
	Cerveja salvar(Cerveja cerveja);
	Cerveja importar(String id);
	Cerveja addImgs(Long id, File rotulo, File garrafa, File[] outros) throws RNException;
}
