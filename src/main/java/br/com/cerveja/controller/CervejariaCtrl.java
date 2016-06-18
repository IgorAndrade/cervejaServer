package br.com.cerveja.controller;

import java.io.File;

import javax.validation.Valid;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.cerveja.exception.RNException;
import br.com.cerveja.model.cerveja.Cervejaria;
import br.com.cerveja.service.CervejariaService;
@RestController
@RequestMapping(value = "/service/cervejaria")
public class CervejariaCtrl {
	@Autowired
	private CervejariaService service;
	@Value("${upload.path}")
	private String uploadPath;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll(
			@RequestParam(value = "pg", defaultValue = "0") Integer pg,
			@RequestParam(value = "qtd", defaultValue = "10") Integer qtd,
			@RequestParam(value = "orderCervejaria", required = false, defaultValue = "asc") String orderCervejaria,Filter param,SortParam sort) {

		Pageable pageRequest = new PageRequest(pg, qtd, new Sort(
				Direction.fromString(orderCervejaria), "name"));

		Page<Cervejaria> pages = service.pesquisar(param, pageRequest);
		if (pages.getTotalElements() <= 0)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(pages);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getOne(@PathVariable("id") Long id) {
		Cervejaria cervejaria = service.findOne(id);
		if (cervejaria == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(cervejaria);
	}

	@RequestMapping(value = "/{id}", method = {RequestMethod.PUT,RequestMethod.POST})
	public ResponseEntity<?> update(@PathVariable("id") Long id,
			@Valid @RequestBody Cervejaria cervejaria, Errors erros) {
		try {
			if (!erros.hasErrors()) {
				Cervejaria salvo = service.save(cervejaria);
				return ResponseEntity.ok(salvo);
			} else {
				return ResponseEntity.badRequest().body(erros.getAllErrors());
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
					e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> novo(@Valid @RequestBody Cervejaria cervejaria,
			Errors erros) {
		try {
			if (!erros.hasErrors()) {
				Cervejaria salvo = service.save(cervejaria);
				return ResponseEntity.ok(salvo);
			} else {
				return ResponseEntity.badRequest().body(erros.getAllErrors());
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
					e.getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {

		try {
			service.delete(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	public ResponseEntity<?> upload(
			@RequestParam("images") MultipartFile images,
			@RequestParam(value = "pasta", required = false) String pasta,
			@RequestParam(value = "id", required = false) Long id) {
		File fimages = null;
		try {
			if (images != null) {
				fimages = new File(uploadPath, images.getOriginalFilename());
				images.transferTo(fimages);
				service.addImgs(id, fimages);
			}
			return ResponseEntity.ok().build();
		} catch (RNException rnE) {
			return ResponseEntity.badRequest().body(rnE.getErros());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@RequestMapping(value = "/teste", method = RequestMethod.GET)
	public ResponseEntity<?> teste(@RequestParam(value = "pg", defaultValue = "0") Integer pg,
			@RequestParam(value = "qtd", defaultValue = "10") Integer qtd,@RequestParam("id") Long id,
			Filter param) {

		try {
			Pageable pageable = new PageRequest(pg, qtd);
			Page<Cervejaria> lista = service.pesquisar(param, pageable);
			return ResponseEntity.ok(lista);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
