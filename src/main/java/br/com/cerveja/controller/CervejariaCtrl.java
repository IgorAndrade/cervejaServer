package br.com.cerveja.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cerveja.model.cerveja.Cervejaria;
import br.com.cerveja.model.cerveja.Style;
import br.com.cerveja.repository.cerveja.CervejaRepository;
import br.com.cerveja.repository.cerveja.CervejariaRepository;
import br.com.cerveja.repository.cerveja.StyleRepository;

@RestController
@RequestMapping(value = "/service/cervejaria")
public class CervejariaCtrl {
	@Autowired
private CervejariaRepository repo;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAll(@RequestParam(value="pg",defaultValue="0")Integer pg,
			@RequestParam(value="qtd",defaultValue="10")Integer qtd,
			@RequestParam(value="filterCervejaria",required=false,defaultValue="")String filterCervejaria,
			@RequestParam(value="filterStatus",required=false,defaultValue="")String filterStatus,
			@RequestParam(value="orderCervejaria",required=false,defaultValue="asc")String orderCervejaria){
		

		Pageable	pageRequest = new PageRequest(pg,qtd,new Sort(Direction.fromString(orderCervejaria), "name"));	
		
		Page<Cervejaria> pages =null;
		if(StringUtils.isBlank(filterCervejaria) && StringUtils.isBlank(filterStatus) )
			pages = repo.findAll(pageRequest);
		else 
			 pages = repo.findByNameIgnoreCaseContainingOrStatus(filterCervejaria, null,pageRequest);

		if(pages.getTotalElements()<=0)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(pages);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getOne(@PathVariable("id") Long id){
		Cervejaria cervejaria = repo.findOne(id);
		if(cervejaria==null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(cervejaria);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") Long id,@Valid @RequestBody Cervejaria cervejaria,Errors erros){
		try{
			if(!erros.hasErrors()){
				Cervejaria salvo = repo.save(cervejaria);
				return  ResponseEntity.ok(salvo);
			}else{
				return ResponseEntity.badRequest().body(erros.getAllErrors());
			}
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
	}
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> novo(@Valid @RequestBody Cervejaria cervejaria,Errors erros){
		try{
			if(!erros.hasErrors()){
				Cervejaria salvo = repo.save(cervejaria);
				return  ResponseEntity.ok(salvo);
			}else{
				return ResponseEntity.badRequest().body(erros.getAllErrors());
			}
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
	}
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		
		try{
			repo.delete(id);
			return ResponseEntity.ok().build();
		}catch(Exception e){
			return ResponseEntity.badRequest().build();
		}
	}
}
