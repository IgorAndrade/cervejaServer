package br.com.cerveja.service;

import java.io.File;
import java.util.Map;

import br.com.cerveja.model.Images;

public interface CloudinaryService {

	Images ulpoad(String url);
	Images ulpoad(String url,Map map);
	Images ulpoad(File url);
	Images ulpoad(File url,Map map);
}
