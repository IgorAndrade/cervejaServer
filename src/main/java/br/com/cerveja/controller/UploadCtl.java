package br.com.cerveja.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/service/upload")
public class UploadCtl {

	@RequestMapping(value="/upload",method={RequestMethod.POST})
	public ResponseEntity<?> upload(@RequestParam("rotulo") MultipartFile imagem,
			@RequestParam(value="pasta",required=false)String pasta ){
		
		return ResponseEntity.notFound().build();
	}
	
}
