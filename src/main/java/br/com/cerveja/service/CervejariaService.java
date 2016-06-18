package br.com.cerveja.service;

import java.io.File;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cerveja.controller.Filter;
import br.com.cerveja.exception.RNException;
import br.com.cerveja.model.Images;
import br.com.cerveja.model.cerveja.Cervejaria;

public interface CervejariaService {

	Cervejaria addImgs(Long id, File imagens)
			throws RNException;

	Cervejaria findByBrewerydbId(String brewerydbId);

	Page<Cervejaria> findByNameIgnoreCaseContaining(String name,
			Pageable pageable);

	Page<Cervejaria> pesquisar(Filter param, Pageable pageable);

	Cervejaria save(Cervejaria entity);

	Page<Cervejaria> findAll(Pageable pageable);

	Cervejaria findOne(Long id);

	void delete(Long id);

	void delete(Cervejaria entity);

	void removerImg(Images img);

}
