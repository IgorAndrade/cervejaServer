package br.com.cerveja.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cerveja.model.Usuario;
import br.com.cerveja.repository.UsuarioRepository;
import br.com.cerveja.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	@Autowired
	private UsuarioRepository repository;

	@Override
	public Usuario findByEmail(String email) {
		return repository.findByEmail(email);
	}

}
