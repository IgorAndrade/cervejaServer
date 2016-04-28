package br.com.cerveja.service.impl.breweryDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cerveja.model.cerveja.Category;
import br.com.cerveja.model.cerveja.Cerveja;
import br.com.cerveja.model.cerveja.Cervejaria;
import br.com.cerveja.model.cerveja.StatusCerveja;
import br.com.cerveja.model.cerveja.Style;
import br.com.cerveja.repository.cerveja.CategoriaRepository;
import br.com.cerveja.repository.cerveja.CervejaRepository;
import br.com.cerveja.repository.cerveja.CervejariaRepository;
import br.com.cerveja.repository.cerveja.StyleRepository;
import br.com.cerveja.service.CloundCervejaService;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sosv.breweryDB.connector.entity.beer.Beer;
import com.sosv.breweryDB.connector.entity.brewery.Brewery;
import com.sosv.breweryDB.connector.guice.BreweryDBSyncModule;
import com.sosv.breweryDB.connector.service.IBreweryDBService;
import com.sosv.breweryDB.connector.service.exceptions.ApiKeyNotFoundExeption;
import com.sosv.breweryDB.connector.service.resource.filter.beer.BeerFilter;
import com.sosv.breweryDB.connector.service.resource.filter.beer.BeersFilter;
import com.sosv.breweryDB.connector.service.resource.filter.search.SearchFilter;

@Service("BreweryDB")
public class CloundCervejaServiceImpl implements CloundCervejaService<Beer> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
//	@Qualifier(value="CategoriaRepository")
	private CategoriaRepository categoriaRepository;
	@Autowired
//	@Qualifier(value="StyleRepository")
	private StyleRepository styleRepository;
	@Autowired
//	@Qualifier(value="CervejariaRepository")
	private CervejariaRepository cervejariaRepository;
	@Autowired
//	@Qualifier(value="CervejaRepository")
	private CervejaRepository cervejaRepository;
	private IBreweryDBService breweryDb;

	@PostConstruct
	private void init() {
		Injector injector = Guice.createInjector(new BreweryDBSyncModule());
		breweryDb = injector.getInstance(IBreweryDBService.class);
	}

	@Override
	public List<Beer> pesquisar(String q) {
		SearchFilter f = new SearchFilter();
		f.setWithBreweries(true);
		try {
			List<Beer> searchBeers = breweryDb.searchBeers(q, f);
			return searchBeers;
		} catch (ApiKeyNotFoundExeption e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Beer> pesquisar(String q, Cerveja c) {
		try {
			BeersFilter filter = new BeersFilter();
			filter.setName(c.getName());
			filter.setWithBreweries(true);
			List<Beer> allBeers = breweryDb.getAllBeers(filter);
			return allBeers;
		} catch (ApiKeyNotFoundExeption e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Cerveja importar(String id) {
		try {
			Beer beerById = breweryDb.getBeerById(id,BeerFilter.createWithBreweriesFilter(true));
			return importar(beerById);
		} catch (ApiKeyNotFoundExeption e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Cerveja> importar(List<Beer> beers) {
		List<Cerveja> lista = new ArrayList<Cerveja>();
		for (Beer beer : beers) {
			Cerveja cerveja = importar(beer);
			lista.add(cerveja);
		}
		return lista;
	}

	@Override
	public Cerveja importar(Beer beer) {
		try {
			Cerveja findByBrewerydbId = cervejaRepository
					.findByBrewerydbId(beer.getId());
			if (findByBrewerydbId != null) {
				logger.info("beer já importado: " + findByBrewerydbId.getName());
				return findByBrewerydbId;
			}
			Cerveja cerveja = new Cerveja();
			cerveja.setStatus(StatusCerveja.IMPORTADO);
			cerveja.setBrewerydbId(beer.getId());
			cerveja.setCreateDate(new Date());
			cerveja.setStyle(importar(beer.getStyle()));
			cerveja.setCervejaria(importar(beer.getBreweries().get(0)));
			BeanUtils.copyProperties(beer, cerveja, "id", "createDate",
					"status", "style", "");
			
			return cervejaRepository.saveAndFlush(cerveja);
		} catch (Exception e) {
			logger.info("Erro ao importar beer:" + beer.getName(),e);
			return null;
		}
	}

	public Cervejaria importar(Brewery brewery) {
		try {
			Cervejaria findByBrewerydbId = cervejariaRepository
					.findByBrewerydbId(brewery.getId());
			if (findByBrewerydbId != null) {
				logger.info("brewery já importado: "
						+ findByBrewerydbId.getName());
				return findByBrewerydbId;
			}

			Cervejaria cervejaria = new Cervejaria();
			cervejaria.setCreateDate(new Date());
			cervejaria.setStatus(StatusCerveja.IMPORTADO);
			cervejaria.setBrewerydbId(brewery.getId());
			BeanUtils.copyProperties(brewery, cervejaria, "id", "createDate",
					"status");

			return cervejariaRepository.saveAndFlush(cervejaria);
		} catch (Exception e) {
			logger.error("Erro ao importar cervejaria:" + brewery.getName(), e);
			return null;
		}
	}

	@Override
	public Style importar(com.sosv.breweryDB.connector.entity.Style style) {
		try {
			Style findByName = styleRepository.findByName(style.getName());
			if (findByName != null) {
				logger.info("Style já importado: " + findByName.getName());
				return findByName;
			}

			Style novo = new Style();
			novo.setCreateDate(new Date());
			BeanUtils.copyProperties(style, novo, "category", "id",
					"createDate");
			novo.setCategory(importar(style.getCategory()));

			return styleRepository.saveAndFlush(novo);
		} catch (Exception e) {
			logger.error("Erro ao importar Syle:" + style.getName(), e);
			return null;
		}
	}

	@Override
	@Transactional
	public Category importar(
			com.sosv.breweryDB.connector.entity.Category category) {
		try {
			Category findByName = categoriaRepository.findByName(category
					.getName());
			if (findByName != null) {
				logger.info("Category já importado: " + findByName.getName());
				return findByName;
			}
			Category novo = new Category();
			novo.setCreateDate(new Date());
			novo.setName(category.getName());

			return categoriaRepository.saveAndFlush(novo);
		} catch (Exception e) {
			logger.error("Erro ao importar category:" + category.getName(), e);
			return null;
		}
	}

}
