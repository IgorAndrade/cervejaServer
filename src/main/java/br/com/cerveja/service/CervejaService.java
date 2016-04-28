package br.com.cerveja.service;

import java.util.List;

import br.com.cerveja.model.cerveja.Cerveja;

public interface CervejaService {
	Cerveja buscarPorId(Long id);
	List<Cerveja> pesquesar(String q);
	List<Cerveja> pesquesar(String q,Cerveja cerveja);
	Cerveja salvar(Cerveja cerveja);
	Cerveja importar(String id);
}
