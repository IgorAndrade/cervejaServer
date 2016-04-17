package br.com.cerveja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cerveja.service.CloundCervejaService;

import com.sosv.breweryDB.connector.entity.beer.Beer;
@RestController
@RequestMapping(value = "/service/cerveja")
public class CervejaCtl {
	@Autowired
	private CloundCervejaService importador;
	
	@RequestMapping(value = "/importar/{id}")
	public ResponseEntity<Beer> importar(@PathVariable("id")String id){
		Beer beer = importador.importar(id);
		
		return ResponseEntity.ok(beer);
	}
}
