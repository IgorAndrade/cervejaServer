package br.com.cerveja.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.cerveja.model.Images;
import br.com.cerveja.repository.cerveja.ImagesRepository;
import br.com.cerveja.service.CloudinaryService;

import com.cloudinary.Cloudinary;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Cloudinary cloudinary;
	@Value("${cloudinary.cloud_name}")
	private String cloud_name;
	@Value("${cloudinary.api_key}")
	private String api_key;
	@Value("${cloudinary.api_secret}")
	private String api_secret;
	@Autowired
	private ImagesRepository repository;
	
	
	@PostConstruct
	private void init() {
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("cloud_name", cloud_name);
		map.put("api_key", api_key);
		map.put("api_secret", api_secret);
		cloudinary = new Cloudinary(map);
	}
	
	@Override
	public Images ulpoad(String file,String pasta) {
		HashMap<String,String> param = new HashMap<String, String>();
		param.put("folder",pasta);
		Images images = doUpload(file, param);
		return images;
	}

	@Override
	public Images ulpoad(String url, Map map) {
		return doUpload(url, map);
	}
	
	@Override
	public Images ulpoad(File file,String pasta) {
		HashMap<String,String> param = new HashMap<String, String>();
		param.put("folder",pasta);
		Images images = doUpload(file, param);
		return images;
	}
	
	@Override
	public Images ulpoad(File url, Map map) {
		return doUpload(url, map);
	}
	
	private Images doUpload(Object img,Map map){
		try {
			if(img == null)
				return null;
			
			Map resposta = cloudinary.uploader().upload(img, map);
			Images imagem = gerarImagem(resposta);
			logger.info("importando img: "+imagem.getPublic_id());
			return repository.saveAndFlush(imagem);
		} catch (Exception e) {
			logger.error("erro ao subir imagem",e);
			return null;
		}
	}
	
	private Images gerarImagem(Map<String,String> map){
		Images i = new Images();
		
		i.setFormat(map.get("format"));
		i.setPublic_id(map.get("public_id"));
		i.setUrl(map.get("url"));
		i.setSecure_url(map.get("secure_url"));
		
		return i;
	}
	@Override
	public void remover(String public_id){
		try {
			Images findByPublic_id = repository.findByPublic_Id(public_id);
			if(findByPublic_id!=null)
				repository.delete(findByPublic_id);
			cloudinary.uploader().destroy(public_id, new HashMap<String,String>());
		} catch (IOException e) {
			logger.error("Erro ao remover img:"+public_id,e);
		}
	}


}
