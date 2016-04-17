package br.com.cerveja.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sosv.breweryDB.connector.entity.beer.Beer;
import com.sosv.breweryDB.connector.guice.BreweryDBAsyncModule;
import com.sosv.breweryDB.connector.guice.BreweryDBSyncModule;
import com.sosv.breweryDB.connector.service.IBreweryDBService;
import com.sosv.breweryDB.connector.service.exceptions.ApiKeyNotFoundExeption;
@Service
public class CloundCervejaService {

	 private IBreweryDBService breweryDb;
	
	public Beer importar(String id){
		
		try {
			Beer beerById = breweryDb.getBeerById(id);
			return beerById;
		} catch (ApiKeyNotFoundExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	@PostConstruct
	private void init(){
		Injector injector = Guice.createInjector(new BreweryDBSyncModule());
		breweryDb = injector
		        .getInstance(IBreweryDBService.class);
	}
	
	
}
