package br.com.cerveja.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cerveja.controller.Filter;
import br.com.cerveja.controller.VO.PesquisaCervejaVO;
import br.com.cerveja.exception.RNException;
import br.com.cerveja.model.cerveja.Cerveja;
import br.com.cerveja.model.cerveja.StatusCerveja;

public interface CervejaService {
	Cerveja buscarPorId(Long id);
	Set<PesquisaCervejaVO> pesquisar(String q);
	List<Cerveja> pesquisar(String q,Cerveja cerveja);
	Cerveja salvar(Cerveja cerveja);
	Cerveja importar(String id);
	Cerveja addImgs(Long id, File rotulo, File garrafa, File[] outros) throws RNException;
	Page<Cerveja> listar(String name, String cervejaria, StatusCerveja statusCerveja, Pageable pageRequest);
	Page<Cerveja> listar(Filter filter, Pageable pageRequest);
}
