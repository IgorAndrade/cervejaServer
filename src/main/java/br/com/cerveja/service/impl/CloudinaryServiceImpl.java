package br.com.cerveja.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.cerveja.model.Images;
import br.com.cerveja.service.CloudinaryService;

import com.cloudinary.Cloudinary;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

	private Cloudinary cloudinary;
	@Value("${cloudinary.cloud_name}")
	private String cloud_name;
	@Value("${cloudinary.api_key}")
	private String api_key;
	@Value("${cloudinary.api_secret}")
	private String api_secret;
	
	@PostConstruct
	private void init() {
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("cloud_name", cloud_name);
		map.put("api_key", api_key);
		map.put("api_secret", api_secret);
		cloudinary = new Cloudinary(map);
	}
	
	@Override
	public Images ulpoad(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Images ulpoad(String url, Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Images ulpoad(File url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Images ulpoad(File url, Map map) {
		// TODO Auto-generated method stub
		return null;
	}

}
