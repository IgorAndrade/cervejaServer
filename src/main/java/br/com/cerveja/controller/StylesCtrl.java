package br.com.cerveja.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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

import br.com.cerveja.model.cerveja.Category;
import br.com.cerveja.model.cerveja.Style;
import br.com.cerveja.repository.cerveja.CategoriaRepository;
import br.com.cerveja.repository.cerveja.StyleRepository;

@RestController
@RequestMapping(value = "/service/style")
public class StylesCtrl {
	@Autowired
private StyleRepository repo;
	@Autowired
	private CategoriaRepository catRepo;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAll(@RequestParam(value="pg",defaultValue="0")Integer pg,
			@RequestParam(value="qtd",defaultValue="10")Integer qtd,
			@RequestParam(value="filterStyle",required=false,defaultValue="")String filterStyle,
			@RequestParam(value="orderStyle",required=false)String orderStyle,
			@RequestParam(value="categoria",required=false,defaultValue="")String filterCat,
			@RequestParam(value="orderCategoria",required=false)String orderCat){
		
		Pageable pageRequest = null;
		List<Order> orders = new ArrayList<Sort.Order>();
		if(orderStyle!=null)
			orders.add(new Order(Direction.fromString(orderStyle), "name"));
		if(orderCat!=null)
			orders.add(new Order(Direction.fromString(orderCat), "category.name"));
		
		if(orders.isEmpty())
			pageRequest = new PageRequest(pg,qtd,new Sort("name"));
		else
			pageRequest = new PageRequest(pg,qtd,new Sort(orders));	
		
		Page<Style> pages =null;
		if(filterCat == null  && filterStyle==null)
			pages = repo.findAll(pageRequest);
		else
			 pages = repo.findByNameOrCategory(filterStyle, filterCat, pageRequest);

		if(pages.getTotalElements()<=0)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(pages);
	}
	@RequestMapping(value="/categoria",method=RequestMethod.GET)
	public ResponseEntity<?> getCatAll(@RequestParam(value="pg",defaultValue="0")Integer pg,
			@RequestParam(value="qtd",defaultValue="10")Integer qtd,Filter filter,SortParam sort){
		
		Pageable pageRequest = null;
		List<Order> orders = new ArrayList<Sort.Order>();
		if (sort.getSortCategoria() != null)
			orders.add(new Order(Direction.fromString(sort.getSortCategoria() ), "name"));
		else
			orders.add(new Order(Direction.ASC , "name"));
			
		Page<Category> lista = catRepo.pesquisarCat(filter, pageRequest);

		if (lista.getTotalElements() <= 0)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(lista);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getOne(@PathVariable("id") Long id){
		Style style = repo.findOne(id);
		if(style==null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(style);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") Long id,@Valid @RequestBody Style style,Errors erros){
		try{
			if(!erros.hasErrors()){
				Style salvo = repo.save(style);
				return  ResponseEntity.ok(salvo);
			}else{
				return ResponseEntity.badRequest().body(erros.getAllErrors());
			}
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
	}
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> novo(@Valid @RequestBody Style style,Errors erros){
		try{
			if(!erros.hasErrors()){
				Style salvo = repo.save(style);
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
