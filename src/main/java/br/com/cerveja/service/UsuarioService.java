package br.com.cerveja.service;

import br.com.cerveja.model.Usuario;

public interface UsuarioService {

	
	public Usuario findByEmail(String email);
	
}
