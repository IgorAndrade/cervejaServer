package br.com.cerveja.service;

import java.util.List;

import br.com.cerveja.model.cerveja.Category;
import br.com.cerveja.model.cerveja.Cerveja;

import com.sosv.breweryDB.connector.entity.Style;
import com.sosv.breweryDB.connector.entity.beer.Beer;


public interface CloundCervejaService<O> extends Importador<O> {

	 List<O> pesquisar(String q);

	List<Beer> pesquisar(String q, Cerveja c);

	br.com.cerveja.model.cerveja.Style importar(Style style);

	Category importar(com.sosv.breweryDB.connector.entity.Category category);
	
}
