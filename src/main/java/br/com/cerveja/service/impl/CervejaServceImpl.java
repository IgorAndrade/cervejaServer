package br.com.cerveja.service.impl;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cerveja.controller.Filter;
import br.com.cerveja.controller.VO.PesquisaCervejaVO;
import br.com.cerveja.exception.RN;
import br.com.cerveja.exception.RNException;
import br.com.cerveja.exception.RNExceptionFactory;
import br.com.cerveja.model.Images;
import br.com.cerveja.model.cerveja.Cerveja;
import br.com.cerveja.model.cerveja.StatusCerveja;
import br.com.cerveja.repository.cerveja.CervejaRepository;
import br.com.cerveja.repository.cerveja.ImagesRepository;
import br.com.cerveja.service.CervejaService;
import br.com.cerveja.service.CloudinaryService;
import br.com.cerveja.service.CloundCervejaService;

import com.sosv.breweryDB.connector.entity.beer.Beer;

@Service("CervejaServce")
public class CervejaServceImpl implements CervejaService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	@Qualifier("BreweryDB")
	CloundCervejaService<Beer> cloundCerveja;
	@Autowired
	CervejaRepository cervejaRepository;
	@Autowired
	CloudinaryService cloudinaryService;
	@Autowired
	RNExceptionFactory rnExceptionFactory;
	

	@Override
	public Cerveja buscarPorId(Long id) {
		return cervejaRepository.findOne(id);
	}

	@Override
	@Transactional
	public Set<PesquisaCervejaVO> pesquisar(String q) {
		Set<PesquisaCervejaVO> resultado = new HashSet<PesquisaCervejaVO>();
		
		List<Cerveja> cervejas = cervejaRepository.pesquisar(q);
		for(Cerveja c : cervejas){
			resultado.add(new PesquisaCervejaVO(c));
		}
		
		List<Beer> listBeer = cloundCerveja.pesquisar(q);
		if(listBeer!=null)
		for(Beer b : listBeer){
			resultado.add(new PesquisaCervejaVO(b));
		}


		return resultado;
	}

	@Override
	public List<Cerveja> pesquisar(String q, Cerveja cerveja) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cerveja salvar(Cerveja cerveja) {
		try {
			return cervejaRepository.save(cerveja);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Cerveja addImgs(Long id, File rotulo,File garrafa,File[] outros) throws RNException {
		Cerveja cerveja = cervejaRepository.findOne(id);
		Images rotuloAntigo = null;
		Images garrafaAntiga = null;
		if(cerveja==null)
			rnExceptionFactory.addRN(RN.CERVEJA_NAO_ECONTRADA).lancar();
		try {
			if(rotulo!=null){
				Images imgRotulo = cloudinaryService.ulpoad(rotulo, "cerveja");
				removerFile(rotulo);
				rotuloAntigo = cerveja.getRotulo();
				cerveja.setRotulo(imgRotulo);
			}
			if(garrafa!=null){
				Images imgGarrafa = cloudinaryService.ulpoad(garrafa, "cerveja");
				removerFile(garrafa);
				garrafaAntiga = cerveja.getGarrafa();
				cerveja.setGarrafa(imgGarrafa);
			}
			Cerveja salva = cervejaRepository.save(cerveja);
			removerImg(rotuloAntigo);
			removerImg(garrafaAntiga);
			return salva;
		} catch (Exception e) {
			logger.error("erro ao fazer upload");
			rnExceptionFactory.addRN(RN.ERRO_UPLOAD,cerveja.getName()).lancar();
			return null;
		}
	}
	
	@Override
	public Cerveja importar(String id) {
		logger.debug("importando cerveja");
		return cloundCerveja.importar(id);
	}
	
	public void removerImg(Images img){
		if(img ==null)
			return;
		
		cloudinaryService.remover(img.getPublic_id());
	}
	
	
	private void removerFile(File file){
		if(file !=null)
		file.deleteOnExit();
	}
	
	@Override
	public Page<Cerveja> listar(String name, String cervejaria,StatusCerveja filterStatus,
			Pageable pageRequest) {
		return cervejaRepository.listar(name, cervejaria, filterStatus,pageRequest);
	}
	@Override
	public Page<Cerveja> listar(Filter filter,
			Pageable pageRequest) {
		return cervejaRepository.pesquisar(filter,pageRequest);
	}
	
}
