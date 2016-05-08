package br.com.cerveja.service;

import java.io.File;
import java.util.Map;

import br.com.cerveja.model.Images;

public interface CloudinaryService {

	Images ulpoad(String url,String pasta);
	Images ulpoad(String url,Map map);
	Images ulpoad(File file, String pasta);
	Images ulpoad(File file,Map map);
	void remover(String public_id);
}
