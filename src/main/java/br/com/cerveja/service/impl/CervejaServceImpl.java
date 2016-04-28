package br.com.cerveja.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.cerveja.model.cerveja.Cerveja;
import br.com.cerveja.repository.cerveja.CervejaRepository;
import br.com.cerveja.service.CervejaService;
import br.com.cerveja.service.CloundCervejaService;
import br.com.cerveja.service.impl.breweryDB.CloundCervejaServiceImpl;

import com.sosv.breweryDB.connector.entity.beer.Beer;

@Service("CervejaServce")
public class CervejaServceImpl implements CervejaService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	@Qualifier("BreweryDB")
	CloundCervejaService<Beer> cloundCerveja;
	@Autowired
	CervejaRepository cervejaRepository;

	@Override
	public Cerveja buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cerveja> pesquesar(String q) {

		List<Beer> listBeer = cloundCerveja.pesquisar(q);

		List<Cerveja> cervejas = cervejaRepository.pesquisar(q);

		addNewsBeer(cervejas, listBeer);

		return null;
	}

	@Override
	public List<Cerveja> pesquesar(String q, Cerveja cerveja) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cerveja salvar(Cerveja cerveja) {
		try {
			return cervejaRepository.save(cerveja);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Cerveja importar(String id) {
		logger.debug("importando cerveja");
		return cloundCerveja.importar(id);
	}
	
	private void addNewsBeer(List<Cerveja> cervejas, List<Beer> beers) {

	}
}
