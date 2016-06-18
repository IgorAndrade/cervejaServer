package br.com.cerveja.service.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cerveja.controller.Filter;
import br.com.cerveja.exception.RN;
import br.com.cerveja.exception.RNException;
import br.com.cerveja.exception.RNExceptionFactory;
import br.com.cerveja.model.Images;
import br.com.cerveja.model.cerveja.Cerveja;
import br.com.cerveja.model.cerveja.Cervejaria;
import br.com.cerveja.repository.cerveja.CervejariaRepository;
import br.com.cerveja.repository.cerveja.ImagesRepository;
import br.com.cerveja.service.CervejariaService;
import br.com.cerveja.service.CloudinaryService;
@Service
public class CervejariaServiceImpl implements CervejariaService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CervejariaRepository repo;
	@Autowired
	CloudinaryService cloudinaryService;
	@Autowired
	RNExceptionFactory rnExceptionFactory;

	@Override
	public Cervejaria addImgs(Long id, File rotulo) throws RNException {
		Cervejaria cervejaria = repo.findOne(id);
		Images rotuloAntigo = null;
		if(cervejaria==null)
			rnExceptionFactory.addRN(RN.CERVEJA_NAO_ECONTRADA).lancar();
		try {
			if(rotulo!=null){
				Images imgRotulo = cloudinaryService.ulpoad(rotulo, "cervejaria");
				removerFile(rotulo);
				rotuloAntigo = cervejaria.getImages();
				cervejaria.setImages(imgRotulo);
			}
			
			Cervejaria salva = repo.save(cervejaria);
			removerImg(rotuloAntigo);
			return salva;
		} catch (Exception e) {
			logger.error("erro ao fazer upload");
			rnExceptionFactory.addRN(RN.ERRO_UPLOAD,cervejaria.getName()).lancar();
			return null;
		}
	}
	@Override
	public Cervejaria findByBrewerydbId(String brewerydbId) {
		return repo.findByBrewerydbId(brewerydbId);
	}
	@Override
	public Page<Cervejaria> findByNameIgnoreCaseContaining(String name,
			Pageable pageable) {
		return repo.findByNameIgnoreCaseContaining(name, pageable);
	}
	@Override
	public Page<Cervejaria> pesquisar(Filter param, Pageable pageable) {
		return repo.pesquisar(param, pageable);
	}
	@Override
	public Cervejaria save(Cervejaria entity) {
		return repo.save(entity);
	}
	@Override
	public Page<Cervejaria> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}
	@Override
	public Cervejaria findOne(Long id) {
		return repo.findOne(id);
	}
	@Override
	public void delete(Long id) {
		repo.delete(id);
	}
	@Override
	public void delete(Cervejaria entity) {
		repo.delete(entity);
	}
	@Override
	public void removerImg(Images img){
		if(img ==null)
			return;
		cloudinaryService.remover(img.getPublic_id());
	}
	
	
	private void removerFile(File file){
		if(file !=null)
		file.deleteOnExit();
	}
}
