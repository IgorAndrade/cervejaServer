package br.com.cerveja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cerveja.model.cerveja.Cerveja;
import br.com.cerveja.service.CervejaService;
import br.com.cerveja.service.CloundCervejaService;
import br.com.cerveja.service.impl.breweryDB.CloundCervejaServiceImpl;

import com.sosv.breweryDB.connector.entity.beer.Beer;
@RestController
@RequestMapping(value = "/service/cerveja")
public class CervejaCtl {
	@Autowired
	private CervejaService service;
	
	@RequestMapping(value = "/importar/{id}")
	public ResponseEntity importar(@PathVariable("id")String id){
		Cerveja cerveja = service.importar(id);
		
		if(cerveja!=null)
		return ResponseEntity.ok(cerveja);
		else
		return ResponseEntity.notFound().build();
	}
}
