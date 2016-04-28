package br.com.cerveja.service;

import java.util.List;

import br.com.cerveja.model.cerveja.Cerveja;

import com.sosv.breweryDB.connector.entity.beer.Beer;

public interface Importador<O> {
	
	public Cerveja importar(String id);
	public Cerveja importar(O beer);
	public List<Cerveja> importar(List<O> beer);
}
